package databaseTests;

import com.dair.classes.Franchise;
import com.dair.service.FranchiseService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class) //Add Spring to Junit
@ContextConfiguration("classpath:beans.xml") //Allows this class to find context (beans, etc...)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Allows ordering the tests in this class
public class TestFranchiseService {

    @Autowired
    FranchiseService franchiseService;

    @Test
    public void testFindHeroByID(){
        Franchise f = franchiseService.findFranchise(1);
        assertEquals("Marvel", f.getFranchiseName());
    }

    @Test
    public void testDontFindHeroByID() {
        Franchise f = franchiseService.findFranchise(10);
        assertNull(f);
    }
}
