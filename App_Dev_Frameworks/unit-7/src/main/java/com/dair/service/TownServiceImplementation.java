package com.dair.service;

import com.dair.dao.TownDao;
import com.dair.entities.County;
import java.util.List;
import com.dair.entities.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TownServiceImplementation implements TownService{

    @Autowired
    TownDao townDao;

    @Override
    public Town save(String townName, County townCounty){
        if (townDao.existsByTownNameAndTownCounty(townName, townCounty)){
            return null;
        }
        else{
            Town newTown = new Town(townName, townCounty);
            return townDao.save(newTown);
        }
    }

    @Override
    public List<Town> getAllTowns(){
        return townDao.findAll();
    }
}
