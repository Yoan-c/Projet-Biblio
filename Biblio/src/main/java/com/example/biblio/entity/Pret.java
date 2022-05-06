package com.example.biblio.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Pret {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Utilisateur user;
    private Date dateDebut;
    private Date dateFin;
    private Boolean renouvele;
    @ManyToOne
    private Exemplaire exemplaire;
}
