package com.example.biblio.repository;

import com.example.biblio.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    @Query("select u from Utilisateur u where u.email = :email")
    public Utilisateur getUtilisateurByMail(@Param("email")String email);
    @Query("select u from Utilisateur u where u.hash = :hash")
    public Utilisateur getUtilisateurByToken(@Param("hash")String hash);

}
