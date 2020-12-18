package com.warner_dair;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("mysql")
@Component
public class ApplicationMySQL implements CommandLineRunner {
	
	@Override
	public void run(String... args) throws Exception {
		// You can only test this code if you create a MySQL database with the appropriate tables and fields.
	}
}
