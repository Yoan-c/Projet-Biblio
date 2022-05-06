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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class MainController {
    @Autowired
    ILivreRepository livre;
    @Autowired
    IAuteurRepository auteurs;
    @Autowired
    private LivreService livreService;

    @Autowired
    private IExemplaireRepository exe;

    @GetMapping("/")
    public List<Exemplaire> test(){
        Logger log = Logger.getLogger("");
      //  List<Livre> lstLivre = livre.findAll();
        //List<Auteur> lstAuteurs = auteurs.findByName("Cole");
        //List<Livre> lstL = livre.searchByAuteurs("Cole", "Patrice");
      //  List<Livre> lstLivre = livreService.getBookByAuthorName("Cole");
        List<Exemplaire> lstLivre = livreService.getBook();
        // Exemplaire ex = exe.getExemplaireByIsbn(lstLivre.get(0));
       // List<Livre> lstLivre = livreService.getBookByGenre("Manga");
     //   log.info("ici"+ ex);
       /* for(Livre l:lstLivre){
            System.out.println("livre genre "+l.getGenres()+" isbn "+l.getIsbn());
        }*/
        return lstLivre;
    }
}
