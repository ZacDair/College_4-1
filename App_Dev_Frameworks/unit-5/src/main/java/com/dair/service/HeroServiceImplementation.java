package com.dair.service;

import com.dair.classes.Hero;
import com.dair.dao.HeroDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeroServiceImplementation implements HeroService {

    @Autowired
    HeroDao heroDao;

    public int countTheHeroes(){
        return heroDao.getHeroCount();
    }

    //public boolean heroExists(String heroName){return heroDao.heroExists(heroName);}

    //Validates the find hero by id
    public Hero findHero(int heroID){
        Hero returnedHero = heroDao.findHeroByHeroID(heroID);
        if (returnedHero == null){
            System.out.println("Error - No Hero found with ID: "+ heroID);
        }
        return returnedHero;
    }

    //Returns all the heroes
    public List<Hero> findAllHeroes(){
        return heroDao.findAllHeroes();
    }

    //Returns the number if any of the hero delete from the database
    public int deleteHeroByHeroID(int heroID){
        int numberDeleted = heroDao.deleteHeroByHeroID(heroID);
        if(numberDeleted == 0){
            System.out.println("ERROR: No hero found with heroID: "+ heroID);
        }
        return numberDeleted;
    }

    //Check the hero name to change is in the DB, and that the new name doesn't exist (possibly not needed unless name is PK)
    public int changeHeroName(String oldName, String newName){
        if (! heroDao.heroExists(oldName)){
            System.out.println("ERROR: "+oldName+" is not in the database");
        }
        if(heroDao.heroExists(newName)){
            System.out.println("ERROR: "+newName+" is already in the database");
        }
        int numberEdited = heroDao.changeHeroName(oldName, newName);
        if(numberEdited == 0){
            System.out.println("ERROR: Nothing was changed in the database");
        }
        return numberEdited;
    }

    //Try to add a hero to the database
    public Hero addHero(String heroName){
       if(!heroDao.heroExists(heroName)){
           return heroDao.findHeroByHeroID(heroDao.addHero(heroName));
       }
       System.out.println("ERROR: "+heroName+" already exists in the database");
       return null;
    }

    //Returns all the heroes that belong to a franchise by ID
    public List<Hero> findAllHeroesByFranchiseID(int franchiseID){
        List<Hero> returnedHeroes = heroDao.findHeroesByFranchiseID(franchiseID);
        if (returnedHeroes.isEmpty()){
            System.out.println("ERROR: No Heroes have a franchise ID of: " + franchiseID);
        }
        return returnedHeroes;
    }
}
