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


    @Query("SELECT l from Livre l join l.genres g join l.auteurs a join l.langue la where g.nom like :genre and a.nom like :name and " +
            "a.prenom like :prenom and l.titre like :titre and la.nom like :nomLangue")
    public List<Livre> search(@Param("genre")String genre, @Param("name")String name, @Param("prenom")String prenom,
                              @Param("titre")String titre, @Param("nomLangue")String langue);
}
