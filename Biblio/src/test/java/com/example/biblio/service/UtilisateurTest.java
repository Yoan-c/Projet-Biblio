package com.example.biblio.service;

import com.example.biblio.entity.Utilisateur;
import com.example.biblio.repository.IUtilisateurRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UtilisateurTest {

    @Autowired
    UtilisateurService utilisateurService;
    Map<String, String> info = new HashMap<>();

    Map<String, String> getInfoForTest(){
        Map<String, String> info = new HashMap<>();
        info.put("nom", "test2");
        info.put("mdp", "1");
        info.put("confirmMdp", "1");
        info.put("prenom", "test2");
        info.put("email", "test2");
        info.put("testMail", "testCreateUser@gmail.com");
        return info;
    }
    @Test
    void connexionUserValid(){
        boolean isConect =  utilisateurService.connexionUser("test@gmail.com", "test");
        assertTrue(isConect);
    }
    @Test
    void connexionUserInvalid(){
        boolean isConect =  utilisateurService.connexionUser("test@gmail.com", "t");
        assertFalse(isConect);
    }
    @Test
    void connexionUserNull(){
        boolean isConect =  utilisateurService.connexionUser("testm", "t");
        assertFalse(isConect);
    }
    @Test
    void deconnexionUser(){
        Utilisateur user = utilisateurService.getUser("test@gmail.com");
        boolean isConect =  utilisateurService.deconnexion(user.getHash());
        assertTrue(isConect);
    }
    @Test
    void deconnexionUserInvalid(){
        boolean isConect =  utilisateurService.deconnexion("gtre");
        assertFalse(isConect);
    }
    @Test
    @Order(1)
    void loadUserByUsernameNullTest(){
        Utilisateur user = utilisateurService.getUser("");
        assertNull(user);
    }
    @Test
    @Order(2)
    void loadUserByUsernameBadParamTest(){
        Utilisateur user = utilisateurService.getUser("sdgsdfg");
        assertNull(user);
    }
    @Test
    @Order(3)
    void loadUserByUsernameGoodParamTest(){
        Utilisateur user = utilisateurService.getUser("test@gmail.com");
        assertNotNull(user);
    }
    @Test
    @Order(4)
    void getUserValidTest(){
        Utilisateur user =  utilisateurService.getUser("test@gmail.com");
        assertEquals(user.getEmail(), "test@gmail.com");
    }
    @Test
    @Order(5)
    void getUserInvalidTest(){
        Utilisateur user =  utilisateurService.getUser("testl.com");
        assertNull(user);
    }
    @Test
    @Order(6)
    void creatUserInvalidTest(){
        info = getInfoForTest();
        boolean isCreate =  utilisateurService.createUser(info);
        assertFalse(isCreate);
    }
    @Test
    @Order(7)
    void creatUserinValidMdpTest(){
        info = getInfoForTest();
        info.put("mdp", "toto");
        info.put("confirmMdp", "tata");
        boolean success  =  utilisateurService.createUser(info);
        assertFalse(success);
    }
    @Test
    @Order(8)
    void creatUserAlreadyExistTest(){
        info = getInfoForTest();
        info.put("email", "test@gmail.com");
        boolean isCreate =  utilisateurService.createUser(info);
        assertFalse(isCreate);
    }
    @Order(9)
    @Test
    void creatUserValidTest(){
        info = getInfoForTest();
        info.put("email", info.get("testMail"));
        boolean isCreate =  utilisateurService.createUser(info);
        assertTrue(isCreate);
    }
    @Test
    @Order(10)
    void updateUserDataInvalidTest(){
        info = getInfoForTest();
        Utilisateur user = utilisateurService.getUser(info.get("testMail"));
        String msg =  utilisateurService.updateUser(info, "testgmail.com", user);
        assertEquals("0;Erreur : Vérifier les champs avant de valider", msg);
    }
    @Test
    @Order(11)
    void updateUserNotFoundTest(){
        info = getInfoForTest();
        Utilisateur user = utilisateurService.getUser(info.get("testMail"));
        info.put("mail", info.get("testMail"));
        String msg =  utilisateurService.updateUser(info, "t@gmail.com", user);
        assertEquals("0;Erreur : Utilisateur non trouvé", msg);
    }
    @Test
    @Order(12)
    void updateUserBadNameTest(){
        info = getInfoForTest();
        info.put("mail", info.get("testMail"));
        Utilisateur user = utilisateurService.getUser(info.get("testMail"));
        info.put("nom", null);
        String msg =  utilisateurService.updateUser(info, info.get("testMail"), user);
        assertEquals("0;Erreur : Vérifier les champs avant de valider", msg);
    }
    @Test
    @Order(13)
    void updateUserBadMdpTest(){
        info = getInfoForTest();
        Utilisateur user = utilisateurService.getUser(info.get("testMail"));
        info.put("mail", info.get("testMail"));
        info.put("mdp", "test");
        String msg =  utilisateurService.updateUser(info, info.get("testMail"), user);
        assertEquals("0;Erreur : Vérifier le mot de passe et la confirmation du mot de passe", msg);
    }

    @Test
    @Order(14)
    void updateUserMdpNotFoundTest(){
        info = getInfoForTest();
        info.put("mail", info.get("testMail"));
        Utilisateur user = utilisateurService.getUser(info.get("testMail"));
        info.put("mdp", "");
        String msg =  utilisateurService.updateUser(info, info.get("testMail"), user);
        assertEquals("0;Erreur : Veuillez remplir le mot de passe et la confirmation", msg);
    }
    @Test
    @Order(15)
    void updateUserMailAlreadyUseTest(){
        info = getInfoForTest();
        info.put("mail", info.get("testMail"));
        Utilisateur user = utilisateurService.getUser(info.get("testMail"));
        String msg =  utilisateurService.updateUser(info, "test@gmail.com", user);
        assertEquals("0;Erreur : Un utilisateur utilise déjà cet email", msg);
    }
    @Test
    @Order(16)
    void updateUserMailNotValidTest(){
        info = getInfoForTest();
        info.put("mail", "testgmail.com");
        Utilisateur user = utilisateurService.getUser(info.get("testMail"));
        String msg =  utilisateurService.updateUser(info, "test2@gmail.com", user);
        assertEquals("0;Erreur : Format d'email invalid", msg);
    }
    @Test
    @Order(17)
    void updateUserValidTest(){
        info = getInfoForTest();
        Utilisateur user = utilisateurService.getUser(info.get("testMail"));
        info.put("mail", info.get("testMail"));
        info.put("nom", "Marie");
        String msg =  utilisateurService.updateUser(info, info.get("testMail"), user);
        String msgs = msg.split(";")[0]+";"+msg.split(";")[1];
        assertEquals("1;Votre compte à été modifié", msgs);
    }

    @Test
    void deleteUserTest(){
        info = getInfoForTest();
        Utilisateur user =  utilisateurService.getUser(info.get("testMail"));
        utilisateurService.delUser(user);
    }
    @Test
    void verifDataIsEmptyTest(){
        UtilisateurService utilisateurService = new UtilisateurService();
        assertFalse(utilisateurService.verifData(""));
    }
    @Test
    void verifDataIsNullTest(){
        UtilisateurService utilisateurService = new UtilisateurService();
        assertFalse(utilisateurService.verifData(null));
    }
    @Test
    void verifDataIsNotNullTest(){
        UtilisateurService utilisateurService = new UtilisateurService();
        assertTrue(utilisateurService.verifData("test"));
    }
    @Test
    void verifDataIsBlankTest(){
        UtilisateurService utilisateurService = new UtilisateurService();
        assertFalse(utilisateurService.verifData("   "));
    }
    @Test
    void verifTokenTest(){
        boolean t = utilisateurService.isValidToken("test");
        assertFalse(t);
    }
    @Test
    void getHashTest(){
        String hash = utilisateurService.getHashUser("test@gmail.com");
        assertNotNull(hash);
    }

}
