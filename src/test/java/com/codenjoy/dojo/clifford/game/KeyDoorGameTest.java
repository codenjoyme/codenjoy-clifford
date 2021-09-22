package com.codenjoy.dojo.clifford.game;

import com.codenjoy.dojo.clifford.model.items.door.Door;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.GENERATE_KEYS;
import static org.junit.Assert.assertEquals;

public class KeyDoorGameTest extends AbstractGameTest {

    @Test
    public void accessGivenDoorsAndKeys() {
        // given
        givenFl("☼☼☼☼☼☼☼☼" +
                "☼      ☼" +
                "☼⍙⍙⍚⍚⍜⍜☼" +
                "☼ ⍍⌺⌺⌼ ☼" +
                "☼ ✦✼⍟  ☼" +
                "☼ ✦ ⍟ ►☼" +
                "☼######☼" +
                "☼☼☼☼☼☼☼☼");

        // then door
        assertEquals(10, field.doors().all().size());
        assertEquals(6, field.doors().all().stream()
                .filter(Door::isOpened)
                .count());
        assertEquals(4, field.doors().all().stream()
                .filter(Door::isClosed)
                .count());

        // then keys
        assertEquals(5, field.keys().all().size());
        assertEquals(2, field.keys().all().stream()
                .filter(key -> key.getKeyType().isGold())
                .count());

        assertE("☼☼☼☼☼☼☼☼" +
                "☼      ☼" +
                "☼⍙⍙⍚⍚⍜⍜☼" +
                "☼ ⍍⌺⌺⌼ ☼" +
                "☼ ✦✼⍟  ☼" +
                "☼ ✦ ⍟ ►☼" +
                "☼######☼" +
                "☼☼☼☼☼☼☼☼");
    }

    @Test
    public void pickKeysWithoutGeneration() {
        settings.bool(GENERATE_KEYS, false);

        givenFl("☼☼☼☼☼☼☼☼" +
                "☼      ☼" +
                "☼      ☼" +
                "☼      ☼" +
                "☼ ►✦✦✼⍟☼" +
                "☼######☼" +
                "☼      ☼" +
                "☼☼☼☼☼☼☼☼");
        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼" +
                "☼      ☼" +
                "☼      ☼" +
                "☼      ☼" +
                "☼  ►✦✼⍟☼" +
                "☼######☼" +
                "☼      ☼" +
                "☼☼☼☼☼☼☼☼");
        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼" +
                "☼      ☼" +
                "☼      ☼" +
                "☼      ☼" +
                "☼   ►✼⍟☼" +
                "☼######☼" +
                "☼      ☼" +
                "☼☼☼☼☼☼☼☼");
        assertEquals("{GOLD=2, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼" +
                "☼      ☼" +
                "☼      ☼" +
                "☼      ☼" +
                "☼    ►⍟☼" +
                "☼######☼" +
                "☼      ☼" +
                "☼☼☼☼☼☼☼☼");
        assertEquals("{GOLD=2, SILVER=1, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼" +
                "☼      ☼" +
                "☼      ☼" +
                "☼      ☼" +
                "☼     ►☼" +
                "☼######☼" +
                "☼      ☼" +
                "☼☼☼☼☼☼☼☼");
        assertEquals("{GOLD=2, SILVER=1, BRONZE=1}", hero().getKeys().toString());

        hero().clearScores();
        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void pickKeysWithGeneration() {
        settings.bool(GENERATE_KEYS, true);

        givenFl("☼☼☼☼☼☼☼☼" +
                "☼      ☼" +
                "☼      ☼" +
                "☼      ☼" +
                "☼ ►✦✦✼⍟☼" +
                "☼######☼" +
                "☼      ☼" +
                "☼☼☼☼☼☼☼☼");
        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        dice(1, 5);
        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼" +
                "☼      ☼" +
                "☼✦     ☼" +
                "☼      ☼" +
                "☼  ►✦✼⍟☼" +
                "☼######☼" +
                "☼      ☼" +
                "☼☼☼☼☼☼☼☼");
        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        dice(2, 5);
        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼" +
                "☼      ☼" +
                "☼✦✦    ☼" +
                "☼      ☼" +
                "☼   ►✼⍟☼" +
                "☼######☼" +
                "☼      ☼" +
                "☼☼☼☼☼☼☼☼");
        assertEquals("{GOLD=2, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        dice(3, 5);
        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼" +
                "☼      ☼" +
                "☼✦✦✼   ☼" +
                "☼      ☼" +
                "☼    ►⍟☼" +
                "☼######☼" +
                "☼      ☼" +
                "☼☼☼☼☼☼☼☼");
        assertEquals("{GOLD=2, SILVER=1, BRONZE=0}", hero().getKeys().toString());

        dice(4, 5);
        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼" +
                "☼      ☼" +
                "☼✦✦✼⍟  ☼" +
                "☼      ☼" +
                "☼     ►☼" +
                "☼######☼" +
                "☼      ☼" +
                "☼☼☼☼☼☼☼☼");
        assertEquals("{GOLD=2, SILVER=1, BRONZE=1}", hero().getKeys().toString());

        hero().clearScores();
        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void heroCannotGoThroughClosedDoor() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ► ⍍  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ►⍍  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ►⍍  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");
    }

    @Test
    public void heroCanGoThroughOpenedDoor() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ► ⍙  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ►⍙  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼    ►  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼    ⍙► ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");
    }

    @Test
    public void openDoor_right() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ►✦⍍  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ►⍍  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ►⍙  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼    ►  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼    ⍙► ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");
    }

    @Test
    public void openDoor_left() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ⍍✦◄  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ⍍◄   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ⍙◄   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ◄    ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().left();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼ ◄⍙    ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");
    }

    @Test
    public void heroCannotOpenDoorWithoutKeys() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ►⍍  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ►⍍  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ►⍍  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void closeDoor_right() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ►✦⍙  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ►⍙  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼    ►  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼    ⍙► ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().left();
        hero().act(2);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼    ⍍◄ ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void closeDoor_left() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ⍙✦◄  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ⍙◄   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ◄    ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().left();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼ ◄⍙    ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().right();
        hero().act(2);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼ ►⍍    ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void closeDoorAtHeroPositionHasNoEffect() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ⍙✦◄  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ⍙◄   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ◄    ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().right();
        hero().act(2);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼  ⍙►   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=1, SILVER=0, BRONZE=0}", hero().getKeys().toString());
    }

    @Test
    public void heroCannotCloseDoorWithoutKeys() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ►⍙  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼    ►  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().right();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼    ⍙► ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("{GOLD=0, SILVER=0, BRONZE=0}", hero().getKeys().toString());

        hero().left();
        hero().act(2);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼    ◄  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().left();
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ◄⍙  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");
    }
}
