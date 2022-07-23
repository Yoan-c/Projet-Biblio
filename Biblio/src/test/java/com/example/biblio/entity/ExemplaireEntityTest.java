package com.example.biblio.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ExemplaireEntityTest {
    @Test
    public void ExemplaireNomTest(){
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setId(1L);
        exemplaire.setIsbn(new Livre());
        exemplaire.setStock(0);
        assertEquals(0, exemplaire.getStock());
    }
    @Test
    public void ExemplaireToStringTest(){
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setId(1L);
        exemplaire.setIsbn(new Livre());
        exemplaire.setStock(0);
        Exemplaire exemplaire2 = new Exemplaire(1L, new Livre(), 0);
        assertEquals(exemplaire2.toString(), exemplaire.toString());
    }
}
