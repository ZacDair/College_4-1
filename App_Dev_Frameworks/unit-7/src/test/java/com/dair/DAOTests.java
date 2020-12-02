package com.dair;

import com.dair.entities.County;
import com.dair.dao.CountyDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Unit7Application.class)
@ActiveProfiles("test")
class DAOTests {

    @Autowired
    CountyDao countyDao;

    @Test
    public void testSave(){
        County c = new County("Cork");
        countyDao.save(c);
        assertEquals(1, countyDao.count());
    }
}
