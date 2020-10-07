package com.dair;

import com.dair.service.HeroService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        HeroService heroService = (HeroService) context.getBean("heroServiceImplementation");
        System.out.println("There are " + heroService.countTheHeroes() + " heroes in the database");
    }
}
