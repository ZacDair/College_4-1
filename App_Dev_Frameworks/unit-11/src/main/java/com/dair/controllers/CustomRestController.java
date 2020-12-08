package com.dair.controllers;

import com.dair.entities.County;
import com.dair.entities.Town;
import com.dair.service.CountyService;
import com.dair.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("myapi")
public class CustomRestController {
    @Autowired
    CountyService countyService;

    @Autowired
    TownService townService;

    @GetMapping("/counties")
    List<County> getCounties(){
        return countyService.getAllCounties();
    }

    @GetMapping("/towns")
    List<Town> getTowns(){
        return townService.getAllTowns();
    }

    // return a county or 404
    @GetMapping("/county/{countyId}")
    ResponseEntity<County> getCounty(@PathVariable(name="countyId") int countyId){
        County county = countyService.findCounty(countyId);
        if (county == null){
            //return new ResponseEntity<County>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //return new ResponseEntity<County>(county, HttpStatus.FOUND);
        return new ResponseEntity<>(county, HttpStatus.FOUND);
    }

    // delete a county
    @DeleteMapping("/county/{countyId}")
    ResponseEntity<County> deleteCounty(@PathVariable(name="countyId") int countyId){
        boolean deleted = countyService.deleteCounty(countyId);
        if (deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //return new ResponseEntity<County>(county, HttpStatus.FOUND);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // returns a list of towns in a county (as json)
    @GetMapping("/townsincounty/{countyId}")
    ResponseEntity<List<Town>> getTownsInACounty(@PathVariable(name="countyId") int countyId){
        County county = countyService.findCounty(countyId);
        if (county == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Town>>(townService.getAllTownsInACounty(countyId), HttpStatus.FOUND);
    }

    // create a new county given its name (POST or PUT can be used here)
    @PostMapping("/county")
    public ResponseEntity<County> addNewCounty(@RequestParam String countyName){
        County newCounty = countyService.save(countyName);
        if (newCounty == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newCounty, HttpStatus.CREATED);
    }
}
