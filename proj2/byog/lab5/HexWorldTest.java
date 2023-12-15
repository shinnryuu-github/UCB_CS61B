package byog.lab5;

import static org.junit.Assert.*;
import org.junit.Test;

public class HexWorldTest {
    @Test
    public void Test_get_width(){
        int expected = 40;
        int actuall = HexWorld.get_Width(4);
        assertEquals(expected, actuall);
    }

}
