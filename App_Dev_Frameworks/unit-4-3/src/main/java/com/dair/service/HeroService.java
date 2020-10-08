package com.dair.service;

import com.dair.classes.Hero;

public interface HeroService {
    int countTheHeroes();
    Hero findHero(int heroID);
}
