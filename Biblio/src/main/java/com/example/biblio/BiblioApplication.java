package com.example.biblio;

import com.example.biblio.entity.Livre;
import com.example.biblio.repository.ILivreRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class BiblioApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BiblioApplication.class, args);
    }

}
