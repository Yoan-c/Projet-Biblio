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

import javax.annotation.processing.SupportedOptions;
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

@CrossOrigin(origins = {"http://localhost"}, allowedHeaders = "*", allowCredentials = "true")
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

    private ObjectMapper mapper = new ObjectMapper();

    Logger log = Logger.getLogger("");

    @PostMapping("/connexion")
    public String v2Login(@RequestParam Map<String, String> info , HttpServletRequest req, HttpServletResponse resp)
            throws JsonProcessingException {
        List<Map<String, String>> ret = new ArrayList<>();
        Map<String, String> retM= new HashMap<>();
        String mail = info.get("username");
        String mdp = info.get("password");
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(mail,mdp);
        authReq.setDetails(new WebAuthenticationDetails(req));
        try {
            Authentication auth = authManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            retM.put("response", "success");
            ret.add(retM);
        }
        catch(BadCredentialsException e){
            retM.put("response", "failed");
            ret.add(retM);
        }

        resp.setStatus(HttpStatus.OK.value());
        if (mail.equals("admin"))
            return "redirect:/batch";
        return mapper.writeValueAsString(ret);
    }
    @GetMapping("/deconnexion")
    public String deconnexion(HttpServletRequest req, HttpServletResponse resp) throws JsonProcessingException, ServletException {
        List<Map<String, String>> ret = new ArrayList<>();
        Map<String, String> retM= new HashMap<>();
        req.logout();
        retM.put("response", "success");
        ret.add(retM);
        return mapper.writeValueAsString(ret);
    }
    @GetMapping(value = "/", produces={"application/json; charset=UTF-8"})
    public String  getBook(HttpServletResponse resp) throws JsonProcessingException {
        String jsonInString = mapper.writeValueAsString(livreService.getBook());
        resp.setStatus(200);
        return jsonInString;
    }
    @GetMapping(value = "/search", produces={"application/json; charset=UTF-8"})
    public String searchBook(@RequestParam Map<String, String> info) throws JsonProcessingException {
        String jsonInString = mapper.writeValueAsString(livreService.search(info));
        return jsonInString;
    }

    @GetMapping(value = "/pret", produces={"application/json; charset=UTF-8"})
    public String pret() throws JsonProcessingException {
        // recuperation de l'id
        MyUserDetails infoUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String jsonInString = mapper.writeValueAsString(pretService.getPretByUser(infoUser.getUsername()));
        return jsonInString;
    }

    @PatchMapping(value = "/pret", produces={"application/json; charset=UTF-8"})
   public String continuePret(@RequestParam String idPret, HttpServletResponse resp) throws JsonProcessingException {
        // recuperation de l'id
        MyUserDetails infoUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean renouvellement = pretService.updatePret(infoUser.getUsername(), idPret);
        HashMap<String, String> ret = new HashMap<>();
       if (renouvellement) {
           resp.setStatus(200);
           ret.put("Result", "success");
           return mapper.writeValueAsString(ret);
       }
       else {
           resp.setStatus(403);
           ret.put("Result", "failure");
           return mapper.writeValueAsString(ret);
       }
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
    public Utilisateur infoUser(){
        // recuperation de l'id
        MyUserDetails infoUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur user = userService.getUser(infoUser.getUsername());
        user.setMdp("");
        return user;
    }

    @PutMapping(value ="/update",  produces={"application/json; charset=UTF-8"})
    public String updateUser(@RequestBody Map<String, String> info) throws JsonProcessingException {
        // recuperation de l'id
        MyUserDetails infoUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String resultat = userService.updateUser(info, infoUser.getUsername());
        int code = Integer.parseInt(resultat.split(";")[0]);
        resultat = resultat.split(";")[1];
         if (code == 2)
             infoUser.setUsername(info.get("mail"));
         return mapper.writeValueAsString(resultat);
    }
    @GetMapping("/batch")
    public List<HashMap<String, String>> batch(@RequestParam String username, @RequestParam String password){
        return pretService.relancePret(username, password);
    }

    @GetMapping(value ="/stats",  produces={"application/json; charset=UTF-8"})
    public Map<String, Object> statsLend(){
        return pretService.getStats();
    }
}
