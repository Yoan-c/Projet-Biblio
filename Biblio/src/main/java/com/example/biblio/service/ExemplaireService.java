package com.example.biblio.service;

import com.example.biblio.entity.Exemplaire;
import com.example.biblio.entity.Livre;
import com.example.biblio.repository.IExemplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExemplaireService {
    @Autowired
    private IExemplaireRepository exemplaireRepository;

    public Exemplaire getExemplaireByIsbn(Livre livre){
        return exemplaireRepository.getExemplaireByIsbn(livre);
    }
}
