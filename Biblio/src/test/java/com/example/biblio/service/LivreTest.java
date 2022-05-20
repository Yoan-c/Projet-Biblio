package com.example.biblio.service;

import com.example.biblio.entity.Exemplaire;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LivreTest {

    @Autowired
    LivreService livreService;

    @Test
    void searchTitleTest(){
        Map<String, String> search = new HashMap<>();
        search.put("titre","Last dance");
        List<Exemplaire> lstExemplaire = livreService.search(search);
        assertEquals(1, lstExemplaire.get(0).getId());
    }
    @Test
    void searchGenreTest(){
        Map<String, String> search = new HashMap<>();
        search.put("genre","Manga");
        List<Exemplaire> lstExemplaire = livreService.search(search);
        assertEquals(1, lstExemplaire.get(0).getId());
    }
}
