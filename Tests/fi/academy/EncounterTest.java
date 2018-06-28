package fi.academy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EncounterTest {

    @Test
    public void testDoestheEncounterDamageaddtotheHurtScore() {
        Game t = new Game();
        t.start();
        t.commandParser("goto Cryogenics");
        assertEquals(0, t.hurt);
        t.encounterHappens("huntBegins");
        assertEquals(10, t.hurt);
        t.commandParser("goto bio lab");
        t.commandParser("goto bio lab");
        assertEquals(10, t.hurt);
    }

    @Test
    public void doesEncounterHashMapreturntheCorrectEncounter() {
        Game g = new Game();
        g.start();
        String encountertobeTested ="startGame";
        Encounter encounter = g.encounters.get(encountertobeTested);
        assertEquals(encountertobeTested, encounter.getEventname());
    }




    @Test public void testDoestheEncounterCheckTriggerAnEncounter() {
        Game g = new Game();
        g.start();
        assertTrue(g.encounterCheck("startGame"));

    }

    @Test
    public void doesEncounterHappenChangetheHasHappenedtoFalse() {
        Game g = new Game();
        g.start();
        g.encounterHappens("startGame");
        assertTrue(g.encounters.get("startGame").getHasHappened());
    }

    @Test
    public void testDoestheEncounterDisableRoomItems() {
        Game t = new Game();
        t.start();
        t.currentArea = 1;
        assertTrue(t.areaList.get(t.currentArea).getItemList().contains("lab computer"));
        t.encounterHappens("lab computerOK");
        t.commandParser("Go to Bio lab");
        assertFalse(t.areaList.get(t.currentArea).getItemList().contains("lab computer"));
    }

    @Test public void testDoestheEncounterCheckWhetherAnEncounterhasAlreadyHappenedandThenNotTriggerIt() {
        Game g = new Game();
        g.start();
        assertTrue(g.encounterCheck("startGame"));
        g.encounterHappens("startGame");
        assertFalse(g.encounterCheck("startGame"));

    }


    @Test
    public void testDoestheEncounterMovethePlayer() {
        Game t = new Game();
        t.start();
        t.encounterHappens("steamDeath");
        assertEquals(21, t.currentArea);

    }

    @Test
    public void testDoestheEncounteraddtotheItems() {
        Game t = new Game();
        t.start();
        assertFalse(t.inventory.contains("whiskey"));
        t.encounterHappens("notWorking");
        assertTrue(t.inventory.contains("whiskey"));

    }
    @Test
    public void testDoestheEncounterRemoveItems() {
        Game t = new Game();
        t.start();
        assertFalse(t.inventory.contains("whiskey"));
        t.encounterHappens("notWorking");
        assertTrue(t.inventory.contains("whiskey"));
        t.encounterHappens("whiskey");
        assertFalse(t.inventory.contains("whiskey"));
    }



    @Test
    public void testDoestheEncounterAddPoints() {
        Game t = new Game();
        t.start();
        assertEquals(0, t.points);
        t.encounterHappens("bench press");
        assertEquals(2, t.points);

    }


    @Test
    public void testDoestheEncounterAddConditiontotheFlagList() {
        Game t = new Game();
        t.start();
        assertFalse(t.flaglist.contains("shitHitTheFan"));
        t.encounterHappens("huntBegins");
        assertTrue(t.flaglist.contains("shitHitTheFan"));

    }
    @Test
    public void testDoestheEncounterClearConditionsfromtheFlagList() {
        Game t = new Game();
        t.start();
        assertFalse(t.flaglist.contains("shitHitTheFan"));
        t.encounterHappens("huntBegins");
        assertTrue(t.flaglist.contains("shitHitTheFan"));
        t.encounterHappens("steamGone");
        assertFalse(t.flaglist.contains("shitHitTheFan"));

    }





}
