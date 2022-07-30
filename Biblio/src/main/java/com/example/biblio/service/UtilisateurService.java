package com.example.biblio.service;

import com.example.biblio.entity.Utilisateur;
import com.example.biblio.functions.hash.Empreinte;
import com.example.biblio.functions.token.Token;
import com.example.biblio.repository.IUtilisateurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UtilisateurService {

    @Autowired
    IUtilisateurRepository userRepository;
    private static Pattern pattern;
    private static Matcher matcher;

    public boolean connexionUser(String mail, String mdp) {
        Utilisateur user = userRepository.getUtilisateurByMail(mail);
        if(user == null)
            return false;
        return isCorrectPassword(mail, mdp);
    }
    public boolean deconnexion(String token){
        Utilisateur user = userRepository.getUtilisateurByToken(token);
        if (user != null){
            user.setConnect(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    public boolean isCorrectPassword(String mail, String password){ //renvoie vrai si le couple utilisateur/mot de passe est correct
        Utilisateur user = userRepository.getUtilisateurByMail(mail);
        String salt = Empreinte.getSalt(64);
        if(user != null){
            boolean isValid = Empreinte.isValidPassword(user.getHash(), password+user.getValidity().minusMinutes(15).getDayOfMonth(), user.getSalt());
            if(isValid) {
                user.setHash(Empreinte.getHashSalt(password+LocalDateTime.now().getDayOfMonth() ,  salt));
                user.setSalt(salt);
                user.setValidity(LocalDateTime.now().plusMinutes(15));
                user.setConnect(true);
                userRepository.save(user);
                return true;
            }
        }
        return  false;
    }
    public boolean createUser(Map<String, String> info) {
        boolean isMatch;
        pattern = Pattern.compile("^[\\w\\.]{3,20}@([\\w-]{2,20}\\.)[\\w-]{2,4}$");
        matcher = pattern.matcher(info.get("email"));
        isMatch = matcher.matches();
        if (info.get("nom") == null || info.get("mdp") == null || info.get("email") == null || info.get("prenom") == null
        || info.get("confirmMdp") == null || !info.get("mdp").equals(info.get("confirmMdp"))) {
            return false;
        }
        Utilisateur user = userRepository.getUtilisateurByMail(info.get("email"));
        if(user != null)
            return false;
        if(!isMatch)
            return false;
        String salt = Empreinte.getSalt(64);
        LocalDateTime now = LocalDateTime.now();
        Utilisateur newUser = new Utilisateur(info.get("email"), info.get("nom"), info.get("prenom"), Empreinte.getHashSalt(info.get("mdp")+now.getDayOfMonth() ,  salt), salt, now.plusMinutes(15) );
        userRepository.save(newUser);
        return true;
    }

    public Utilisateur getUser(String email){
        Utilisateur user = userRepository.getUtilisateurByMail(email);
        return user;
    }
    public void delUser(Utilisateur user){
         userRepository.deleteById(user.getId());
    }

    public String updateUser(Map<String, String> infoUser, String mail, Utilisateur userModif){

        boolean changeMail = false;
        if (!verifData(infoUser.get("nom")) || !verifData(infoUser.get("prenom")) || !verifData(infoUser.get("mail")))
            return "0;Erreur : Vérifier les champs avant de valider";

        Utilisateur user = userRepository.getUtilisateurByMail(mail);
        if (user == null)
            return "0;Erreur : Utilisateur non trouvé";
        user.setNom(infoUser.get("nom"));
        user.setPrenom(infoUser.get("prenom"));
        if(verifData(infoUser.get("mdp")) && verifData(infoUser.get("confirmMdp"))){
            if (infoUser.get("mdp").equals(infoUser.get("confirmMdp"))){
                String salt = Empreinte.getSalt(64);
                user.setHash(Empreinte.getHashSalt(infoUser.get("mdp")+LocalDateTime.now().getDayOfMonth() ,  salt));
                user.setSalt(salt);
                user.setValidity(LocalDateTime.now().plusMinutes(15));
                user.setConnect(true);
            }
            else
                return "0;Erreur : Vérifier le mot de passe et la confirmation du mot de passe";
        }
        else
            return "0;Erreur : Veuillez remplir le mot de passe et la confirmation";
        if (verifData(infoUser.get("mail")) && !infoUser.get("mail").equals(mail)){
            boolean isMatch;
            pattern = Pattern.compile("^[\\w\\.]{3,20}@([\\w-]{2,20}\\.)[\\w-]{2,4}$");
            matcher = pattern.matcher(infoUser.get("mail"));
            isMatch = matcher.matches();
            if (isMatch){
                Utilisateur checkUser = userRepository.getUtilisateurByMail(infoUser.get("mail"));
                if(checkUser == null){
                    user.setEmail(infoUser.get("mail"));
                    changeMail = true;
                }

                else
                    return "0;Erreur : Un utilisateur utilise déjà cet email";
            }
            else
                return "0;Erreur : Format d'email invalid";
        }
        userRepository.save(user);
        userModif = user;
        if (changeMail)
            return "2;mail changé;"+userModif.getHash();
        else
            return "1;Votre compte à été modifié;"+userModif.getHash();
    }

    public boolean verifData(String data) {
        if (data == null)
            return false;
        if (data.isEmpty())
            return false;
        if (data.isBlank())
            return false;
        return true;
    }

    public String getHashUser(String email){
        Utilisateur user = userRepository.getUtilisateurByMail(email);
        return user.getHash();
    }
    public Utilisateur getUserByToken(String token){
        return userRepository.getUtilisateurByToken(token);
    }

    public boolean isValidToken(String token){ //Renvoie vrai si le Token aossicé à l'identifiant est valide, faux sinon
        Utilisateur user = userRepository.getUtilisateurByToken(token);
        if (user != null) {
            if (LocalDateTime.now().isBefore(user.getValidity())){
                if (user.isConnect())
                    return true;
            }
            else {
                user.setConnect(false);
                userRepository.save(user);
            }
        }
        return false;
    }
}
