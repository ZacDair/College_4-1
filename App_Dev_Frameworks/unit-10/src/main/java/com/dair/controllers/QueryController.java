package com.dair.controllers;

import com.dair.entities.County;
import com.dair.entities.Town;
import com.dair.service.CountyService;
import com.dair.service.TownService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QueryController {
    @Autowired
    CountyService countyService;

    @Autowired
    TownService townService;

    @GetMapping("county/{countyId}")
    public String getCountyByCountyId(@PathVariable("countyId") int countyId, Model model){
        County county = countyService.findCounty(countyId);
        if (county == null){
            model.addAttribute("countyId", countyId);
            return "notfounderror";
        }
        model.addAttribute("county", county);
        return "county";
    }

    @GetMapping("/counties")
    public String showCounties(Model model){
        List<County> counties = countyService.getAllCounties();
        model.addAttribute("counties", counties);
        return "counties";
    }

    @GetMapping("townsincounty/{countyId}")
    public String showTownsInCounty(@PathVariable(name="countyId") int countyId, Model model){
        County county = countyService.getCountyAndTownsByCountyId(countyId);
        if (county == null){
            model.addAttribute("countyId", countyId);
            return "notfounderror";
        }
        model.addAttribute("county", county);
        return "townsincounty";
    }

    /* Delete Methods need overwriting before these work
    @GetMapping("/county/delete/{countyId}")
    public String deleteCounty(@PathVariable(name="countyId") int countyId, Model model){
        if (countyService.deleteCounty(countyId)){
            return "redirect:/counties";
        }
        model.addAttribute("countyId", countyId);
        return "notfounderror";
    }

    @DeleteMapping("/county/delete/{countyId}")
    public String deleteMappingCounty(@PathVariable(name="countyId") int countyId, Model model){
        if (countyService.deleteCounty(countyId)){
            return "redirect:/counties";
        }
        model.addAttribute("countyId", countyId);
        return "notfounderror";
    }
     */

    @GetMapping("town/{townId}")
    public String getTownByTownId(@PathVariable("townId") int townId, Model model){
        Town town = townService.findTown(townId);
        if (town == null){
            model.addAttribute("townId", townId);
            return "notfounderror";
        }
        model.addAttribute("town", town);
        return "town";
    }


}
