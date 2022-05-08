package com.example.biblio.repository;

import com.example.biblio.entity.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPretRepository extends JpaRepository<Pret, Long> {
    @Query("select p from Pret p join p.user u join p.exemplaire ex where u.id = :x")
    public List<Pret> getPretByUserId(@Param("x")Long id);
}
