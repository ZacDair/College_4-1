package com.dair;

import com.dair.classes.Franchise;
import com.dair.classes.Hero;
import com.dair.classes.Publisher;
import com.dair.config.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class Main{

    public static void main(String [] args){
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        Publisher p = context.getBean("marvel", Publisher.class);
        System.out.println(p);

        //Print all Hero beans
        Map<String, Hero> heroBeans = context.getBeansOfType(Hero.class);
        for(Map.Entry<String, Hero> c: heroBeans.entrySet()){
            System.out.println(c.getValue());
        }
    }
}