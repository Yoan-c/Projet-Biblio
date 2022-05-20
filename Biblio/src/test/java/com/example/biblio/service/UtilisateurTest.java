package com.example.biblio.service;

import com.example.biblio.entity.Utilisateur;
import com.example.biblio.repository.IUtilisateurRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        return info;
    }
    @Test
    @Order(1)
    void loadUserByUsernameNullTest(){
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            utilisateurService.loadUserByUsername("");
        });
        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    @Order(2)
    void loadUserByUsernameBadParamTest(){
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            utilisateurService.loadUserByUsername("terherth");
        });
        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    @Order(3)
    void loadUserByUsernameGoodParamTest(){
        UserDetails user =  utilisateurService.loadUserByUsername("test@gmail.com");
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
        boolean isCreate =  utilisateurService.createUser(info);
        assertFalse(isCreate);
    }
    @Test
    @Order(8)
    void creatUserAlreadyExistTest(){
        info = getInfoForTest();
        info.put("mail", "test@gmail.com");
        boolean isCreate =  utilisateurService.createUser(info);
        assertFalse(isCreate);
    }
    @Order(9)
    @Test
    void creatUserValidTest(){
        info = getInfoForTest();
        info.put("email", "test2@gmail.com");
        boolean isCreate =  utilisateurService.createUser(info);
        assertTrue(isCreate);
    }
    @Test
    @Order(10)
    void updateUserDataInvalidTest(){
        info = getInfoForTest();
        String msg =  utilisateurService.updateUser(info, "test2@gmail.com");
        assertEquals("0;Erreur : Vérifier les champs avant de valider", msg);
    }
    @Test
    @Order(11)
    void updateUserNotFoundTest(){
        info = getInfoForTest();
        info.put("mail", "test2@gmail.com");
        String msg =  utilisateurService.updateUser(info, "t@gmail.com");
        assertEquals("0;Erreur : Utilisateur non trouvé", msg);
    }
    @Test
    @Order(12)
    void updateUserBadNameTest(){
        info = getInfoForTest();
        info.put("mail", "test2@gmail.com");
        info.put("nom", null);
        String msg =  utilisateurService.updateUser(info, "test2@gmail.com");
        assertEquals("0;Erreur : Vérifier les champs avant de valider", msg);
    }
    @Test
    @Order(13)
    void updateUserBadMdpTest(){
        info = getInfoForTest();
        info.put("mail", "test2@gmail.com");
        info.put("mdp", "test");
        String msg =  utilisateurService.updateUser(info, "test2@gmail.com");
        assertEquals("0;Erreur : Vérifier le mot de passe et la confirmation du mot de passe", msg);
    }

    @Test
    @Order(14)
    void updateUserMdpNotFoundTest(){
        info = getInfoForTest();
        info.put("mail", "test2@gmail.com");
        info.put("mdp", "");
        String msg =  utilisateurService.updateUser(info, "test2@gmail.com");
        assertEquals("0;Erreur : Veuillez remplir le mot de passe et la confirmation", msg);
    }
    @Test
    @Order(14)
    void updateUserMailAlreadyUseTest(){
        info = getInfoForTest();
        info.put("mail", "test@gmail.com");
        String msg =  utilisateurService.updateUser(info, "test2@gmail.com");
        assertEquals("0;Erreur : Un utilisateur utilise déjà cet email", msg);
    }
    @Test
    @Order(15)
    void updateUserMailNotValidTest(){
        info = getInfoForTest();
        info.put("mail", "testgmail.com");
        String msg =  utilisateurService.updateUser(info, "test2@gmail.com");
        assertEquals("0;Erreur : Format d'email invalid", msg);
    }
    @Test
    @Order(15)
    void updateUserValidTest(){
        info = getInfoForTest();
        info.put("mail", "test2@gmail.com");
        info.put("nom", "Marie");
        String msg =  utilisateurService.updateUser(info, "test2@gmail.com");
        assertEquals("1;Votre compte à été modifié", msg);
    }

    @Test
    void deleteUserTest(){
        Utilisateur user =  utilisateurService.getUser("test2@gmail.com");
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
}
