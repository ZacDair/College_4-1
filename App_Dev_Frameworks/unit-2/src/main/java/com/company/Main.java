package com.company;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main{

    public static void main(String [] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        Publisher p = ((Publisher) context.getBean("dc"));
        System.out.println(p);

        Franchise av = ((Franchise) context.getBean("arrowverse"));
        System.out.println(av);

        Franchise jl = ((Franchise) context.getBean("justiceLeague"));
        System.out.println(jl);

        Hero bc = ((Hero) context.getBean("blackCanary"));
        System.out.println(bc);

        Hero ga = ((Hero) context.getBean("greenArrow"));
        System.out.println(ga);

        Hero kf = ((Hero) context.getBean("killerFrost"));
        System.out.println(kf);

        Hero ww = ((Hero) context.getBean("wonderWoman"));
        System.out.println(ww);

        Hero aqua = ((Hero) context.getBean("aquaman"));
        System.out.println(aqua);

        Hero bats = ((Hero) context.getBean("batman"));
        System.out.println(bats);

        Franchise sg = ((Franchise) context.getBean("dcSuperGirls"));
        System.out.println(sg);

    }
}