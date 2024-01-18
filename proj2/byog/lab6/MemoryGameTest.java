package byog.lab6;
import org.junit.Test;
import org.junit.Assert.*;

public class MemoryGameTest {
    @Test
    public void TestgenerateRandomString(){
        for (int i = 0; i < 10; i++) {
            MemoryGame mg = new MemoryGame(10, 10, i);
            System.out.println(mg.generateRandomString(5));
        }
    }
}
