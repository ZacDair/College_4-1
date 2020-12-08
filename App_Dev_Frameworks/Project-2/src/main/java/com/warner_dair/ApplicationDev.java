package com.warner_dair;

import com.warner_dair.dao.DirectorDao;
import com.warner_dair.dao.FilmDao;
import com.warner_dair.entities.Director;
import com.warner_dair.entities.Film;
import com.warner_dair.entities.CustomUser;
import com.warner_dair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class ApplicationDev implements CommandLineRunner {

	@Autowired
	DirectorDao directorDao;

	@Autowired
	FilmDao filmDao;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;

	
	@Override
	public void run(String... args) throws Exception {

		// Populate DB with some Director entities
		Director jGunn = new Director("James", "Gunn");
		Director mScorsese = new Director("Martin", "Scorsese");
		Director sSpielberg = new Director("Steven", "Spielberg");
		Director qTarantino = new Director("Quentin", "Tarantino");
		Director cNolan = new Director("Christopher", "Nolan");
		Director gLucas = new Director("George", "Lucas");

		directorDao.save(jGunn);
		directorDao.save(mScorsese);
		directorDao.save(sSpielberg);
		directorDao.save(qTarantino);
		directorDao.save(cNolan);
		directorDao.save(gLucas);

		// Populate DB with some Film entities
		Film guardiansOfTheGalaxy = new Film("Guardians of the Galaxy", 2014, jGunn);
		Film taxiDriver = new Film("Taxi Driver", 1976, mScorsese);
		Film jaws = new Film("Jaws", 1975, sSpielberg);
		Film ET = new Film("ET", 1982, sSpielberg);
		Film pulpFiction = new Film("Pulp Fiction", 1994, qTarantino);
		Film inception = new Film("Inception", 2010, cNolan);
		Film starWarsI = new Film("Star Wars: A New Hope", 1977, gLucas);
		Film starWarsII = new Film("Star Wars: The Empire Strikes Back", 1980, gLucas);
		Film starWarsIII = new Film("Star Wars: Return of the Jedi", 1983, gLucas);

		filmDao.save(guardiansOfTheGalaxy);
		filmDao.save(taxiDriver);
		filmDao.save(jaws);
		filmDao.save(ET);
		filmDao.save(pulpFiction);
		filmDao.save(inception);
		filmDao.save(starWarsI);
		filmDao.save(starWarsII);
		filmDao.save(starWarsIII);

		// Populate our DB with our custom user entities
		CustomUser adminCustomUser = new CustomUser("admin@admin.ie", passwordEncoder.encode("password"), "ADMIN");
		CustomUser apiCustomUser = new CustomUser("api@api.ie", passwordEncoder.encode("password"), "API");
		CustomUser standardCustomUser = new CustomUser("user@user.ie", passwordEncoder.encode("password"), "USER");

		userService.save(adminCustomUser);
		userService.save(apiCustomUser);
		userService.save(standardCustomUser);
	}
}
