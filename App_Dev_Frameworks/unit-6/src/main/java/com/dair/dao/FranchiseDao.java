package com.dair.dao;

import com.dair.classes.Franchise;

public interface FranchiseDao {

    int getFranchiseCount();

    Franchise findFranchiseByID(int franchiseID);

    boolean franchiseExists(String franchiseName);

    int deleteFranchiseByFranchiseID(int franchiseID);

}
