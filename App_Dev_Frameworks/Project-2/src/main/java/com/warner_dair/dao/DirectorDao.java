package com.warner_dair.dao;

import com.warner_dair.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DirectorDao extends JpaRepository<Director, Integer>{

    boolean existsByDirectorLastName(String directorLastName);

    List<Director> findAllByOrderByDirectorLastNameAsc();

    // Find the director with ID: directorId and the corresponding films
    @Query("SELECT director FROM Director director LEFT JOIN FETCH director.directorFilms films WHERE director.directorId= :directorId")
    Director findDirectorAndFilmsByDirectorId(int directorId);

    boolean existsByDirectorFirstNameAndDirectorLastName(String fname, String lname);
}
