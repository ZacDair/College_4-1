package com.dair;

import com.dair.classes.Hero;
import com.dair.service.HeroService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        HeroService heroService = (HeroService) context.getBean("heroServiceImplementation");

        //Get the count of heroes
        System.out.println("There are " + heroService.countTheHeroes() + " heroes in the database");

        //Get hero by id (slight issue)
        System.out.println("\nFind Hero with ID 1: " + heroService.findHero(1));

        //Get all heroes from the DB
        System.out.println("\nAll heroes in the database: ");
        List<Hero> heroes = heroService.findAllHeroes();
        for (Hero hero : heroes){
            System.out.println("\t" + hero);
        }

        //Delete or try to delete a hero from the database by ID
        System.out.println("\nDelete hero by heroID: 2\nNumber of rows deleted: " + heroService.deleteHeroByHeroID(3));
        System.out.println("Now trying to delete the same hero...");
        System.out.println("Delete hero by heroID: 2\nNumber of rows deleted: " + heroService.deleteHeroByHeroID(3));

        //Change or try to change a hero name from the database
        System.out.println("\nChange 'Green Arrow' to 'Oliver Queen' => " + heroService.changeHeroName("Green Arrow", "Oliver Queen"));
        System.out.println("Now trying to change 'Green Arrow' again ...");
        System.out.println("Change 'Green Arrow' to 'Oliver Queen' => " + heroService.changeHeroName("Green Arrow", "Oliver Queen"));
        System.out.println("Change 'Oliver Queen' to 'Green Arrow' => " + heroService.changeHeroName("Oliver Queen", "Green Arrow"));

        //Try to add a new hero to our database
        System.out.println("\nAdding 'The Punisher' => "+heroService.addHero("The Punisher"));
        System.out.println("Trying to add 'The Punisher' again...");
        System.out.println("Adding 'The Punisher' => "+heroService.addHero("The Punisher"));

    }
}
