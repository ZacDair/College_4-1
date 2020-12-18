package com.warner_dair.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "Films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int filmId;

    @Column(nullable = false)
    private String filmName;

    @Column(nullable = false)
    private int filmReleaseYear;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Director filmDirector;

    // Custom constructor, as by default the film object will be missing it's ID
    public Film(String filmName, int filmReleaseYear, Director director){
        this.filmName = filmName;
        this.filmReleaseYear = filmReleaseYear;
        this.filmDirector = director;
    }

}
