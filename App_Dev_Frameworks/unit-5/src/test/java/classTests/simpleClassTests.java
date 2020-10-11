package classTests;

import com.dair.classes.Franchise;
import com.dair.classes.Hero;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class simpleClassTests {

    @Test
    public void testSetHeroName(){
        Hero h = new Hero();
        h.setHeroName("Green Arrow");
        assertEquals("Green Arrow", h.getHeroName());
    }
    @Test
    public void testHeroSetters(){
        Hero h = new Hero();
        h.setHeroName("Green Arrow");
        h.setHeroID(12);
        h.setFranchiseID(2);
        assertTrue(h.toString().contains("Green Arrow") && h.toString().contains("2"));
    }
    @Test
    public void testSetFranchiseName(){
        Franchise f = new Franchise();
        f.setFranchiseName("Marvel");
        assertEquals("Marvel", f.getFranchiseName());
    }
    @Test
    public void testFranchiseSetters(){
        Franchise f = new Franchise();
        f.setFranchiseName("Marvel");
        f.setFranchiseID(1);
        assertTrue(f.toString().contains("Marvel") && f.toString().contains("1"));
    }


}
