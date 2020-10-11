package databaseTests;
import com.dair.classes.Hero;
import com.dair.dao.HeroDao;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class) //Add Spring to Junit
@ContextConfiguration("classpath:beans.xml") //Allows this class to find context (beans, etc...)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Allows ordering the tests in this class
public class TestHeroDao {

    @Autowired
    HeroDao heroDao;

    @Test
    @Order(1)
    public void testHeroCount(){
        int count = heroDao.getHeroCount();
        assertEquals(9, count);
    }

    @Test
    @Order(2)
    public void testFindHeroByID(){
        Hero h = heroDao.findHeroByHeroID(1);
        assertEquals("Iron Man", h.getHeroName());
    }

    @Test
    @Order(3)
    public void testAddingHero(){
        String newHeroName = "Vision";
        int newID = heroDao.addHero(newHeroName);
        Hero h = heroDao.findHeroByHeroID(newID);
        assertEquals(newHeroName, h.getHeroName());
    }
}
