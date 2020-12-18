package com.warner_dair.dao;

import com.warner_dair.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface FilmDao extends JpaRepository<Film, Integer>{

    List<Film> findAllByOrderByFilmReleaseYearAsc();

    @Modifying
    @Transactional
    @Query("UPDATE Film film SET film.filmName = :newFilmName WHERE film.filmId = :filmId")
    void changeFilmName(@Param("newFilmName") String newFilmName, @Param("filmId") int filmId);

    List<Film> findAllByFilmReleaseYear(int filmReleaseYear);

}
