package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {
    /* Replace with the magic word given in lab.
     * If you are submitting early, just put in "early" */
    public static final String MAGIC_WORD = "";

    @Test
    public void testBasics() {
        Clorus p = new Clorus(2);
        assertEquals("clorus", p.name());
        assertEquals(2, p.energy(), 0.001);
        assertEquals(new Color(34, 0, 231), p.color());
        p.move();
        assertEquals(1.97, p.energy(), 0.001);
        p.move();
        assertEquals(1.94, p.energy(), 0.001);
        p.stay();
        assertEquals(1.93, p.energy(), 0.001);
        p.stay();
        assertEquals(1.92, p.energy(), 0.001);
    }

    @Test
    public void testReplicate() {
        Clorus p = new Clorus(1.2);
        Clorus newp = p.replicate();
        assertNotSame(p, newp);
        assertEquals(0.6, p.energy(), 0.001);
        assertEquals(0.6, newp.energy(), 0.001);
        assertEquals("clorus", newp.name());
    }

    @Test
    public void testChoose() {
        Clorus p = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        //You can create new empties with new Empty();
        //Despite what the spec says, you cannot test for Cloruses nearby yet.
        //Sorry!

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        surrounded.put(Direction.RIGHT, new Empty());
        surrounded.put(Direction.LEFT, new Plip());
        actual = p.chooseAction(surrounded);
        expected = new Action(Action.ActionType.ATTACK, Direction.LEFT);

        assertEquals(expected, actual);

        surrounded.put(Direction.RIGHT, new Impassible());
        surrounded.put(Direction.LEFT, new Empty());
        actual = p.chooseAction(surrounded);
        expected = new Action(Action.ActionType.REPLICATE, Direction.LEFT);

        assertEquals(expected, actual);

        surrounded.put(Direction.LEFT, new Empty());
        p = new Clorus(0.8);
        actual = p.chooseAction(surrounded);
        expected = new Action(Action.ActionType.MOVE, Direction.LEFT);

        assertEquals(expected, actual);
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestPlip.class));
    }
}
