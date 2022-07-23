package com.example.biblio.functions.format;

import com.example.biblio.entity.*;
import com.example.biblio.repository.IPretRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FormatTest {
    @Autowired
    private IPretRepository pretRepository;

    @Test
    public void getStatFormatOneAuteurTest(){
        Format f = new Format();
        List<Auteur> lstA = new ArrayList<>();
        List<Map<String, String>> lstAuteur = new ArrayList<>();
        List<Map<String, String>> lstAuteurTest = new ArrayList<>();
        Livre book = new Livre();
        Auteur auteur = new Auteur();
        auteur.setNom("Paquette");
        auteur.setPrenom("Gautier");
        lstA.add(auteur);
        book.setAuteurs(lstA);
        Map<String, String> mapAuteur = new HashMap<>();
        mapAuteur.put("nb", "1");
        mapAuteur.put("stats", "100");
        mapAuteur.put("nom", "Paquette");
        mapAuteur.put("prenom", "Gautier");
        lstAuteurTest.add(mapAuteur);
        assertEquals(lstAuteurTest, f.FormatAuteur(book, lstAuteur, 0));
    }
    @Test
    public void getStatFormatManyAuteurTest(){
        Format f = new Format();
        List<Auteur> lstA = new ArrayList<>();
        List<Map<String, String>> lstAuteur = new ArrayList<>();
        List<Map<String, String>> lstAuteurTest = new ArrayList<>();
        Livre book = new Livre();
        Auteur auteur = new Auteur();
        Auteur auteur2 = new Auteur();
        Auteur auteur3 = new Auteur();
        auteur.setNom("Paquette");
        auteur.setPrenom("Gautier");
        auteur2.setNom("Mortier");
        auteur2.setPrenom("Maurice");
        auteur3.setNom("Blanc");
        auteur3.setPrenom("Fabrice");
        lstA.add(auteur);
        lstA.add(auteur2);
        lstA.add(auteur3);
        book.setAuteurs(lstA);
        Map<String, String> mapAuteur = new HashMap<>();
        mapAuteur.put("nb", "1");
        mapAuteur.put("stats", "100");
        mapAuteur.put("nom", "Paquette");
        mapAuteur.put("prenom", "Gautier");
        Map<String, String> mapAuteur2 = new HashMap<>();
        mapAuteur2.put("nb", "1");
        mapAuteur2.put("stats", "100");
        mapAuteur2.put("nom", "Mortier");
        mapAuteur2.put("prenom", "Maurice");
        Map<String, String> mapAuteur3 = new HashMap<>();
        mapAuteur3.put("nb", "1");
        mapAuteur3.put("stats", "100");
        mapAuteur3.put("nom", "Blanc");
        mapAuteur3.put("prenom", "Fabrice");
        lstAuteurTest.add(mapAuteur);
        lstAuteurTest.add(mapAuteur2);
        lstAuteurTest.add(mapAuteur3);
        assertEquals(lstAuteurTest, f.FormatAuteur(book, lstAuteur, 0));
    }
    @Test
    public void getStatFormatSameAuteurTest(){
        Format f = new Format();
        List<Auteur> lstA = new ArrayList<>();
        List<Map<String, String>> lstAuteur = new ArrayList<>();
        List<Map<String, String>> lstAuteurTest = new ArrayList<>();
        Livre book = new Livre();
        Auteur auteur = new Auteur();
        Auteur auteur2 = new Auteur();
        auteur.setNom("Paquette");
        auteur.setPrenom("Gautier");
        auteur2.setNom("Paquette");
        auteur2.setPrenom("Gautier");
        lstA.add(auteur);
        lstA.add(auteur2);
        book.setAuteurs(lstA);
        Map<String, String> mapAuteur = new HashMap<>();
        mapAuteur.put("nb", "2");
        mapAuteur.put("stats", "100");
        mapAuteur.put("nom", "Paquette");
        mapAuteur.put("prenom", "Gautier");
        lstAuteurTest.add(mapAuteur);
        assertEquals(lstAuteurTest, f.FormatAuteur(book, lstAuteur, 0));
    }

    @Test
    public void getStatFormatGenreTest() {
        Format f = new Format();
        List<Genre> lstG = new ArrayList<>();
        List<Map<String, String>> lstGenre = new ArrayList<>();
        List<Map<String, String>> lstGenreTest = new ArrayList<>();
        Livre book = new Livre();
        Genre genre = new Genre();
        genre.setNom("Fantastique");
        lstG.add(genre);
        book.setGenres(lstG);
        Map<String, String> mapGenre = new HashMap<>();
        mapGenre.put("nom", "Fantastique");
        mapGenre.put("nb", "1");
        mapGenre.put("stats", "100");
        lstGenreTest.add(mapGenre);
        assertEquals(lstGenreTest, f.FormatGenre(book, lstGenre, 0));
    }

    @Test
    public void getStatFormatSameGenreTest() {
        Format f = new Format();
        List<Genre> lstG = new ArrayList<>();
        List<Map<String, String>> lstGenre = new ArrayList<>();
        List<Map<String, String>> lstGenreTest = new ArrayList<>();
        Livre book = new Livre();
        Genre genre = new Genre();
        Genre genre2 = new Genre();
        genre.setNom("Fantastique");
        genre2.setNom("Fantastique");
        lstG.add(genre);
        lstG.add(genre2);
        book.setGenres(lstG);
        Map<String, String> mapGenre = new HashMap<>();
        mapGenre.put("nom", "Fantastique");
        mapGenre.put("nb", "2");
        mapGenre.put("stats", "100");
        lstGenreTest.add(mapGenre);
        assertEquals(lstGenreTest, f.FormatGenre(book, lstGenre, 0));
    }
    @Test
    public void getStatFormatManyGenreTest() {
        Format f = new Format();
        List<Genre> lstG = new ArrayList<>();
        List<Map<String, String>> lstGenre = new ArrayList<>();
        List<Map<String, String>> lstGenreTest = new ArrayList<>();
        Livre book = new Livre();
        Genre genre = new Genre();
        Genre genre2 = new Genre();
        genre.setNom("Fantastique");
        genre2.setNom("Manga");
        lstG.add(genre);
        lstG.add(genre2);
        book.setGenres(lstG);
        Map<String, String> mapGenre = new HashMap<>();
        Map<String, String> mapGenre2 = new HashMap<>();
        mapGenre.put("nom", "Fantastique");
        mapGenre.put("nb", "1");
        mapGenre.put("stats", "100");
        mapGenre2.put("nom", "Manga");
        mapGenre2.put("nb", "1");
        mapGenre2.put("stats", "100");
        lstGenreTest.add(mapGenre);
        lstGenreTest.add(mapGenre2);
        assertEquals(lstGenreTest, f.FormatGenre(book, lstGenre, 0));
    }

    @Test
    public void getStatFormatEditeurTest() {
        Format f = new Format();
        List<Map<String, String>> lstEditeur = new ArrayList<>();
        List<Map<String, String>> lstEditeurTest = new ArrayList<>();
        Livre book = new Livre();
        Editeur editeur = new Editeur();
        editeur.setNom("Baudelaire");
        book.setEditeur(editeur);
        Map<String, String> mapEditeur = new HashMap<>();
        mapEditeur.put("nom", "Baudelaire");
        mapEditeur.put("nb", "1");
        mapEditeur.put("stats", "100");
        lstEditeurTest.add(mapEditeur);
        assertEquals(lstEditeurTest, f.FormatEditeur(book, lstEditeur, 0));
    }
    @Test
    public void getStatFormatManyEditeurTest() {
        Format f = new Format();
        List<Map<String, String>> lstEditeur = new ArrayList<>();
        List<Map<String, String>> lstEditeurTest = new ArrayList<>();
        Livre book = new Livre();
        Editeur editeur = new Editeur();
        editeur.setNom("Baudelaire");
        book.setEditeur(editeur);
        Map<String, String> mapEditeur = new HashMap<>();
        mapEditeur.put("nom", "Baudelaire");
        mapEditeur.put("nb", "1");
        mapEditeur.put("stats", "100");
        lstEditeurTest.add(mapEditeur);
        assertEquals(lstEditeurTest, f.FormatEditeur(book, lstEditeur, 0));
    }
    @Test
    public void getStatFormatSameEditeurTest() {
        Format f = new Format();
        List<Map<String, String>> lstEditeurTest = new ArrayList<>();
        Livre book = new Livre();
        Editeur editeur = new Editeur();
        editeur.setNom("Baudelaire");
        book.setEditeur(editeur);
        Map<String, String> mapEditeur = new HashMap<>();
        mapEditeur.put("nom", "Baudelaire");
        mapEditeur.put("nb", "1");
        mapEditeur.put("stats", "100");
        lstEditeurTest.add(mapEditeur);
        assertEquals(lstEditeurTest, f.FormatEditeur(book, lstEditeurTest, 0));
    }

    @Test
    public void getStatFormatLivreTest() {
        Format f = new Format();
        List<Map<String, String>> lstBook = new ArrayList<>();
        List<Map<String, String>> lstBookTest = new ArrayList<>();
        Livre book = new Livre();
        book.setIsbn("9781234567892");
        book.setTitre("Last dance");
        Map<String, String> mapBook = new HashMap<>();
        mapBook.put("nom", "Last dance");
        mapBook.put("nb", "1");
        mapBook.put("isbn", "9781234567892");
        mapBook.put("stats", "100");
        lstBookTest.add(mapBook);
        assertEquals(lstBookTest, f.FormatTitle(book, lstBook, 0));
    }
    @Test
    public void getStatFormatSameLivreTest() {
        Format f = new Format();
        List<Map<String, String>> lstBookTest = new ArrayList<>();
        Livre book = new Livre();
        book.setIsbn("9781234567892");
        book.setTitre("Last dance");
        Map<String, String> mapBook = new HashMap<>();
        mapBook.put("nom", "Last dance");
        mapBook.put("nb", "1");
        mapBook.put("isbn", "9781234567892");
        mapBook.put("stats", "100");
        lstBookTest.add(mapBook);
        assertEquals(lstBookTest, f.FormatTitle(book, lstBookTest, 0));
    }
}
