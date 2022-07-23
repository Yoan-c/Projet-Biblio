package com.example.biblio.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LangueTest {
    @Test
    public void LangueNomTest(){
        Langue langue = new Langue();
        langue.setNom("Francais");
        assertEquals("Francais", langue.getNom());
    }
    @Test
    public void LangueToStringTest(){
        Langue langue = new Langue();
        langue.setNom("Francais");
        // resultat
        Langue langueTest = new Langue("Francais");
        assertEquals(langueTest.toString(), langue.toString());
    }
}
