package com.example.biblio.service;

import com.example.biblio.entity.Livre;
import com.example.biblio.entity.Pret;
import com.example.biblio.functions.format.Format;
import com.example.biblio.functions.stats.Stats;
import com.example.biblio.repository.IPretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Service
public class PretService {

    @Autowired
    private IPretRepository pretRepository;

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
            if (pret.get(i).getExemplaire().getIsbn().getIsbn().equals(idPret)) {
                if (pret.get(i).getRenouvele() == false) {
                    pret.get(i).setDateFin(new Date(pret.get(i).getDateFin().getTime() + (7*(1000*60*60*24))));
                    pret.get(i).setRenouvele(true);
                    pretRepository.save(pret.get(i));
                    return true;
                }
            }
        }
        return false;
    }
    public List<HashMap<String, String>> relancePret(String username, String mdp){

        List<Pret> pret = pretRepository.getAllByDate(new Date());
        List<HashMap<String, String>> relanceP = new ArrayList<>();
        String mdp2 = "admin";
        if(!username.equals("admin") || !mdp2.equals(mdp)){
            relanceP.add(0, new HashMap<>(){});
            return relanceP;
        }
        if(pret == null){
            relanceP.add(0, new HashMap<>(){});
            return relanceP;
        }
        int size = pret.size();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0 ; i < size; i++){
            HashMap<String, String> infoP = new HashMap<String, String>();
            infoP.put("email", pret.get(i).getUser().getEmail());
            infoP.put("nom", pret.get(i).getUser().getNom());
            infoP.put("prenom", pret.get(i).getUser().getPrenom());
            infoP.put("dateDebut", simpleDateFormat.format(pret.get(i).getDateDebut()));
            infoP.put("dateFin", simpleDateFormat.format(pret.get(i).getDateFin()));
            infoP.put("titre", pret.get(i).getExemplaire().getIsbn().getTitre());
            relanceP.add(infoP);
        }
        return relanceP;
    }

    public Map<String, Object> getStats(){
        Format f = new Format();
        List<Pret> lstPret = pretRepository.getAllPret();
        Map<String, Object> lstStat = new HashMap<>();
        List<Map<String, String>> lstAuteur = new ArrayList<Map<String, String>>();
        List<Map<String, String>> lstGenre = new ArrayList<Map<String, String>>();
        List<Map<String, String>> lstEditeur = new ArrayList<Map<String, String>>();
        List<Map<String, String>> lstLivre = new ArrayList<Map<String, String>>();
        int total = lstPret.size();
        for (int i = 0 ; i < total; i++) {
            Livre book = lstPret.get(i).getExemplaire().getIsbn();
            lstAuteur = f.FormatAuteur(book, lstAuteur, total);
            lstGenre = f.FormatGenre(book, lstGenre, total);
            lstEditeur = f.FormatEditeur(book, lstEditeur, total);
            lstLivre = f.FormatTitle(book, lstLivre, total);
        }
        lstStat.put("total",total);
        lstStat.put("auteur",lstAuteur);
        lstStat.put("genre",lstGenre);
        lstStat.put("editeur",lstEditeur);
        lstStat.put("livre",lstLivre);
        Stats stats = new Stats();
        lstStat = stats.calcStat(lstStat);
        return lstStat;
    }
}
