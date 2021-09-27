package com.codenjoy.dojo.clifford.game;

import com.codenjoy.dojo.clifford.model.items.Potion;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BulletGameTest extends AbstractGameTest {

    @Test
    public void heroCanShoot_rightDirection() {
        givenFl("☼☼☼☼☼" +
                "☼   ☼" +
                "☼►  ☼" +
                "☼###☼" +
                "☼☼☼☼☼");

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼" +
                "☼   ☼" +
                "☼►• ☼" +
                "☼###☼" +
                "☼☼☼☼☼");
        assertEquals(1, field.bullets().size());
    }

    @Test
    public void heroCanShoot_leftDirection() {
        givenFl("☼☼☼☼☼" +
                "☼   ☼" +
                "☼  ◄☼" +
                "☼###☼" +
                "☼☼☼☼☼");

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼" +
                "☼   ☼" +
                "☼ •◄☼" +
                "☼###☼" +
                "☼☼☼☼☼");
        assertEquals(1, field.bullets().size());
    }

    @Test
    public void heroKillOtherHero() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►  ►   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►  (   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►• (   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►  Z   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        events.verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [HERO_DIE]\n");
    }

    @Test
    public void heroWithMaskIsImmortal_bulletGoThrough() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►  ► ► ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");
        hero(1).pick(Potion.PotionType.MASK_POTION);

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►  ⋉ ( ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero(0).act(1);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►• ⋉ ( ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►  ⋉ ( ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►  ⋉ Z ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        events.verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => []\n" +
                "listener(2) => [HERO_DIE]\n");
    }
}
