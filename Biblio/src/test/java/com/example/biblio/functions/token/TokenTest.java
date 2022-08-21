package com.example.biblio.functions.token;

import com.example.biblio.entity.Utilisateur;
import com.example.biblio.service.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.biblio.functions.token.Token;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TokenTest {
    @Autowired
    private UtilisateurService user;
    @Test
    public void genereTokenTest(){
        Utilisateur us = user.getUser("test@gmail.com");
        Token t = new Token(us);
        assertNotNull(t);



    }
}
