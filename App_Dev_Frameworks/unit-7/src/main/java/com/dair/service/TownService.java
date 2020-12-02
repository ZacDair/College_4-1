package com.dair.service;

import com.dair.entities.County;
import com.dair.entities.Town;

import java.util.List;

public interface TownService {
    Town save(String townName, County townCounty);

    List<Town> getAllTowns();
}
