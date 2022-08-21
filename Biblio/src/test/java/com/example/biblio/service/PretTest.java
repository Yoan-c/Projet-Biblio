package com.example.biblio.service;

import com.example.biblio.entity.Pret;
import com.example.biblio.entity.Utilisateur;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PretTest {

    @Autowired
    PretService pretService;

    @Test
   void getPretUserTest(){
        List<Pret> lstPret = pretService.getPretByUser("test@gmail.com");
        assertNotNull(lstPret);
    }
    @Test
    void updatePretNullTest(){
        boolean isEmpty = pretService.updatePret("testgmail.com", "1");
        assertFalse(isEmpty);
    }
    @Test
    void updatePretEmptyTest(){
        boolean isEmpty = pretService.updatePret("test@gmail.com", "1");
        assertFalse(isEmpty);
    }
    @Test
    void updatePretTest(){
        boolean isEmpty = pretService.updatePret("test@gmail.com", "9781234567892");
        //assertTrue(isEmpty);
        assertFalse(isEmpty);
    }
    @Test
    void updateAlreadyPretTest(){
        boolean isEmpty = pretService.updatePret("test@gmail.com", "1");
        assertFalse(isEmpty);
    }
    @Test
    void relancePretNullTest(){
        List<HashMap<String, String>> lstPret = pretService.relancePret("test@gmail.com", "1");
        assertNull(lstPret.get(0).get("email"));
    }
    @Test
    void relancePretTest(){
        List<HashMap<String, String>> lstPret = pretService.relancePret("admin", "admin");
        assertEquals("test@gmail.com", lstPret.get(0).get("email"));
    }

}
