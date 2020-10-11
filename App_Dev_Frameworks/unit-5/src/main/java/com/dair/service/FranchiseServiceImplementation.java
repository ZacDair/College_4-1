package com.dair.service;


import com.dair.classes.Franchise;
import com.dair.classes.Hero;
import com.dair.dao.FranchiseDao;
import com.dair.dao.HeroDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FranchiseServiceImplementation implements FranchiseService {

    @Autowired
    FranchiseDao franchiseDao;

    public int countFranchises() {
        return franchiseDao.getFranchiseCount();
    }

    //Validates the find franchise by id
    public Franchise findFranchise(int franchiseID){
        Franchise returnedFranchise = franchiseDao.findFranchiseByID(franchiseID);
        if (returnedFranchise == null){
            System.out.println("Error - No Fanchise found with ID: "+ franchiseID);
        }
        return returnedFranchise;
    }

    //Returns the number if any of the franchise deleted from the database
    public int deleteFranchiseByFranchiseID(HeroService heroService, int franchiseID){
        List<Hero> franchiseHeroes = heroService.findAllHeroesByFranchiseID(franchiseID);
        if (!franchiseHeroes.isEmpty()) {
            int rowCount = franchiseHeroes.size();
            for (Hero hero : franchiseHeroes){
                heroService.deleteHeroByHeroID(hero.getHeroID());
            }
            System.out.println("\n"+rowCount+" Rows were deleted from the Hero table");

        }
        int numberDeleted = franchiseDao.deleteFranchiseByFranchiseID(franchiseID);
        if (numberDeleted == 0) {
            System.out.println("ERROR: No Franchise found with franchiseID: " + franchiseID);
        }
        return numberDeleted;
    }
}
