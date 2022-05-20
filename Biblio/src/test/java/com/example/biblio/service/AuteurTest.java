package com.example.biblio.service;

import com.example.biblio.entity.Pret;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuteurTest {

    @Autowired
    AuteurService auteurService;

    @Test
   void getNameFormatNomTest(){
        List<String> lstName = auteurService.getNameFormat("Jean Martin");
        assertEquals("Jean", lstName.get(0));
    }
    @Test
    void getNameFormatPrenomTest(){
        List<String> lstName = auteurService.getNameFormat("Jean Martin");
        assertEquals("Martin", lstName.get(1));
    }
    @Test
    void getNameFormatBadParamTest(){
        List<String> lstName = auteurService.getNameFormat("");
        assertEquals("", lstName.get(0));
    }
    @Test
    void getNameFormatBadParamNameTest(){
        List<String> lstName = auteurService.getNameFormat("JeanMartin");
        assertEquals("JeanMartin", lstName.get(0));
    }
}
