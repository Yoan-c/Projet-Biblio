package com.example.biblio.controller;

import com.example.biblio.entity.*;
import com.example.biblio.functions.hash.Empreinte;
import com.example.biblio.functions.token.Token;
import com.example.biblio.repository.IUtilisateurRepository;
import com.example.biblio.service.LivreService;
import com.example.biblio.service.PretService;
import com.example.biblio.service.UtilisateurService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jdk.jshell.execution.Util;
import org.apache.catalina.filters.ExpiresFilter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.processing.SupportedOptions;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MainController {
    @Autowired
    private LivreService livreService;

    @Autowired
    private PretService pretService;

    @Autowired
    UtilisateurService userService;


    private ObjectMapper mapper = new ObjectMapper();


    Logger log = Logger.getLogger("");

    @PostMapping("/connexion")
    public String v2Login(@RequestParam Map<String, String> info , HttpServletRequest req, HttpServletResponse resp)
            throws JsonProcessingException {
        List<Map<String, String>> ret = new ArrayList<>();
        Map<String, String> retM= new HashMap<>();
        String mail = info.get("username");
        String mdp = info.get("password");
        boolean isConnect = userService.connexionUser(mail, mdp);
        if(isConnect){
            retM.put("response", "success");
            retM.put("token", userService.getHashUser(mail));
            ret.add(retM);
        }
        else {
            retM.put("response", "failed");
            ret.add(retM);
        }
        resp.setStatus(HttpStatus.OK.value());

        return mapper.writeValueAsString(ret);
    }
    @GetMapping("/deconnexion")
    public String deconnexion(@RequestParam String token, HttpServletResponse resp) throws JsonProcessingException, ServletException {
        List<Map<String, String>> ret = new ArrayList<>();
        Map<String, String> retM= new HashMap<>();
        boolean isDeconnect = userService.deconnexion(token);
        String response = (isDeconnect)? "success" : "failed";
        retM.put("response", response);
        ret.add(retM);
        return mapper.writeValueAsString(ret);
    }
    @GetMapping(value = "/", produces={"application/json; charset=UTF-8"})
    public String  getBook(@RequestParam String token, HttpServletResponse resp) throws JsonProcessingException {
        token = (token != null) ? token : "";
        boolean isConnect = userService.isValidToken(token);
        String jsonInString = "";
        if(isConnect) {
            jsonInString = mapper.writeValueAsString(livreService.getBook());
            resp.setStatus(200);
        }
        else {
            jsonInString = mapper.writeValueAsString("");
        }
        return jsonInString;
    }
    @GetMapping(value = "/search", produces={"application/json; charset=UTF-8"})
    public String searchBook(@RequestParam Map<String, String> info) throws JsonProcessingException {
        String token = info.get("token");
        token = (token != null) ? token : "";
        boolean isConnect = userService.isValidToken(token);
        String jsonInString = "";
        if(isConnect)
            jsonInString = mapper.writeValueAsString(livreService.search(info));
        else
            jsonInString = mapper.writeValueAsString("");
        return jsonInString;
    }

    @GetMapping(value = "/pret", produces={"application/json; charset=UTF-8"})
    public String pret(@RequestParam String token) throws JsonProcessingException {
        token = (token != null) ? token : "";
        boolean isConnect = userService.isValidToken(token);
        String jsonInString = "";
        if(isConnect) {
            mapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            String mail = userService.getUserByToken(token).getEmail();
            jsonInString = mapper.writeValueAsString(pretService.getPretByUser(mail));
        }else
            jsonInString = mapper.writeValueAsString("");
        return jsonInString;
    }

    @PatchMapping(value = "/pret", produces={"application/json; charset=UTF-8"})
   public String continuePret(@RequestParam Map<String, String> info, HttpServletResponse resp) throws JsonProcessingException {
        String token = info.get("token");
        token = (token != null) ? token : "";
        HashMap<String, String> ret = new HashMap<>();
        boolean isConnect = userService.isValidToken(token);
        if (isConnect){
            String mail = userService.getUserByToken(token).getEmail();
            boolean renouvellement = pretService.updatePret(mail, info.get("idPret"));
            if (renouvellement) {
                resp.setStatus(200);
                ret.put("Result", "success");
                return mapper.writeValueAsString(ret);
            }
        }
        resp.setStatus(403);
        ret.put("Result", "failure");
        return mapper.writeValueAsString(ret);
    }

    @PostMapping(value = "/create", produces={"application/json; charset=UTF-8"})
    public String createUser(@RequestBody Map<String, String> info) throws JsonProcessingException {
        boolean create = userService.createUser(info);

        if(!create){
            return mapper.writeValueAsString("KO");
        }
        return mapper.writeValueAsString("OK");
    }

    @GetMapping("/info")
    public Utilisateur infoUser(@RequestParam String token){
        // recuperation de l'id
        token = (token != null) ? token : "";
        HashMap<String, String> ret = new HashMap<>();
        boolean isConnect = userService.isValidToken(token);
        if (isConnect){
            Utilisateur user = userService.getUserByToken(token);
            user.setValidity(null);
            return user;
        }
        return null;
    }

    @PutMapping(value ="/update",  produces={"application/json; charset=UTF-8"})
    public String updateUser(@RequestBody Map<String, String> info) throws JsonProcessingException {
        // recuperation de l'id
        String token = info.get("token");
        HashMap<String, String> ret = new HashMap<>();
        token = (token != null) ? token : "";
        boolean isConnect = userService.isValidToken(token);
        if (isConnect){
            Utilisateur infoUser = userService.getUserByToken(token);
            String t = infoUser.getHash();
            String resultats = userService.updateUser(info, infoUser.getEmail(), infoUser);
            int code = Integer.parseInt(resultats.split(";")[0]);
            String resultat = resultats.split(";")[1];
            log.info(resultat);
            if (code == 2)
                infoUser.setEmail(info.get("mail"));
            if (code > 0)
                token = resultats.split(";")[2];
            ret.put("token", token);
            return mapper.writeValueAsString(ret);
        }
        return mapper.writeValueAsString("Erreur");
    }
    @GetMapping("/batch")
    public List<HashMap<String, String>> batch(@RequestParam String username, @RequestParam String password){
        return pretService.relancePret(username, password);
    }

    @GetMapping(value ="/stats",  produces={"application/json; charset=UTF-8"})
    public Map<String, Object> statsLend(){
        return pretService.getStats();
    }

    @RequestMapping(value = "/images/{name}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)

    public void getImage(@PathVariable("name") final String name, HttpServletResponse response) throws IOException {
        var imgFile = new ClassPathResource("images/"+name);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try{
            StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
        }
        catch(Exception e){
           log.info("Erreur "+ e);
           imgFile = new ClassPathResource("images/aucun.png");
           StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
        }
    }
}
