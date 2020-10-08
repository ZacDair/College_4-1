package com.dair.dao;

import com.dair.classes.Hero;

public interface HeroDao {

    static Hero findHeroByHeroID(int heroID) {
        return null;
    }


    int getHeroCount();
}
