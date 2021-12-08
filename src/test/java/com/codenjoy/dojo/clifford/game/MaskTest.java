package com.codenjoy.dojo.clifford.game;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2021 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
