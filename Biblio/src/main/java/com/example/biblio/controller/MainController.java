package com.example.biblio.controller;

import com.example.biblio.entity.*;
import com.example.biblio.repository.IUtilisateurRepository;
import com.example.biblio.security.AppAuthProvider;
import com.example.biblio.security.MyUserDetails;
import com.example.biblio.service.LivreService;
import com.example.biblio.service.PretService;
import com.example.biblio.service.UtilisateurService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jshell.execution.Util;
import org.apache.catalina.filters.ExpiresFilter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost/",  allowedHeaders = "*", allowCredentials = "true")
@RestController
public class MainController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private PretService pretService;

    @Autowired
    UtilisateurService userService;
    @Autowired
    private AuthenticationManager authManager;

    Logger log = Logger.getLogger("");

    @PostMapping("/connexion")
    public String v2Login(@RequestParam Map<String, String> info , HttpServletRequest req, HttpServletResponse resp)
            throws JsonProcessingException {
        List<Map<String, String>> ret = new ArrayList<>();
        Map<String, String> retM= new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String mail = info.get("username");
        String mdp = info.get("password");
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(mail,mdp);
        authReq.setDetails(new WebAuthenticationDetails(req));
        try {
            Authentication auth = authManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            retM.put("response", "sucess");
            ret.add(retM);
        }
        catch(BadCredentialsException e){
            retM.put("response", "failed");
            ret.add(retM);
        }

        resp.setStatus(HttpStatus.OK.value());
        return mapper.writeValueAsString(ret);
    }
    @GetMapping("/deconnexion")
    public String deconnexion(HttpServletRequest req, HttpServletResponse resp) throws JsonProcessingException, ServletException {
        List<Map<String, String>> ret = new ArrayList<>();
        Map<String, String> retM= new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        req.logout();
        retM.put("response", "success");
        ret.add(retM);
        return mapper.writeValueAsString(ret);
    }
    @GetMapping(value = "/", produces={"application/json; charset=UTF-8"})
    public String  getBook(HttpServletResponse resp) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(livreService.getBook());
        resp.setStatus(200);
        return jsonInString;
    }
    @GetMapping(value = "/search", produces={"application/json; charset=UTF-8"})
    public String searchBook(@RequestParam Map<String, String> info, HttpServletResponse resp) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(livreService.search(info));
        return jsonInString;
    }

    @GetMapping(value = "/pret", produces={"application/json; charset=UTF-8"})
    public List<Pret> pret(@RequestParam Map<String, String> info){
        // recuperation de l'id
        MyUserDetails infoUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return pretService.getPretByUser(infoUser.getUsername());
    }
   // @PutMapping("/pret")
   @RequestMapping( method = RequestMethod.PUT)

   public String continuePret(@RequestParam String idPret){
        // recuperation de l'id
        MyUserDetails infoUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean renouvellement = pretService.updatePret(infoUser.getUsername(), idPret);
        if (renouvellement)
            return "ok";
        return "KO";
    }

    @GetMapping("/create")
    public String createUser(@RequestParam Map<String, String> info){

        boolean create = userService.createUser(info);
        if(!create){
            return "KO";
        }
        return "creation";
    }

    @GetMapping("/info")
    public Utilisateur infoUser(){
        // recuperation de l'id
        MyUserDetails infoUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur user = userService.getUser(infoUser.getUsername());
        user.setMdp("");
        return user;
    }

    @PutMapping("/update")
    public String updateUser(@RequestParam Map<String, String> info){
        // recuperation de l'id
        MyUserDetails infoUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String resultat = userService.updateUser(info, infoUser.getUsername());
        int code = Integer.parseInt(resultat.split(";")[0]);
//        try {
            resultat = resultat.split(";")[1];
//        } catch(Exception e) {
//            ExpiresFilter.XHttpServletResponse(404);
//            return e;
//        }
        if (code == 0){
            // erreur
            return resultat;
        }
        else {
            // modif ok
            return resultat;
        }
    }
    @GetMapping("/batch")
    public List<HashMap<String, String>> batch(@RequestParam String username, @RequestParam String password){
        return pretService.relancePret(username, password);
    }

    @GetMapping("/stats")
    public Map<String, Object> statsLend(){
        return pretService.getStats();
    }
}
