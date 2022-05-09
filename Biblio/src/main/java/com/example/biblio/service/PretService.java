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

    public List<Pret> getPretByUser(String mail){
      List<Pret> pret =  pretRepository.getPretByUserMail(mail);
      return pret;
    }

    public boolean updatePret(String mail, String idPret){
        List<Pret> pret =  pretRepository.getPretByUserMail(mail);
        if (pret == null || pret.isEmpty()){
            return false;
        }
        int size = pret.size();
        for (int i = 0; i < size ; i++) {
            if (pret.get(i).getId().toString().equals(idPret)) {
                if (pret.get(i).getRenouvele() == false) {
                    pret.get(i).setRenouvele(true);
                    pretRepository.save(pret.get(i));
                    return true;
                }
            }
        }
        return false;
    }
}
