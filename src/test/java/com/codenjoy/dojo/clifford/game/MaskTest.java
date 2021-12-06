package com.codenjoy.dojo.clifford.game;

import com.codenjoy.dojo.clifford.game.check.AbstractGameCheckTest;
import com.codenjoy.dojo.clifford.model.items.Potion;
import org.junit.Test;

public class MaskTest extends AbstractGameCheckTest {

    @Test
    public void heroWithMaskShouldGetClues_bulletGoThrough() {

        // given
        givenFl("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼►$&@  ►☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        hero(0).pick(Potion.PotionType.MASK_POTION);

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼w$&@  »☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        // given Hero(1)  continuously generates bullets
        // when hero(0) with MASK_POTION trying to get clues through under the flying bullets
        hero(1).left();
        hero(1).act(1);
        tick();
        hero(1).left();
        hero(1).act(1);
        tick();
        hero(1).left();
        hero(1).act(1);
        tick();
        hero(1).left();
        hero(1).act(1);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼w$&@• «☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[1,3,LEFT], [3,3,LEFT], [5,3,LEFT], [7,3,LEFT]]");

        hero(0).right();
        hero(1).left();
        hero(1).act(1);
        tick();

        // then should get CLUE_KNIFE
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼•w&@• «☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");
        verifyAllEvents("listener(0) => [GET_CLUE_KNIFE(1)]\n");

        hero(0).right();
        hero(1).left();
        hero(1).act(1);
        tick();

        // then should get CLUE_GLOVE
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼• w@• «☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        verifyAllEvents("listener(0) => [GET_CLUE_GLOVE(1)]\n");

        hero(0).right();
        hero(1).left();
        hero(1).act(1);
        tick();

        // then should get CLUE_RING
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼• •w• «☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        verifyAllEvents("listener(0) => [GET_CLUE_RING(1)]\n");

        hero(0).right();
        hero(1).left();
        hero(1).act(1);
        tick();

        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼• • w C☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        verifyAllEvents("listener(1) => [HERO_DIE, SUICIDE]\n");
    }
}
