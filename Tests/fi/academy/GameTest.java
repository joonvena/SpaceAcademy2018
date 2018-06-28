package fi.academy;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


public class GameTest {

    @Test
    public void doestheGameReadData() {
        Game g = new Game();
        g.start();
        assertFalse(g.areaList.isEmpty());
        assertFalse(g.encounters.isEmpty());
    }

    @Test
    public void doestheTakeCommandWork() {
        Game g = new Game();
        g.start();
        g.commandParser("goto Cryogenics");
        g.commandParser("take manual");
        assertTrue(g.inventory.contains("manual"));
    }

    @Test
    public void doestheGotoCommandWork() {
        Game g = new Game();
        g.start();
        g.commandParser("goto Bio Lab");
        assertEquals(1, g.currentArea);
    }


    @Test
    public void doestheGetItemMethodAddAnItem() {
        Game g = new Game();
        g.start();
        g.getItem("chemicals");
        assertTrue(g.inventory.contains("chemicals"));
    }

    @Test
    public void doestheDropItemDropanItem() {
        Game g = new Game();
        g.start();
        assertTrue(g.inventory.contains("chemicals"));
        g.commandParser("drop chemicals");
        assertFalse(g.inventory.contains("chemicals"));
    }


    @Test
    public void doesFetchAreaIDReturntheCorrectRoomNumber() {
        Game g = new Game();
        g.start();
        String areaThatIsSought = "Bio lab";
        assertEquals(1, g.fetchAreaID(areaThatIsSought));
    }
    @Test
    public void doesFetchAreaNameReturntheCorrectRoomName() {
        Game g = new Game();
        g.start();
        int areaThatisSought = 1;
        assertEquals("Bio Lab", g.fetchAreaName(areaThatisSought));
    }

    @Test
    public void doesuseComputerwiththeCorrectAnswerTriggertheCorrectAnswerEvent() {
        Game g = new Game();
        g.start();
        g.commandParser("goto Bio lab");
        g.commandParser("use lab computer hello world");
        assertTrue(g.lastItemUsed.equals("lab computerOK"));
    }

    @Test
    public void doestheUseItemChangeLastItemUsed(){
        Game t = new Game();
        t.start();
        t.commandParser("goto Cryogenics");
        t.commandParser("take manual");
        t.commandParser("use manual");
        assertTrue(t.lastItemUsed.equals("manual"));

    }





}
