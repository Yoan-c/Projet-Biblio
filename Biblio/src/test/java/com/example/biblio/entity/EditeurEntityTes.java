package com.example.biblio.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EditeurEntityTes {
    @Test
    public void EditeurIdTest(){
        Editeur editeur = new Editeur();
        editeur.setNom("Hachette");
        Long id = 5L;
        editeur.setId(id);
        assertEquals(5L, editeur.getId());
    }
    @Test
    public void EditeurToStringTest(){
        Editeur editeur = new Editeur();
        editeur.setNom("Hachette");
        Long id = 5L;
        editeur.setId(id);
        // resultat
        Editeur editeurTest = new Editeur(5L, "Hachette");
        assertEquals(editeurTest.toString(), editeur.toString());
    }
}
