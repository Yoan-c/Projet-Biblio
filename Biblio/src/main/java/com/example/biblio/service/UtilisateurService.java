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

@Service
@Slf4j
public class UtilisateurService implements UserDetailsService  {

    @Autowired
    IUtilisateurRepository userRepository;

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

}