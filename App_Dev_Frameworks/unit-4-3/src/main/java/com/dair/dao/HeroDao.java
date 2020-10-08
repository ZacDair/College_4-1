package com.dair.dao;

import com.dair.classes.Hero;

import java.util.List;

public interface HeroDao {

    Hero findHeroByHeroID(int heroID);

    int getHeroCount();

    boolean heroExists(String heroName);

    List<Hero> findAllHeroes();

    int deleteHeroByHeroID(int heroID);


    int changeHeroName(String oldName, String newName);

    int addHero(String heroName);
}
