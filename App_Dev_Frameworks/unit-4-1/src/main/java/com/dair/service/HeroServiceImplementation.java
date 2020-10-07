package com.dair.service;

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
}
