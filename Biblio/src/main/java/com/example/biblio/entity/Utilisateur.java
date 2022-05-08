package com.example.biblio.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Utilisateur implements Serializable{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nom;
    private String prenom;
    private String mdp;


    public Utilisateur(String email, String nom, String prenom, String mdp) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String formatMdp = encoder.encode(mdp);
        this.mdp = formatMdp;
    }
}