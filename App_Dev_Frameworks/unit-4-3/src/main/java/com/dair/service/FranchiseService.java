package com.dair.service;

import com.dair.classes.Franchise;
import com.dair.classes.Hero;

import java.util.List;

public interface FranchiseService {
    int countFranchises();

    Franchise findFranchise(int franchiseID);

    int deleteFranchiseByFranchiseID(HeroService heroService, int franchiseID);
}
