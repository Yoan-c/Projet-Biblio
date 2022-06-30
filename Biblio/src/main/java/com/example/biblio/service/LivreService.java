package com.example.biblio.service;

import com.example.biblio.entity.Exemplaire;
import com.example.biblio.entity.Livre;
import com.example.biblio.repository.IExemplaireRepository;
import com.example.biblio.repository.ILivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LivreService {
    @Autowired
    private ILivreRepository livresRepository;
    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private AuteurService auteurService;

    /*
    fonction qui retourne tout les livres et ses informations
     */
    public List<Exemplaire> getBook(){
        List<Livre> lstLivre = livresRepository.findAll();
        List<Exemplaire> lstExemplaire = new ArrayList<Exemplaire>();
        for(Livre l:lstLivre){
            Exemplaire ex =  exemplaireService.getExemplaireByIsbn(l);
            lstExemplaire.add(ex);
        }
        return lstExemplaire;
    }



    /*
        fontion parametre : map d'information de recherche ex "search.get("title")
        et retour une liste de livre avec toute les informations (stock, genre, auteur etc...)
     */
    public List<Exemplaire> search(Map<String, String> search){
        String tagSearch = "%";
        String titre = tagSearch;
        List<String> auteur = new ArrayList<>();
        String genre = tagSearch;
        String langue = tagSearch;

        if (!search.isEmpty()){
            if (search.get("titre") != null)
                titre = search.get("titre");
            titre += tagSearch;
            if (search.get("auteur")!= null)
                auteur = auteurService.getNameFormat(search.get("auteur"));
            else {
                auteur.add(tagSearch);
                auteur.add(tagSearch);
            }
            if (search.get("genre") != null)
                genre = search.get("genre");
            genre += tagSearch;
            if (search.get("langue") != null)
                langue = search.get("langue");
            langue += tagSearch;
            List<Livre> livres = livresRepository.search(genre, auteur.get(0)+"%", auteur.get(1)+"%", titre, langue);
            List<Livre> newLstLivre = getUniqueLivre(livres);
            List<Exemplaire> lstExemplaire = new ArrayList<>();
            for (Livre l:newLstLivre){
                lstExemplaire.add(exemplaireService.getExemplaireByIsbn(l));
            }

            return lstExemplaire;
        }
        return getBook();
    }

    /*
     fonction qui prend une liste de livre en parametre
     et retourne une liste de livre sans doublon
     */
    public List<Livre> getUniqueLivre(List<Livre> livres){
        List<Livre> lstLivre = new ArrayList<>();
        int i = 0;
        boolean find;
        int size = livres.size();
        int j = 0;
        for (i = 0 ; i < size ; i++){
            find = false;
            for(j = 0; j < lstLivre.size(); j++){
                if (livres.get(i).getIsbn().equals(lstLivre.get(j).getIsbn())){
                    find = true;
                }
            }
            if (!find){
                lstLivre.add(livres.get(i));
            }
        }
        return lstLivre;
    }
}
