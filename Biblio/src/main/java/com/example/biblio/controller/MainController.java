package com.example.biblio.controller;

import com.example.biblio.entity.Auteur;
import com.example.biblio.entity.Exemplaire;
import com.example.biblio.entity.Livre;
import com.example.biblio.repository.IAuteurRepository;
import com.example.biblio.repository.IExemplaireRepository;
import com.example.biblio.repository.ILivreRepository;
import com.example.biblio.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class MainController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private IExemplaireRepository exe;

    Logger log = Logger.getLogger("");

    @GetMapping("/")
    public List<Exemplaire> getBook(){

        List<Exemplaire> lstLivre = livreService.getBook();

        return lstLivre;
    }
    @GetMapping("/search")
    public List<Exemplaire> searchBook(@RequestParam Map<String, String> info){

        List<Exemplaire> lstLivre = livreService.search(info);
        log.info("recherche "+ lstLivre);

        return lstLivre;
    }
}
