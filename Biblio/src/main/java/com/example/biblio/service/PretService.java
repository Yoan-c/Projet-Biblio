package com.example.biblio.service;

import com.example.biblio.entity.Pret;
import com.example.biblio.repository.IPretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PretService {

    @Autowired
    private IPretRepository pretRepository;
    Logger log = Logger.getLogger("");

    public void getPretByUser(String mail){
      List<Pret> pret =  pretRepository.getPretByUserMail(mail);
      log.info("pret "+ pret);
    }
}
