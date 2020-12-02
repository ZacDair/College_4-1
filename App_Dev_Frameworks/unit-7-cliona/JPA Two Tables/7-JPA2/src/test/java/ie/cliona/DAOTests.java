package ie.cliona;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ie.cliona.dao.CountyDao;
import ie.cliona.entities.County;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)	// Execute the tests in the order specified through annotations
class DAOTests {
	@Autowired
	CountyDao countyDao;
		
	@Test
	@Order(1)
	public void testSave()
	{
		County c = new County("Cork");
		countyDao.save(c);
		assertEquals(1, countyDao.count());
	}
	
	@Test
	@Order(2)
	public void testRename()
	{
		countyDao.changeCountyName("Dublin", 1);
		assertEquals("Dublin", countyDao.findById(1).get().getCountyName());
	}
	
	@Test
	@Order(3)
	public void testRemove()
	{
		countyDao.deleteById(1);
		assertEquals(0, countyDao.count());
	}
}
