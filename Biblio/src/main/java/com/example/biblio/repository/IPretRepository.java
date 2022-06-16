package com.example.biblio.repository;

import com.example.biblio.entity.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IPretRepository extends JpaRepository<Pret, Long> {
    @Query("select p from Pret p join p.user u join p.exemplaire ex where u.email = :x")
    public List<Pret> getPretByUserMail(@Param("x")String email);

    @Query("select p from Pret p join p.user u join p.exemplaire ex where p.dateFin < :x")
    public List<Pret> getAllByDate(@Param("x") Date d);

    @Query("select p from Pret p join p.user u join p.exemplaire ex")
    public List<Pret> getAllPret();
}
