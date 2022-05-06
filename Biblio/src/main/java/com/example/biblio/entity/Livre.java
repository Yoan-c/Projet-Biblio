package com.example.biblio.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Livre implements Serializable {

    @Id
    private String isbn;
    private String titre;
    private Date datePublication;
    private String description;
    private String cover;
    @ManyToOne
    private Langue langue;
    @ManyToOne
    @JoinColumn(name = "id_editeur")
    private Editeur editeur;
    @ManyToMany
    private Collection<Genre> genres;
    @ManyToMany
    private Collection<Auteur> auteurs;

}
