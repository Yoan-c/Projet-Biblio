package com.example.biblio.controller;

import com.example.biblio.entity.*;
import com.example.biblio.repository.IAuteurRepository;
import com.example.biblio.repository.IExemplaireRepository;
import com.example.biblio.repository.ILivreRepository;
import com.example.biblio.repository.IPretRepository;
import com.example.biblio.security.MyUserDetails;
import com.example.biblio.service.LivreService;
import com.example.biblio.service.PretService;
import com.example.biblio.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class MainController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private PretService pretService;

    @Autowired
    UtilisateurService userService;

    Logger log = Logger.getLogger("");

    @GetMapping("/")
    public List<Exemplaire> getBook(){

        List<Exemplaire> lstLivre = livreService.getBook();

        return lstLivre;
    }
    @GetMapping("/search")
    public List<Exemplaire> searchBook(@RequestParam Map<String, String> info){
      // recuperation de l'id
        MyUserDetails req = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("recherche info "+ req.getUsername());
        List<Exemplaire> lstLivre = livreService.search(info);
        log.info("recherche "+ lstLivre);

        return lstLivre;
    }

    @GetMapping("/pret")
    public String pret(@RequestParam Map<String, String> info){
        // recuperation de l'id
        MyUserDetails infoUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pretService.getPretByUser(infoUser.getUsername());
     //   log.info("recherche "+ pret);

        return "ok";
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
        resultat = resultat.split(";")[1];
        if (code == 0){
            // erreur
            return resultat;
        }
        else {
            // modif ok
            return resultat;
        }
    }
}
