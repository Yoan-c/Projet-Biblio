package com.example.biblio.functions.token;

import com.example.biblio.entity.Utilisateur;

import java.time.LocalDateTime;

public class Token {
    private String email;
    private LocalDateTime validity;


    private  void generateValidity() {//génère une date de fin de validité. La ligne commentée est celle qui devrait être utilisé en théorie, mais pour faire des tests, il vaut mieux utiliser une validité plus courte.
        //this.validity = LocalDateTime.now().plusHours(24); //La validité de notre Token est de 24 heures
        this.validity = LocalDateTime.now().plusSeconds(4); //La validité de notre Token est de 4 secondes...
    }

    public Token(Utilisateur user) {
        this.email = user.getEmail();
        generateValidity();
    }

    public boolean isValid(){
        return LocalDateTime.now().isBefore(validity);
    }
}
