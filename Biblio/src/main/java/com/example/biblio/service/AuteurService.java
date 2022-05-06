package com.example.biblio.service;

import com.example.biblio.entity.Livre;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuteurService {

    public List<String> getNameFormat(String name){
        int indexSpace = name.indexOf(" ");
        String nom = "";
        String prenom = "";
        List<String> lstName = new ArrayList<>();
        if(indexSpace > 0){
            nom = name.substring(0, indexSpace);
            lstName.add(nom);
            prenom = name.substring(indexSpace+1);
            lstName.add(prenom);
        }
        else {
            lstName.add(name);
            lstName.add("");
        }
        return lstName;
    }
}
