package com.example.biblio.service;

import com.example.biblio.entity.Exemplaire;
import com.example.biblio.entity.Livre;
import com.example.biblio.repository.IExemplaireRepository;
import com.example.biblio.repository.ILivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class LivreService {
    @Autowired
    private ILivreRepository livresRepository;
    @Autowired
    private ExemplaireService exemplaireService;

    public List<Exemplaire> getBook(){
        List<Livre> lstLivre = livresRepository.findAll();
        List<Exemplaire> lstExemplaire = new ArrayList<Exemplaire>();
        for(Livre l:lstLivre){
            Exemplaire ex =  exemplaireService.getExemplaireByIsbn(l);
            lstExemplaire.add(ex);
        }
        return lstExemplaire;
    }

    public List<Livre> getBookByAuthorName(String name){
        int indexSpace = name.indexOf(" ");
        String nom = "";
        String prenom = "";
        List<Livre> livres;
        if(indexSpace > 0){
            nom = name.substring(0, indexSpace);
            prenom = name.substring(indexSpace+1);
            livres = livresRepository.searchByAuteurs(nom, prenom);
        }
        livres = livresRepository.searchByAuteurs(name);
        Logger log = Logger.getLogger("");
         for(Livre l:livres){
             Exemplaire ex =  exemplaireService.getExemplaireByIsbn(l);

             log.info("exemplaire "+ ex);
        //    System.out.println("livre genre "+l.getGenres()+" isbn "+l.getIsbn());
        }

         return livres;
    }

    public List<Livre> getBookByGenre(String genre){
        return livresRepository.searchByGenre(genre);
    }
}
