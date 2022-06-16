package com.example.biblio.functions.format;

import com.example.biblio.entity.*;

import java.util.*;
import java.util.logging.Logger;

public class Format {


    public List<Map<String, String>> FormatAuteur(Livre book, List<Map<String, String>> lstAuteur, int total){
        Collection<Auteur> auteurList = book.getAuteurs();
        for (Auteur newAuteur : auteurList) {
            Map<String, String> auteur = new HashMap<>();
            if (lstAuteur.isEmpty()) {
                auteur.put("nom", newAuteur.getNom());
                auteur.put("prenom", newAuteur.getPrenom());
                auteur.put("nb", "1");
                auteur.put("stats", "100");
                lstAuteur.add(auteur);
            } else {
                boolean add = false;
                for (int i = 0; i < lstAuteur.size(); i++) {
                    if (lstAuteur.get(i).get("nom").equals(newAuteur.getNom()) && lstAuteur.get(i).get("prenom")
                            .equals(newAuteur.getPrenom())) {
                        int nb = Integer.valueOf(lstAuteur.get(i).get("nb"));
                        nb++;
                        lstAuteur.get(i).put("nb", String.valueOf(nb));
                        add = true;
                    }
                }
                if (!add) {
                    auteur.put("nom", newAuteur.getNom());
                    auteur.put("prenom", newAuteur.getPrenom());
                    auteur.put("nb", "1");
                    auteur.put("stats", "100");
                    lstAuteur.add(auteur);
                }
            }
        }
        return lstAuteur;
    }

    public List<Map<String, String>> FormatGenre(Livre book, List<Map<String, String>> lstGenre, int total){
        Collection<Genre> genreList = book.getGenres();
        for (Genre newGenre : genreList) {
            Map<String, String> genre = new HashMap<>();
            if (lstGenre.isEmpty()){
                genre.put("nom", newGenre.getNom());
                genre.put("nb", "1");
                genre.put("stats", "100");
                lstGenre.add(genre);
            }
            else {
                boolean add = false;
                for (int i = 0 ; i < lstGenre.size(); i++){
                    if (lstGenre.get(i).get("nom").equals(newGenre.getNom())) {
                        int nb = Integer.valueOf(lstGenre.get(i).get("nb"));
                        nb ++;
                        lstGenre.get(i).put("nb", String.valueOf(nb));
                        add = true;
                    }
                }
                if (!add){
                    genre.put("nom", newGenre.getNom());
                    genre.put("nb", "1");
                    genre.put("stats", "100");
                    lstGenre.add(genre);
                }
            }
        }
        return lstGenre;
    }

    public List<Map<String, String>> FormatEditeur(Livre book, List<Map<String, String>> lstEditeur, int total){
        Editeur editeur = book.getEditeur();
        Map<String, String> editeurMap = new HashMap<>();

        boolean add = false;
        for (int i = 0 ; i < lstEditeur.size(); i++){
            if (lstEditeur.get(i).get("nom").equals(editeur.getNom())) {
                int nb = Integer.valueOf(lstEditeur.get(i).get("nb"));
                nb ++;
                lstEditeur.get(i).put("nb", String.valueOf(nb));
                add = true;
            }
        }
        if (!add){
            editeurMap.put("nom", editeur.getNom());
            editeurMap.put("nb", "1");
            editeurMap.put("stats", "100");
            lstEditeur.add(editeurMap);
        }
        return lstEditeur;
    }

    public List<Map<String, String>> FormatTitle(Livre book, List<Map<String, String>> lstLivre, int total){
        String isbn = book.getIsbn();
        String title = book.getTitre();
        Map<String, String> livre = new HashMap<>();
        boolean add = false;
        for (int i = 0 ; i < lstLivre.size(); i++){
            if (lstLivre.get(i).get("nom").equals(title) && lstLivre.get(i).get("isbn").equals(isbn)) {
                int nb = Integer.valueOf(lstLivre.get(i).get("nb"));
                nb ++;
                lstLivre.get(i).put("nb", String.valueOf(nb));
                add = true;
            }
        }
        if (!add){
            livre.put("nom", title);
            livre.put("isbn", isbn);
            livre.put("nb", "1");
            livre.put("stats", "100");
            lstLivre.add(livre);
        }
        return lstLivre;
    }

}
