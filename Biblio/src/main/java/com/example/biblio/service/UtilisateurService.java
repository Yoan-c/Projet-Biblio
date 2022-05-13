package com.example.biblio.service;

import com.example.biblio.entity.Utilisateur;
import com.example.biblio.repository.IUtilisateurRepository;
import com.example.biblio.security.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UtilisateurService implements UserDetailsService  {

    @Autowired
    IUtilisateurRepository userRepository;
    private static Pattern pattern;
    private static Matcher matcher;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Utilisateur user = userRepository.getUtilisateurByMail(mail);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }

    public boolean createUser(Map<String, String> info) {
        if (info.get("nom") == null || info.get("mdp") == null || info.get("email") == null || info.get("prenom") == null
        || info.get("confirmMdp") == null || !info.get("mdp").equals(info.get("confirmMdp"))) {
            return false;
        }
        Utilisateur user = userRepository.getUtilisateurByMail(info.get("email"));
        if(user != null)
            return false;
        Utilisateur newUser = new Utilisateur(info.get("email"), info.get("nom"), info.get("prenom"), info.get("mdp"));
        userRepository.save(newUser);
        return true;
    }

    public Utilisateur getUser(String email){
        Utilisateur user = userRepository.getUtilisateurByMail(email);
        return user;
    }



    public String updateUser(Map<String, String> infoUser, String mail){


        if (!verifData(infoUser.get("nom")) || !verifData(infoUser.get("prenom")) || !verifData(infoUser.get("mail")))
            return "0;Erreur : Vérifier les champs avant de valider";

        Utilisateur user = userRepository.getUtilisateurByMail(mail);
        if (user == null)
            return "0;Erreur : Utilisateur non trouvé";

        if (verifData(infoUser.get("nom")))
            user.setNom(infoUser.get("nom"));
        else
            return "0;Erreur : Vérifier le nom";
        if (verifData(infoUser.get("prenom")))
            user.setPrenom(infoUser.get("prenom"));
        else
            return "0;Erreur : Vérifier le prenom";
        if(verifData(infoUser.get("mdp")) && verifData(infoUser.get("confirmMdp"))){
            if (infoUser.get("mdp").equals(infoUser.get("confirmMdp"))){
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String mdp = encoder.encode(infoUser.get("mdp"));
                user.setMdp(mdp);
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
                if(checkUser == null)
                    user.setEmail(infoUser.get("mail"));
                else
                    return "0;Erreur : Un utilisateur utilise déjà cet email";
            }
            else
                return "0;Erreur : Format d'email invalid";

        }
        userRepository.save(user);
        return "1;Votre compte à été modifié";
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
}
