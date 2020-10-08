package com.dair.service;

import com.dair.classes.Hero;

import java.util.List;

public interface HeroService {
    int countTheHeroes();

    Hero findHero(int heroID);

    List<Hero> findAllHeroes();

    int deleteHeroByHeroID(int i);

    int changeHeroName(String green_arrow, String oliver_queen);

    Hero addHero(String the_punisher);
}
