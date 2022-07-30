package com.example.biblio.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    private String hash;
    private String salt;
    private LocalDateTime validity;
    boolean isConnect ;


    public Utilisateur(String email, String nom, String prenom, String hash, String salt, LocalDateTime now) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.hash = hash;
        this.salt = salt;
        this.validity = now;
        this.isConnect = false;
    }
}
