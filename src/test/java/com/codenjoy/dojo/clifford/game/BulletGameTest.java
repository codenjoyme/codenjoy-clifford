package com.codenjoy.dojo.clifford.game;

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
}
