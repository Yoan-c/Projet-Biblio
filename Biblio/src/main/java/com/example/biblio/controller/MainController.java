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
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        String mail = info.get("username");
        String mdp = info.get("password");
        boolean isConnect = userService.connexionUser(mail, mdp);
        if(isConnect){
            resultMap.put("response", "success");
            resultMap.put("token", userService.getHashUser(mail));
            result.add(resultMap);
        }
        else {
            resultMap.put("response", "failed");
            result.add(resultMap);
        }
        resp.setStatus(HttpStatus.OK.value());

        return mapper.writeValueAsString(result);
    }
    @GetMapping("/deconnexion")
    public String deconnexion(@RequestParam String token, HttpServletResponse resp) throws JsonProcessingException, ServletException {
        boolean isDeconnect = userService.deconnexion(token);
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        String response = (isDeconnect)? "success" : "failed";
        resultMap.put("response", response);
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }
    @GetMapping(value = "/", produces={"application/json; charset=UTF-8"})
    public String  getBook(@RequestParam String token, HttpServletResponse resp) throws JsonProcessingException {
        token = (token != null) ? token : "";
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        boolean isConnect = userService.isValidToken(token);
        String jsonInString = "";
        if(isConnect) {
            jsonInString = mapper.writeValueAsString(livreService.getBook());
            resultMap.put("data", jsonInString);
            resultMap.put("response", "success");
            resp.setStatus(200);
        }
        else {
            jsonInString = mapper.writeValueAsString("");
            resultMap.put("response", "failed");
        }
        resultMap.put("data", jsonInString);
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }
    @GetMapping(value = "/search", produces={"application/json; charset=UTF-8"})
    public String searchBook(@RequestParam Map<String, String> info) throws JsonProcessingException {
        String token = info.get("token");
        token = (token != null) ? token : "";
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        boolean isConnect = userService.isValidToken(token);
        String jsonInString = "";
        if(isConnect){
            resultMap.put("response", "success");
            jsonInString = mapper.writeValueAsString(livreService.search(info));
        }
        else{
            resultMap.put("response", "failed");
            jsonInString = mapper.writeValueAsString("");
        }
        resultMap.put("data", jsonInString);
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }

    @GetMapping(value = "/pret", produces={"application/json; charset=UTF-8"})
    public String pret(@RequestParam String token) throws JsonProcessingException {
        token = (token != null) ? token : "";
        boolean isConnect = userService.isValidToken(token);
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        String jsonInString = "";
        if(isConnect) {
            mapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            String mail = userService.getUserByToken(token).getEmail();
            jsonInString = mapper.writeValueAsString(pretService.getPretByUser(mail));
            resultMap.put("response", "success");
        }else {
            resultMap.put("response", "failed");
            jsonInString = mapper.writeValueAsString("");
        }
        resultMap.put("data", jsonInString);
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }

    @PatchMapping(value = "/pret", produces={"application/json; charset=UTF-8"})
   public String continuePret(@RequestParam Map<String, String> info, HttpServletResponse resp) throws JsonProcessingException {
        String token = info.get("token");
        token = (token != null) ? token : "";
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        HashMap<String, String> ret = new HashMap<>();
        boolean isConnect = userService.isValidToken(token);
        if (isConnect){
            String mail = userService.getUserByToken(token).getEmail();
            boolean renouvellement = pretService.updatePret(mail, info.get("idPret"));
            if (renouvellement) {
                resp.setStatus(200);
                resultMap.put("response", "success");
                resultMap.put("data", mapper.writeValueAsString(renouvellement));
                result.add(resultMap);
                return mapper.writeValueAsString(result);
            }
        }
        resp.setStatus(403);
        resultMap.put("response", "failure");
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }

    @PostMapping(value = "/create", produces={"application/json; charset=UTF-8"})
    public String createUser(@RequestBody Map<String, String> info) throws JsonProcessingException {
        boolean create = userService.createUser(info);
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        if(!create){
            resultMap.put("response", "success");
            resultMap.put("data", mapper.writeValueAsString(create));
            result.add(resultMap);
            return mapper.writeValueAsString(result);
        }
        resultMap.put("response", "failed");
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }

    @GetMapping("/info")
    public String infoUser(@RequestParam String token) throws JsonProcessingException {
        // recuperation de l'id
        token = (token != null) ? token : "";
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        boolean isConnect = userService.isValidToken(token);
        if (isConnect){
            Utilisateur user = userService.getUserByToken(token);
            user.setValidity(null);
            resultMap.put("response", "success");
            resultMap.put("data", mapper.writeValueAsString(user));
            result.add(resultMap);
            return mapper.writeValueAsString(result);
        }
        resultMap.put("response", "failed");
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }

    @PutMapping(value ="/update",  produces={"application/json; charset=UTF-8"})
    public String updateUser(@RequestBody Map<String, String> info) throws JsonProcessingException {
        // recuperation de l'id
        String token = info.get("token");
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        token = (token != null) ? token : "";
        boolean isConnect = userService.isValidToken(token);
        if (isConnect){
            Utilisateur infoUser = userService.getUserByToken(token);
            String t = infoUser.getHash();
            String resultats = userService.updateUser(info, infoUser.getEmail(), infoUser);
            int code = Integer.parseInt(resultats.split(";")[0]);
            String resultat = resultats.split(";")[1];
            if (code == 2)
                infoUser.setEmail(info.get("mail"));
            if (code > 0)
                token = resultats.split(";")[2];
            resultMap.put("response", "success");
            resultMap.put("token", token);
            resultMap.put("data", resultat);
            result.add(resultMap);
            return mapper.writeValueAsString(result);
        }
        resultMap.put("response", "failed");
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }
    @GetMapping("/batch")
    public List<HashMap<String, String>> batch(@RequestParam String username, @RequestParam String password){
        return pretService.relancePret(username, password);
    }

    @GetMapping(value ="/stats",  produces={"application/json; charset=UTF-8"})
    public String statsLend(@RequestParam String token) throws JsonProcessingException {
        boolean isConnect = userService.isValidToken(token);
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        if (isConnect){
            resultMap.put("response", "success");
            resultMap.put("data", mapper.writeValueAsString(pretService.getStats()));
        }
        else{
            resultMap.put("response", "failed");
        }
        result.add(resultMap);
        return mapper.writeValueAsString(result);
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
