package com.example.biblio.functions.stats;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StatTest {
    @Test
    public void getStatTest(){
        Map<String, String> mapAuteur = new HashMap<>();
        Map<String, String> mapAuteur2 = new HashMap<>();
        List<Map<String, String>> lstAuteurTest = new ArrayList<>();
        mapAuteur.put("nb", "1");
        mapAuteur.put("stats", "100");
        mapAuteur.put("nom", "Paquette");
        mapAuteur.put("prenom", "Gautier");
        mapAuteur2.put("nb", "1");
        mapAuteur2.put("stats", "100");
        mapAuteur2.put("nom", "Paquette");
        mapAuteur2.put("prenom", "Gautier");
        lstAuteurTest.add(mapAuteur);
        lstAuteurTest.add(mapAuteur2);
        // resultat
        List<Map<String, String>> lstAuteurTest2 = new ArrayList<>();
        Map<String, String> mapAuteur3 = new HashMap<>();
        Map<String, String> mapAuteur4 = new HashMap<>();
        mapAuteur3.put("nb", "1");
        mapAuteur3.put("stats", "50,00");
        mapAuteur3.put("nom", "Paquette");
        mapAuteur3.put("prenom", "Gautier");
        mapAuteur4.put("nb", "1");
        mapAuteur4.put("stats", "50,00");
        mapAuteur4.put("nom", "Paquette");
        mapAuteur4.put("prenom", "Gautier");
        lstAuteurTest2.add(mapAuteur3);
        lstAuteurTest2.add(mapAuteur4);


        List<Map<String, String>> lstGenreTest = new ArrayList<>();
        Map<String, String> mapGenre = new HashMap<>();

        mapGenre.put("nom", "Fantastique");
        mapGenre.put("nb", "1");
        mapGenre.put("stats", "100");
        lstGenreTest.add(mapGenre);

        // resultat
        List<Map<String, String>> lstGenreTest2 = new ArrayList<>();
        Map<String, String> mapGenre2 = new HashMap<>();
        mapGenre2.put("nom", "Fantastique");
        mapGenre2.put("nb", "1");
        mapGenre2.put("stats", "100,00");
        lstGenreTest2.add(mapGenre2);


        List<Map<String, String>> lstEditeurTest = new ArrayList<>();
        Map<String, String> mapEditeur = new HashMap<>();
        mapEditeur.put("nom", "Baudelaire");
        mapEditeur.put("nb", "1");
        mapEditeur.put("stats", "100");
        lstEditeurTest.add(mapEditeur);
        //resultat
        List<Map<String, String>> lstEditeurTest2 = new ArrayList<>();
        Map<String, String> mapEditeur2 = new HashMap<>();
        mapEditeur2.put("nom", "Baudelaire");
        mapEditeur2.put("nb", "1");
        mapEditeur2.put("stats", "50,00");
        lstEditeurTest2.add(mapEditeur2);


        List<Map<String, String>> lstBookTest = new ArrayList<>();
        Map<String, String> mapBook = new HashMap<>();
        mapBook.put("nom", "Last dance");
        mapBook.put("nb", "1");
        mapBook.put("isbn", "9781234567892");
        mapBook.put("stats", "100");
        lstBookTest.add(mapBook);
        Map<String, Object> lstStat = new HashMap<>();

        //resultat
        List<Map<String, String>> lstBookTest2 = new ArrayList<>();
        Map<String, String> mapBook2 = new HashMap<>();
        mapBook2.put("nom", "Last dance");
        mapBook2.put("nb", "1");
        mapBook2.put("isbn", "9781234567892");
        mapBook2.put("stats", "50,00");
        lstBookTest2.add(mapBook2);

        lstStat.put("total",2);
        lstStat.put("auteur",lstAuteurTest);
        lstStat.put("genre",lstGenreTest);
        lstStat.put("editeur",lstEditeurTest);
        lstStat.put("livre",lstBookTest);
        Stats stats = new Stats();
        lstStat = stats.calcStat(lstStat);
        //resultat
        Map<String, Object> lstStatTest = new HashMap<>();
        lstStatTest.put("total",2);
        lstStatTest.put("auteur",lstAuteurTest2);
        lstStatTest.put("genre",lstGenreTest2);
        lstStatTest.put("editeur",lstEditeurTest2);
        lstStatTest.put("livre",lstBookTest2);

        assertEquals(lstStatTest, lstStat);
    }
}
