package com.example.biblio.repository;

import com.example.biblio.entity.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAuteurRepository extends JpaRepository<Auteur, Long> {
    @Query("select a from Auteur a where a.nom like :x")
    public List<Auteur> findByName(@Param("x")String name);
}
