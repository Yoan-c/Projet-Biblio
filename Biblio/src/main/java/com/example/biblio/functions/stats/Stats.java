package com.example.biblio.functions.stats;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class Stats {
    private static final DecimalFormat dfZero = new DecimalFormat("0.00");
    public Map<String, Object> calcStat(Map<String, Object> lstStat){
        int total = Integer.parseInt(String.valueOf(lstStat.get("total")));
        lstStat = calcStatBook(lstStat, total);
        lstStat = calcStatEditeur(lstStat, total);
        lstStat = calcStatByOption(lstStat, "genre");
        lstStat = calcStatByOption(lstStat, "auteur");
        return lstStat;
    }
    public Map<String, Object> calcStatBook(Map<String, Object> lstStat, int total){
        List<Map<String, String>> livre = (List<Map<String, String>>) lstStat.get("livre");
        for ( int i = 0 ; i < livre.size(); i++){
            float nb = Float.parseFloat(livre.get(i).get("nb"));
            float stat = (100 * nb / total);
            livre.get(i).put("stats", dfZero.format(stat));
        }
        return lstStat;
    }
    public Map<String, Object> calcStatEditeur(Map<String, Object> lstStat, int total){
        List<Map<String, String>> editeur = (List<Map<String, String>>) lstStat.get("editeur");
        for ( int i = 0 ; i < editeur.size(); i++){
            float nb = Float.parseFloat(editeur.get(i).get("nb"));
            float stat = (100 * nb / total);
            editeur.get(i).put("stats", dfZero.format(stat));
        }
        return lstStat;
    }
    public Map<String, Object> calcStatByOption(Map<String, Object> lstStat, String option){
        List<Map<String, String>> auteur = (List<Map<String, String>>) lstStat.get(option);
        float total = 0;
        for ( int i = 0 ; i < auteur.size(); i++){
            total += Float.parseFloat(auteur.get(i).get("nb"));
        }
        for ( int i = 0 ; i < auteur.size(); i++){
            float nb = Float.parseFloat(auteur.get(i).get("nb"));
            float stat = (100 * nb / total);
            auteur.get(i).put("stats", dfZero.format(stat));
        }
        return lstStat;
    }

}
