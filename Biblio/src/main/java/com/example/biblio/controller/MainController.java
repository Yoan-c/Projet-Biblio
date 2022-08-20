package com.example.biblio.controller;

import com.example.biblio.entity.*;
import com.example.biblio.service.LivreService;
import com.example.biblio.service.PretService;
import com.example.biblio.service.UtilisateurService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Controller qui oriente les requêtes Http
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MainController {
    @Autowired
    private LivreService livreService;

    @Autowired
    private PretService pretService;

    @Autowired
    UtilisateurService userService;


    private ObjectMapper mapper = new ObjectMapper();

    Logger log = Logger.getLogger("");


    /**
     * Fonction qui permet de se connecter a l'api
     *
     * @param info doit contenir un <code>username</code> (qui est l'identifiant) et un <code>password</code> pour
     *             se connecter
     * @param resp est remplit automatiquement lors de la requête Http
     * @return objet contenant le <code>token</code> de connexion et une <code>response</code>
     * @throws JsonProcessingException est levé lors de la traduction de la data en JSON
     */
    @PostMapping("/connexion")
    public String v2Login(@RequestParam Map<String, String> info, HttpServletResponse resp)
            throws JsonProcessingException {

        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        String mail = info.get("username");
        String mdp = info.get("password");
        boolean isConnect = userService.connexionUser(mail, mdp);
        if(isConnect){
            resultMap.put("response", "success");
            resultMap.put("token", userService.getHashUser(mail));
            result.add(resultMap);
        }
        else {
            resultMap.put("response", "failed");
            result.add(resultMap);
        }
        resp.setStatus(HttpStatus.OK.value());

        return mapper.writeValueAsString(result);
    }

    /**
     * Fonction qui permet de se déconnecter à l'api
     *
     * @param token correspond au token de connexion
     * @param resp est rempli automatiquement lors de la requête Http
     * @return object contenant une <code>response</code>
     * @throws JsonProcessingException est levé lors de la traduction de la data en JSON
     */
    @GetMapping("/deconnexion")
    public String deconnexion(@RequestParam String token, HttpServletResponse resp) throws JsonProcessingException {
        boolean isDeconnect = userService.deconnexion(token);
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        String response = (isDeconnect)? "success" : "failed";
        resultMap.put("response", response);
        result.add(resultMap);
        resp.setStatus(HttpStatus.OK.value());
        return mapper.writeValueAsString(result);
    }
    /**
     * Affiche la liste des livres
     *
     * @param token correspond au token de connexion
     * @param resp est rempli automatiquement lors de la requête Http
     * @return Objet JSON contenant une <code>response</code> et <code>data</code>. Data correspond aux informations
     * sur les livres
     * @throws JsonProcessingException est levé lors de la traduction de la data en JSON
     */
    @GetMapping(value = "/", produces={"application/json; charset=UTF-8"})
    public String  getBook(@RequestParam String token, HttpServletResponse resp) throws JsonProcessingException {
        token = (token != null) ? token : "";
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        boolean isConnect = userService.isValidToken(token);
        String jsonInString = "";
        if(isConnect) {
            jsonInString = mapper.writeValueAsString(livreService.getBook());
            resultMap.put("data", jsonInString);
            resultMap.put("response", "success");
            resp.setStatus(HttpStatus.OK.value());
        }
        else {
            jsonInString = mapper.writeValueAsString("");
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resultMap.put("response", "failed");
        }
        resultMap.put("data", jsonInString);
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }

    /**
     * Recherche un ou plusieurs livres correspondant aux critères de recherche
     *
     * @param info doit contenir le token de connexion et un ou plusieurs champs suivant <code>titre, auteur,
     *             genre, langue</code>
     * @param resp est rempli automatiquement lors de la requête Http
     * @return Object JSON contenant une <code>response</code> et <code>data</code>. Data correspond aux informations
     * sur les livres
     * @throws JsonProcessingException est levé lors de la traduction de la data en JSON
     */
    @GetMapping(value = "/search", produces={"application/json; charset=UTF-8"})
    public String searchBook(@RequestParam Map<String, String> info, HttpServletResponse resp) throws JsonProcessingException {
        String token = info.get("token");
        token = (token != null) ? token : "";
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        boolean isConnect = userService.isValidToken(token);
        String jsonInString = "";
        if(isConnect){
            resp.setStatus(HttpStatus.OK.value());
            resultMap.put("response", "success");
            jsonInString = mapper.writeValueAsString(livreService.search(info));
        }
        else{
            resultMap.put("response", "failed");
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            jsonInString = mapper.writeValueAsString("");
        }
        resultMap.put("data", jsonInString);
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }
    /**
     * Recherche les livres empruntés par l'utilisateur connecté
     *
     * @param token est le token de connexion
     * @param resp est rempli automatiquement lors de la requête Http
     * @return Object JSON contenant une <code>response</code> et <code>data</code>. Data correspond aux informations
     * sur les livres que l'utilisateur a emprunté
     * @throws JsonProcessingException est levé lors de la traduction de la data en JSON
     */
    @GetMapping(value = "/pret", produces={"application/json; charset=UTF-8"})
    public String pret(@RequestParam String token, HttpServletResponse resp) throws JsonProcessingException {
        token = (token != null) ? token : "";
        boolean isConnect = userService.isValidToken(token);
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        String jsonInString = "";
        if(isConnect) {
            mapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            String mail = userService.getUserByToken(token).getEmail();
            jsonInString = mapper.writeValueAsString(pretService.getPretByUser(mail));
            resp.setStatus(HttpStatus.OK.value());
            resultMap.put("response", "success");
        }else {
            resultMap.put("response", "failed");
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            jsonInString = mapper.writeValueAsString("");
        }
        resultMap.put("data", jsonInString);
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }

    /**
     * Envoi une demande de modification dans la durée de prêt concernant un livre
     *
     * @param info doit contenir le <code>token</code> de connexion et <code>idPret</code> qui correspond a l'ISBN du livre emprunté
     * @param resp est rempli automatiquement lors de la requête Http
     * @return Object JSON contenant une <code>response</code> et <code>data</code>. Data correspond aux informations
     * sur le renouvellement du livre
     * @throws JsonProcessingException est levé lors de la traduction de la data en JSON
     */
    @PatchMapping(value = "/pret", produces={"application/json; charset=UTF-8"})
   public String continuePret(@RequestParam Map<String, String> info, HttpServletResponse resp) throws JsonProcessingException {
        String token = info.get("token");
        token = (token != null) ? token : "";
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        HashMap<String, String> ret = new HashMap<>();
        boolean isConnect = userService.isValidToken(token);
        if (isConnect){
            String mail = userService.getUserByToken(token).getEmail();
            boolean renouvellement = pretService.updatePret(mail, info.get("idPret"));
            if (renouvellement) {
                resp.setStatus(HttpStatus.OK.value());
                resultMap.put("response", "success");
                resultMap.put("data", mapper.writeValueAsString(renouvellement));
                result.add(resultMap);
                return mapper.writeValueAsString(result);
            }
        }
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resultMap.put("response", "failure");
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }
    /**
     * Crée un nouvel utilisateur
     *
     * @param info doit contenir les informations de l'utilisateur <code>nom, prenom, mdp, email, confirmMdp</code>
     * @param resp est rempli automatiquement lors de la requête Http
     * @return object JSON contenant une <code>response</code> et <code>data</code>. Data correspondant au resultat
     * de la création de compte
     * @throws JsonProcessingException est levé lors de la traduction de la data en JSON
     */
    @PostMapping(value = "/create", produces={"application/json; charset=UTF-8"})
    public String createUser(@RequestBody Map<String, String> info, HttpServletResponse resp) throws JsonProcessingException {
        boolean create = userService.createUser(info);
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        if(!create){
            resultMap.put("response", "success");
            resultMap.put("data", mapper.writeValueAsString(create));
            result.add(resultMap);
            resp.setStatus(HttpStatus.CREATED.value());
            return mapper.writeValueAsString(result);
        }
        resp.setStatus(HttpStatus.BAD_REQUEST.value());
        resultMap.put("response", "failed");
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }
    /**
     * Affiche les informations concernant un utilisateur
     *
     * @param token est le token de connexion
     * @param resp est rempli automatiquement lors de la requête Http
     * @return Object JSON contenant une <code>response</code> et <code>data</code>. Data correspondant aux informations
     * de compte de l'utilisateur
     * @throws JsonProcessingException est levé lors de la traduction de la data en JSON
     */
    @GetMapping("/info")
    public String infoUser(@RequestParam String token, HttpServletResponse resp) throws JsonProcessingException {
        // recuperation de l'id
        token = (token != null) ? token : "";
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        boolean isConnect = userService.isValidToken(token);
        if (isConnect){
            Utilisateur user = userService.getUserByToken(token);
            user.setValidity(null);
            resultMap.put("response", "success");
            resultMap.put("data", mapper.writeValueAsString(user));
            result.add(resultMap);
            resp.setStatus(HttpStatus.OK.value());
            return mapper.writeValueAsString(result);
        }
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resultMap.put("response", "failed");
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }

    /**
     * Met à jour les informations concernant un utilisateur
     *
     * @param info doit contenir un <code>token</code> de connexion et les informations de l'utilisateur <code>nom,
     *             prenom, mdp, confirmMdp, mail</code>
     * @param resp est rempli automatiquement lors de la requête Http
     * @return object JSON contenant une <code>response</code> et <code>data</code>. Data correspondant aux informations
     * de compte de l'utilisateur modifié
     * @throws JsonProcessingException est levé lors de la traduction de la data en JSON
     */
    @PutMapping(value ="/update",  produces={"application/json; charset=UTF-8"})
    public String updateUser(@RequestBody Map<String, String> info, HttpServletResponse resp) throws JsonProcessingException {
        // recuperation de l'id
        String token = info.get("token");
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        token = (token != null) ? token : "";
        boolean isConnect = userService.isValidToken(token);
        if (isConnect){
            Utilisateur infoUser = userService.getUserByToken(token);
            String resultats = userService.updateUser(info, infoUser.getEmail(), infoUser);
            int code = Integer.parseInt(resultats.split(";")[0]);
            String resultat = resultats.split(";")[1];
            if (code == 2)
                infoUser.setEmail(info.get("mail"));
            if (code > 0)
                token = resultats.split(";")[2];
            resultMap.put("response", "success");
            resultMap.put("token", token);
            resultMap.put("data", resultat);
            result.add(resultMap);
            resp.setStatus(HttpStatus.OK.value());
            return mapper.writeValueAsString(result);
        }
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resultMap.put("response", "failed");
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }

    /**
     * Récupération des mails de tous les utilisateurs n'ayant pas rendu un ou plusieurs livres
     * empruntés
     *
     * @param username est l'identifiant de l'utilisateur qui peut utiliser le batch
     * @param password est le mot de passe de l'utilisateur pouvant utiliser le batch
     * @return object JSON contenant les adresses mail des utilisateurs n'ayant pas rendu leur(s) livre(s)
     */
    @GetMapping("/batch")
    public List<HashMap<String, String>> batch(@RequestParam String username, @RequestParam String password){
        return pretService.relancePret(username, password);
    }
    /**
     * Affiche les statistiques concernant les livres, les auteurs etc... les plus emprunté , lus etc..
     *
     * @param token est le token identifiant l'utilisateur
     * @param resp est rempli automatiquement lors de la requête Http
     * @return object JSON contenant une <code>response</code> et <code>data</code>. Data correspondant aux statistiques
     * d'emprunt
     * @throws JsonProcessingException est levé lors de la traduction de la data en JSON
     */
    @GetMapping(value ="/stats",  produces={"application/json; charset=UTF-8"})
    public String statsLend(@RequestParam String token, HttpServletResponse resp) throws JsonProcessingException {
        boolean isConnect = userService.isValidToken(token);
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> resultMap= new HashMap<>();
        if (isConnect){
            resultMap.put("response", "success");
            resp.setStatus(HttpStatus.OK.value());
            resultMap.put("data", mapper.writeValueAsString(pretService.getStats()));
        }
        else{
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resultMap.put("response", "failed");
        }
        result.add(resultMap);
        return mapper.writeValueAsString(result);
    }
    /**
     * Affiche l'image demandée en requête
     *
     * @param name est le nom de l'image demandé par la requête Http
     * @param response est remplit automatiquement lors de la requête Http
     * @throws IOException est levé lorsque la ressource n'est pas trouvée
     */
    @RequestMapping(value = "/images/{name}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)

    public void getImage(@PathVariable("name") final String name, HttpServletResponse response) throws IOException {
        var imgFile = new ClassPathResource("images/"+name);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setStatus(HttpStatus.OK.value());
        try{
            StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
        }
        catch(Exception e){
           log.info("Erreur "+ e);
           imgFile = new ClassPathResource("images/aucun.png");
           StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
        }
    }
}
