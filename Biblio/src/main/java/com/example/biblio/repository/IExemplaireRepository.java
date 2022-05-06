package com.example.biblio.repository;

import com.example.biblio.entity.Exemplaire;
import com.example.biblio.entity.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IExemplaireRepository extends JpaRepository<Exemplaire, Long> {
    @Query("select e from Exemplaire e where e.isbn = :x")
    public Exemplaire getExemplaireByIsbn(@Param("x") Livre isbn);
}
