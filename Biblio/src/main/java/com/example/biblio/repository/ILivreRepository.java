package com.example.biblio.repository;

import com.example.biblio.entity.Auteur;
import com.example.biblio.entity.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface ILivreRepository extends JpaRepository<Livre, String> {
    @Query("SELECT l from Livre l join l.auteurs a where a.nom like :x or a.prenom like :x")
    public List<Livre> searchByAuteurs(@Param("x")String auteur);
    @Query("SELECT l from Livre l join l.auteurs a where a.nom = :n and a.prenom = :p")
    public List<Livre> searchByAuteurs(@Param("n")String nom, @Param("p") String prenom);

    @Query("SELECT l from Livre l join l.genres g where g.nom like :x")
    public List<Livre> searchByGenre(@Param("x")String genre);

}
