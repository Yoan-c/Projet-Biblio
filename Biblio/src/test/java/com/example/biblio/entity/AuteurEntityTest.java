package com.example.biblio.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuteurEntityTest {

    @Test
    public void AuteurIdTest(){
        Auteur auteur = new Auteur();
        auteur.setNom("Folin");
        auteur.setPrenom("Bertrand");
        Long id = 50L;
        auteur.setId(id);
        assertEquals(50L, auteur.getId());
    }
    @Test
    public void AuteurToStringTest(){
        Auteur auteur = new Auteur();
        auteur.setNom("Folin");
        auteur.setPrenom("Bertrand");
        Long id = 50L;
        auteur.setId(id);
        // resultat
        Auteur auteurTest = new Auteur(50L, "Folin", "Bertrand");
        assertEquals(auteurTest.toString(), auteur.toString());
    }
}
