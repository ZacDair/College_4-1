package com.dair.service;

import com.dair.classes.Hero;
import com.dair.dao.HeroDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeroServiceImplementation implements HeroService {

    @Autowired
    HeroDao heroDao;

    public int countTheHeroes(){
        return heroDao.getHeroCount();
    }

    //Validates the find hero by id
    public Hero findHero(int heroID){
        Hero returnedHero = HeroDao.findHeroByHeroID(heroID);
        if (returnedHero == null){
            System.out.println("Error - No Hero found with ID: "+ heroID);
        }
        return returnedHero;
    }
}
