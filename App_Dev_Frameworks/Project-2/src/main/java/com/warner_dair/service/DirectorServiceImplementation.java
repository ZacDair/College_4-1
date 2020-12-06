package com.warner_dair.service;

import com.warner_dair.dao.DirectorDao;
import com.warner_dair.entities.Director;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DirectorServiceImplementation implements DirectorService{

    @Autowired
    DirectorDao directorDao;

    @Override
    public Director save(String directorFirstName, String directorLastName){
        if(directorDao.existsByDirectorLastName(directorLastName)){
            log.error("ERROR: Duplicate Directory Addition Attempt");
            return null;
        }
        return directorDao.save(new Director(directorFirstName, directorLastName));
    }

    @Override
    public List<Director> getAllDirectorsAlphabetically() {
        return directorDao.findAllByOrderByDirectorLastNameAsc();
    }

    @Override
    public Director getDirectorById(int directorId) {
        return directorDao.findDirectorAndFilmsByDirectorId(directorId);
    }
}
