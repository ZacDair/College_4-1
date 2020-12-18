package com.dair;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dair.service.CountyService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)	// Execute the tests in the order specified through annotations
class ServiceTests {

	@Autowired
    CountyService countyService;
	
	@Test
	@Order(1)
	public void testSave()
	{
		countyService.save("Cork");
		assertEquals(1, countyService.numberOfCounties());
	}
	
	@Test
	@Order(2)
	public void testSaveDuplicate()
	{
		countyService.save("Cork");
		assertEquals(1, countyService.numberOfCounties());
	}
	
	
}
