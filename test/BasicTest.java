import models.IceCream;

import org.junit.Test;

import play.test.UnitTest;

public class BasicTest extends UnitTest {

    @Test
    public void aVeryImportantThingToTest() {
        String flavour = "chocolate";
        IceCream ice = new IceCream(flavour);
        ice.save();
        IceCream iceCream = IceCream.find("byFlavour", flavour).first();
        assertNotNull(iceCream);
        assertEquals(ice, iceCream);
    }

}
