package com.codenjoy.dojo.clifford.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
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


import com.codenjoy.dojo.clifford.model.items.Brick;
import com.codenjoy.dojo.clifford.model.items.potion.PotionType;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.Direction.LEFT;
import static com.codenjoy.dojo.services.Direction.RIGHT;
import static com.codenjoy.dojo.services.PointImpl.pt;
import static com.codenjoy.dojo.services.round.RoundSettings.Keys.*;

public class MultiplayerTest extends AbstractGameTest {

    // появляется другие игроки, игра становится многопользовательской
    @Test
    public void shouldMultipleGame() { // TODO разделить тест на части
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼► ► ☼\n" +
                "☼####☼\n" +
                "☼ ► $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼\n" +
                "☼► » ☼\n" +
                "☼####☼\n" +
                "☼ » $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼» ► ☼\n" +
                "☼####☼\n" +
                "☼ » $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼» » ☼\n" +
                "☼####☼\n" +
                "☼ ► $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 2);

        // when
        hero(0).right();
        hero(1).right();
        hero(2).left();

        tick();

        // then        
        assertF("☼☼☼☼☼☼\n" +
                "☼ ► »☼\n" +
                "☼####☼\n" +
                "☼«  $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼ » ►☼\n" +
                "☼####☼\n" +
                "☼«  $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼ » »☼\n" +
                "☼####☼\n" +
                "☼◄  $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 2);

        // when
        hero(0).crack();
        game(1).close();

        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼ ►  ☼\n" +
                "☼##*#☼\n" +
                "☼«  $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼ »  ☼\n" +
                "☼##*#☼\n" +
                "☼◄  $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 2);

        try {
            assertF("☼☼☼☼☼☼\n" +
                    "☼ »  ☼\n" +
                    "☼##*#☼\n" +
                    "☼«  $☼\n" +
                    "☼####☼\n" +
                    "☼☼☼☼☼☼\n", 1);
        } catch (IllegalStateException e) {
            assertEquals("No board for this player", e.getMessage());
        }

        // when
        hero(0).right();

        tick();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼## #☼\n" +
                "☼« ►$☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼## #☼\n" +
                "☼◄ »$☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 2);

        // when
        hero(0).crack(LEFT);
        hero(2).right();

        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼## #☼\n" +
                "☼ F◄$☼\n" +
                "☼#*##☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼## #☼\n" +
                "☼ U«$☼\n" +
                "☼#*##☼\n" +
                "☼☼☼☼☼☼\n", 2);

        // when
        for (int count = 2; count < Brick.CRACK_TIMER; count++) {
            tick();
        }

        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼####☼\n" +
                "☼  ◄$☼\n" +
                "☼#C##☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼####☼\n" +
                "☼  «$☼\n" +
                "☼#O##☼\n" +
                "☼☼☼☼☼☼\n", 2);

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [HERO_DIED]\n" +
                "listener(2) => [HERO_DIED]\n");

        assertEquals(true, game(1).isGameOver());

        // when
        dice(1, 4);
        game(2).newGame();

        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼»   ☼\n" +
                "☼####☼\n" +
                "☼  ◄$☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼►   ☼\n" +
                "☼####☼\n" +
                "☼  «$☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 2);

        // when
        hero(0).right();

        dice(0); // free cell index for new gold

        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼»   ☼\n" +
                "☼####☼\n" +
                "☼$  ►☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼►   ☼\n" +
                "☼####☼\n" +
                "☼$  »☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 2);

        verifyAllEvents(
                "listener(0) => [GET_CLUE_KNIFE(1)]\n");
    }

    @Test
    public void thatRobbersDoNotHauntMaskPlayers() {
        // given
        settings().integer(ROBBERS_COUNT, 1)
                .integer(MASK_POTIONS_COUNT, 1);
        
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼► ( ► ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().disableMock();
        hero(1).pick(PotionType.MASK_POTION);

        // when
        // охотимся за первым игроком // TODO потестить когда поохотимся за вторым
        dice(0);
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼»)  w ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void thatTwoMasksWalkThroughEachOther() {
        // given
        settings().integer(MASK_POTIONS_COUNT, 1);
        
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero(0).pick(PotionType.MASK_POTION);
        hero(1).pick(PotionType.MASK_POTION);

        hero(0).right();

        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ w    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ w    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");
    }

    @Test
    public void thatMaskKillsNonMaskPlayer_killHero() {
        // given
        settings().integer(MASK_POTIONS_COUNT, 1);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero().pick(PotionType.MASK_POTION);

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼w»    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼z►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        hero().right();

        tick();

        // then
        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ w    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ O    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        field().remove(player(1)); // он геймовер его уберут
        tick();

        // then
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ w    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ O    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void thatMaskKillsNonMaskPlayer_killEnemy() {
        // given
        settings().integer(MASK_POTIONS_COUNT, 1);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        player(0).inTeam(1);
        player(1).inTeam(2);

        // when
        hero().pick(PotionType.MASK_POTION);

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼wQ    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼q►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        hero().right();

        tick();

        // then
        verifyAllEvents(
                "listener(0) => [KILL_ENEMY_HERO]\n" +
                "listener(1) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ w    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ O    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        field().remove(player(1)); // он геймовер его уберут
        tick();

        // then
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ w    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ O    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void thatMaskFallsAtTheRegularPlayerAndKillsHim() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►     ☼\n" +
                "☼►     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        settings().integer(MASK_POTIONS_COUNT, 1);
        hero().pick(PotionType.MASK_POTION);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼u     ☼\n" +
                "☼»     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼f     ☼\n" +
                "☼►     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        dice(10); // free cell index for new potion
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  m   ☼\n" +
                "☼w     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  m   ☼\n" +
                "☼O     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        field().remove(player(1)); // он геймовер его уберут
        tick();

        // then
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  m   ☼\n" +
                "☼w     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  m   ☼\n" +
                "☼O     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void thatMaskStairsUpTheLadderAtTheRegularPlayerAndKillsHim() {
        // given
        settings().integer(MASK_POTIONS_COUNT, 1);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►   ☼\n" +
                "☼ ►H   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero(1).pick(PotionType.MASK_POTION);

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►   ☼\n" +
                "☼ zH   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  »   ☼\n" +
                "☼ wH   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        hero(1).right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►   ☼\n" +
                "☼  d   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  »   ☼\n" +
                "☼  a   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        hero(1).up();
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [HERO_DIED]\n" +
                "listener(1) => [KILL_OTHER_HERO]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  O   ☼\n" +
                "☼  H   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  w   ☼\n" +
                "☼  H   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        field().remove(player(0)); // он геймовер его уберут
        tick();

        // then
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  O   ☼\n" +
                "☼  H   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  w   ☼\n" +
                "☼  H   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    // можно ли проходить героям друг через дурга? Нет
    @Test
    public void shouldICantGoViaAnotherPlayer_whenAtWay() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►►  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►»  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero(0).right();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►»  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero(1).left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►«  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void shouldICantGoViaAnotherPlayer_whenAtLadder() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼ ►  ☼\n" +
                "☼ H  ☼\n" +
                "☼►H  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼\n" +
                "☼ »  ☼\n" +
                "☼ H  ☼\n" +
                "☼►H  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);

        // when
        hero(0).down();
        hero(1).right();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ D  ☼\n" +
                "☼ A  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);

        // when
        hero(0).down();
        hero(1).up();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ D  ☼\n" +
                "☼ A  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);

        // when
        hero(0).down();
        hero(1).up();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ D  ☼\n" +
                "☼ A  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void shouldICantGoViaAnotherPlayer_whenAtPipe() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼►~~►☼\n" +
                "☼#  #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼►~~»☼\n" +
                "☼#  #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero(0).right();
        hero(1).left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ IJ ☼\n" +
                "☼#  #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ JI ☼\n" +
                "☼#  #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);

        // when
        hero(0).right();
        hero(1).left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ IJ ☼\n" +
                "☼#  #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero(0).right();
        hero(1).left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ IJ ☼\n" +
                "☼#  #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // могу ли я прострелить под другим героем? Нет
    @Test
    public void shouldICantCrackUnderOtherHero() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►►  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►»  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero(0).crack();
        hero(1).crack(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►«  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // если я прыгаю сверху на героя, то я должен стоять у него на голове
    @Test
    public void shouldICanStayAtOtherHeroHead() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼ ►  ☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼\n" +
                "☼ U  ☼\n" +
                "☼    ☼\n" +
                "☼ »  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ »  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero().down();  //и даже если я сильно захочу я не смогу впрыгнуть в него

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ »  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // если я прыгаю сверху на героя который на трубе, то я должен стоять у него на голове
    @Test
    public void shouldICantStayAtOtherHeroHeadWhenOnPipe() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼ ►  ☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ ~  ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ U  ☼\n" +
                "☼    ☼\n" +
                "☼ J  ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ J  ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero().down();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ J  ☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

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

        // when
        hero(0).pick(PotionType.MASK_POTION);

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼w$&@  »☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");


        // when
        // Hero(1)  continuously generates bullets
        // hero(0) with MASK_POTION trying to get clues through under the flying bullets
        hero(1).shoot(LEFT);
        tick();

        hero(1).shoot(LEFT);
        tick();

        hero(1).shoot(LEFT);
        tick();

        hero(1).shoot(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼w$&@ •«☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        assertBullets("[[0,3,RIGHT], [2,3,LEFT], [4,3,LEFT], [6,3,LEFT]]");

        // when
        hero(0).right();
        hero(1).shoot(LEFT);
        dice(-1); // не генерим ничего нового на поле
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ w&@ •«☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        verifyAllEvents("listener(0) => [GET_CLUE_KNIFE(1)]\n");

        // when
        hero(0).right();
        hero(1).shoot(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ •w@ •«☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        verifyAllEvents("listener(0) => [GET_CLUE_GLOVE(1)]\n");

        // when
        hero(0).right();
        hero(1).shoot(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ • w •«☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        verifyAllEvents("listener(0) => [GET_CLUE_RING(1)]\n");

        // when
        hero(0).right();
        hero(1).shoot(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼       ☼\n" +
                "☼ • •w•C☼\n" +
                "☼#######☼\n" +
                "☼       ☼\n" +
                "☼☼☼☼☼☼☼☼☼\n");

        verifyAllEvents("listener(1) => [HERO_DIED, SUICIDE]\n");
    }

    @Test
    public void shouldCanMoveWhenAtOtherHero() {
        // given
        shouldICantStayAtOtherHeroHeadWhenOnPipe();

        // when
        hero(1).left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ U  ☼\n" +
                "☼«~  ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼«I  ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero(1).right();  // нельзя входить в друг в друга :) даже на трубе
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼»I  ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero(0).left();  // нельзя входить в друг в друга :) даже на трубе
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼»I  ☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // когда два героя на трубе, они не могут друг в друга войти
    @Test
    public void shouldStopOnPipe() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼ ►► ☼\n" +
                "☼~~~~☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼~IJ~☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero(1).left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼~IJ~☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero(0).right();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼~IJ~☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero(0).right();
        hero(1).left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼~IJ~☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void shouldICanGoWhenIAmAtOtherHero() {
        // given
        shouldICanStayAtOtherHeroHead();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ »  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼U   ☼\n" +
                "☼ »  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼◄»  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void shouldICanGoWhenAtMeIsOtherHero() {
        // given
        shouldICanStayAtOtherHeroHead();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ »  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        hero(1).right();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ U  ☼\n" +
                "☼  » ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ ►» ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // тест с раундами: игра не начнется, пока не соберутся игроки
    // в достаточном количестве
    // TODO тест реально не тестирует этого, а просто эмулирует -
    //      возможно в будущем придумается фреймворк, который будет
    //      тестить эту фичу вглубь с rooms логикой которая сейчас на
    //      стороне сервера а могла бы быть в engine.
    @Test
    public void shouldStopGame_whenRoundIsNotStarted() {
        // given
        settings().bool(ROUNDS_ENABLED, true)
                .integer(ROUNDS_TIME_BEFORE_START, 1)
                .integer(ROUNDS_PER_MATCH, 1)
                .integer(ROUNDS_PLAYERS_PER_ROOM, 3);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼► ►   ☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // юзеров недостаточно, ничего не происходит
        tryTick();

        // then
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼O C   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼C O   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        // попытка переместиться
        hero(0).right();
        hero(1).right();

        tryTick();

        // then
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼O C   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼C O   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        givenPlayer(pt(5, 1));

        // вот а тут уже укомплектована комната - погнали!
        tryTick();

        // then
        verifyAllEvents(
                "listener(0) => [START_ROUND, [Round 1]]\n" +
                "listener(1) => [START_ROUND, [Round 1]]\n" +
                "listener(2) => [START_ROUND, [Round 1]]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼► » » ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼» ► » ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼» » ► ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 2);

        // when
        // попытка переместиться
        hero(0).right();
        hero(1).right();
        hero(2).right();

        tryTick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ► » »☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ » ► »☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ » » ►☼\n" +
                "☼☼☼☼☼☼☼☼\n", 2);
    }

    private void tryTick() {
        // эмуляция проверки загрузки комнаты, если комната недогружена то не тикаем
        // вообще это делает фреймворк, тут лишь эмулируем
        if (settings().getPlayersPerRoom() != players().size()) {
            return;
        }

        tick();
    }

    @Test
    public void winnerIsTheOneWhoBuriedTheMostPlayers() {
        // given
        settings().bool(ROUNDS_ENABLED, true)
                .integer(ROUNDS_TIME_BEFORE_START, 1)
                .integer(ROUNDS_PER_MATCH, 1)
                .integer(ROUNDS_TIME, 30)
                .integer(ROUNDS_TIME_FOR_WINNER, 5)
                .integer(ROUNDS_PLAYERS_PER_ROOM, 8)
                .integer(KILL_OTHER_HERO_SCORE, 1)
                .integer(ROUND_WIN, 50);

        givenEightPlayers();

        // when
        crack(2, 3);

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼»      »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼»  HH  »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ F◄HH»F ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        goUp();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼»      »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼» ◄HH» »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        crack(4, 5);

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼»      »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ F◄HH»F ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        goUp();

        // then
        assertScores("");

        verifyAllEvents("");

        // when
        tick();

        // then
        assertScores(
                "hero(0)=1\n" +
                "hero(1)=1");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [KILL_OTHER_HERO]\n" +
                "listener(2) => [HERO_DIED]\n" +
                "listener(3) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼» ◄  » »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#C#HH#C#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tickAndRemoveDied();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼» ◄  » »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        crack(6, 7);

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼ F◄  »F ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  »  ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();
        tick();

        // then
        assertScores(
                "hero(0)=1\n" +
                "hero(1)=1");

        verifyAllEvents("");

        // when
        tick();

        // then
        assertScores(
                "hero(0)=2\n" +
                "hero(1)=2");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [KILL_OTHER_HERO]\n" +
                "listener(4) => [HERO_DIED]\n" +
                "listener(5) => [HERO_DIED]\n");


        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  »  ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#C#HH#C#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tickAndRemoveDied();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  »  ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        // then
        assertScores(
                "hero(0)=2\n" +
                "hero(1)=2");

        verifyAllEvents("");

        // when
        tick();

        // then
        assertScores(
                "hero(0)=3\n" +
                "hero(1)=3");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [KILL_OTHER_HERO]\n" +
                "listener(6) => [HERO_DIED]\n" +
                "listener(7) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  »  ☼\n" +
                "☼#C#HH#C#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tickAndRemoveDied();

        // then
        assertScores(
                "hero(0)=3\n" +
                "hero(1)=3");

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  »  ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  »  ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertScores(
                "hero(0)=53\n" +
                "hero(1)=53");

        verifyAllEvents(
                "listener(0) => [WIN_ROUND]\n" +
                "listener(1) => [WIN_ROUND]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  O  C  ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertEquals(false, hero(0).isAlive());
        assertEquals(false, hero(1).isAlive());

        // when
        dice(1, 2);
        game(0).newGame();

        dice(8, 2);
        game(1).newGame();

        // then
        assertScores("");
        assertEquals(2, field().countPlayers());

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼O  HH  C☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertScores("");
        assertEquals(2, field().countPlayers());

        verifyAllEvents(
                "listener(0) => [START_ROUND, [Round 2]]\n" +
                "listener(1) => [START_ROUND, [Round 2]]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼►  HH  »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void twoWinnersIfTheyHaveEqualKillsBeforeTimeout_caseOneRounds() {
        // given
        settings().bool(ROUNDS_ENABLED, true)
                .integer(ROUNDS_TIME_BEFORE_START, 1)
                .integer(ROUNDS_PER_MATCH, 1)
                .integer(ROUNDS_TIME, 30)
                .integer(ROUNDS_TIME_FOR_WINNER, 5)
                .integer(ROUNDS_PLAYERS_PER_ROOM, 8)
                .integer(KILL_OTHER_HERO_SCORE, 1)
                .integer(ROUND_WIN, 50);

        givenEightPlayers();

        // when
        crack(2, 3);

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼»      »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼»  HH  »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ F◄HH»F ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        goUp();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼»      »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼» ◄HH» »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        crack(4, 5);

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼»      »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ F◄HH»F ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        goUp();

        // then
        assertScores("");

        verifyAllEvents("");

        // when
        tick();

        // then
        assertScores(
                "hero(0)=1\n" +
                "hero(1)=1");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [KILL_OTHER_HERO]\n" +
                "listener(2) => [HERO_DIED]\n" +
                "listener(3) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼» ◄  » »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#C#HH#C#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tickAndRemoveDied();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼» ◄  » »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        crack(6, -1);

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼ F◄  » »☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  » »☼\n" +
                "☼#K#HH# #☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();
        tick();

        // then
        assertScores(
                "hero(0)=1\n" +
                "hero(1)=1");

        verifyAllEvents("");

        // when
        tick();

        // then
        assertScores(
                "hero(0)=2\n" +
                "hero(1)=2");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [KILL_OTHER_HERO]\n" +
                "listener(4) => [HERO_DIED]\n" +
                "listener(5) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  » »☼\n" +
                "☼#K#HH# #☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#C#HH#C#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tickAndRemoveDied();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  » »☼\n" +
                "☼#K#HH# #☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        // then
        assertScores(
                "hero(0)=2\n" +
                "hero(1)=2");

        verifyAllEvents("");

        // when
        tick();

        // then
        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(6) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  » »☼\n" +
                "☼#C#HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tickAndRemoveDied();

        // then
        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  » »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");

        // when
        tick();

        // then
        assertScores(
                "hero(0)=53\n" +
                "hero(1)=2");

        verifyAllEvents(
                "listener(0) => [WIN_ROUND]\n" +
                "listener(1) => [[Time is over]]\n" +
                "listener(7) => [[Time is over]]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  O  C C☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        assertEquals(false, hero(0).isAlive());
        assertEquals(false, hero(1).isAlive());
        assertEquals(false, hero(7).isAlive());

        // when
        dice(1, 2);
        game(0).newGame();

        dice(2, 2);
        game(1).newGame();

        dice(3, 2);
        game(2).newGame();

        dice(6, 2);
        game(3).newGame();

        dice(7, 2);
        game(4).newGame();

        dice(8, 2);
        game(5).newGame();

        dice(1, 5);
        game(6).newGame();

        dice(2, 5);
        game(7).newGame();

        // then
        assertScores("");
        assertEquals(8, field().countPlayers());

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼CC HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼OCCHHCCC☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertScores("");
        assertEquals(8, field().countPlayers());

        verifyAllEvents(
                "listener(0) => [START_ROUND, [Round 2]]\n" +
                "listener(1) => [START_ROUND, [Round 2]]\n" +
                "listener(2) => [START_ROUND, [Round 2]]\n" +
                "listener(3) => [START_ROUND, [Round 2]]\n" +
                "listener(4) => [START_ROUND, [Round 2]]\n" +
                "listener(5) => [START_ROUND, [Round 2]]\n" +
                "listener(6) => [START_ROUND, [Round 2]]\n" +
                "listener(7) => [START_ROUND, [Round 2]]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼»» HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼►»»HH»»»☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void twoWinnersIfTheyHaveEqualKillsBeforeTimeout_caseTwoRounds() {
        // given
        settings().bool(ROUNDS_ENABLED, true)
                .integer(ROUNDS_TIME_BEFORE_START, 1)
                .integer(ROUNDS_PER_MATCH, 2)
                .integer(ROUNDS_TIME, 30)
                .integer(ROUNDS_TIME_FOR_WINNER, 5)
                .integer(ROUNDS_PLAYERS_PER_ROOM, 8)
                .integer(KILL_OTHER_HERO_SCORE, 1)
                .integer(ROUND_WIN, 50);

        givenEightPlayers();

        // when
        crack(2, 3);

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼»      »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼»  HH  »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ F◄HH»F ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        goUp();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼»      »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼» ◄HH» »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        crack(4, 5);

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼»      »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ F◄HH»F ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        goUp();

        // then
        assertScores("");

        verifyAllEvents("");

        // when
        tick();

        // then
        assertScores(
                "hero(0)=1\n" +
                "hero(1)=1");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [KILL_OTHER_HERO]\n" +
                "listener(2) => [HERO_DIED]\n" +
                "listener(3) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼» ◄  » »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#C#HH#C#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tickAndRemoveDied();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼» ◄  » »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        crack(6, -1);

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼ F◄  » »☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  » »☼\n" +
                "☼#K#HH# #☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();
        tick();

        // then
        assertScores(
                "hero(0)=1\n" +
                "hero(1)=1");

        verifyAllEvents("");

        // when
        tick();

        // then
        assertScores(
                "hero(0)=2\n" +
                "hero(1)=2");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [KILL_OTHER_HERO]\n" +
                "listener(4) => [HERO_DIED]\n" +
                "listener(5) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  » »☼\n" +
                "☼#K#HH# #☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#C#HH#C#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tickAndRemoveDied();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  » »☼\n" +
                "☼#K#HH# #☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        // then
        assertScores(
                "hero(0)=2\n" +
                "hero(1)=2");

        verifyAllEvents("");

        // when
        tick();

        // then
        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(6) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  » »☼\n" +
                "☼#C#HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tickAndRemoveDied();

        // then
        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");

        // when
        verifyAllEvents("");

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  » »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");

        dice(3, 2,
            6, 2,
            8, 2);

        // when
        tick();

        // then
        assertScores("");

        verifyAllEvents(
                "listener(0) => [WIN_ROUND]\n" +
                "listener(1) => [[Time is over]]\n" +
                "listener(7) => [[Time is over]]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼  OHHC C☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertScores("");

        verifyAllEvents(
                "listener(0) => [START_ROUND, [Round 2]]\n" +
                "listener(1) => [START_ROUND, [Round 2]]\n" +
                "listener(7) => [START_ROUND, [Round 2]]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼  ►HH» »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertScores("");

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼  ►HH» »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void heroKillHeroAndKillEnemy() {
        // given
        settings().bool(ROUNDS_ENABLED, true)
                .integer(ROUNDS_TIME_BEFORE_START, 1)
                .integer(ROUNDS_PLAYERS_PER_ROOM, 4)
                .integer(KILL_OTHER_HERO_SCORE, 1)
                .integer(KILL_ENEMY_HERO_SCORE, 10);

        givenFourPlayers();

        // when
        crack(2, 3);

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ F◄HHQF ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        goUp();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼  ◄HHQ  ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼  ◄HHQ  ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#K#HH#K#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        // when
        goUp();

        // then
        assertScores("");

        verifyAllEvents("");

        // when
        tick();

        // then
        assertScores(
                "hero(0)=1\n" +
                "hero(1)=10");

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [KILL_ENEMY_HERO]\n" +
                "listener(2) => [HERO_DIED]\n" +
                "listener(3) => [HERO_DIED]\n");
    }

    private void givenFourPlayers() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n");

        givenPlayer(pt(3, 2)).inTeam(1);
        givenPlayer(pt(6, 2)).inTeam(2);

        givenPlayer(pt(1, 2)).inTeam(1);
        givenPlayer(pt(8, 2)).inTeam(1);

        // when
        tick();

        // then
        assertScores("");

        verifyAllEvents(
                "listener(0) => [START_ROUND, [Round 1]]\n" +
                "listener(1) => [START_ROUND, [Round 1]]\n" +
                "listener(2) => [START_ROUND, [Round 1]]\n" +
                "listener(3) => [START_ROUND, [Round 1]]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼» ►HHQ »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);
    }

    private void givenEightPlayers() {
        // given
        givenFl("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n");

        givenPlayer(pt(3, 2)); // соревнующиеся ребята
        givenPlayer(pt(6, 2));

        givenPlayer(pt(1, 2)); // 1 этаж
        givenPlayer(pt(8, 2));

        givenPlayer(pt(1, 5)); // 2 этаж
        givenPlayer(pt(8, 5));

        givenPlayer(pt(1, 8)); // 3 этаж
        givenPlayer(pt(8, 8));

        // when
        tick();

        // then
        assertScores("");

        verifyAllEvents(
                "listener(0) => [START_ROUND, [Round 1]]\n" +
                "listener(1) => [START_ROUND, [Round 1]]\n" +
                "listener(2) => [START_ROUND, [Round 1]]\n" +
                "listener(3) => [START_ROUND, [Round 1]]\n" +
                "listener(4) => [START_ROUND, [Round 1]]\n" +
                "listener(5) => [START_ROUND, [Round 1]]\n" +
                "listener(6) => [START_ROUND, [Round 1]]\n" +
                "listener(7) => [START_ROUND, [Round 1]]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼»      »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼»  HH  »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼» ►HH» »☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);
    }

    private void goUp() {
        // when
        // идут на следующий этаж
        hero(0).right();
        hero(1).left();
        tick();

        hero(0).up();
        hero(1).up();
        tick();

        hero(0).up();
        hero(1).up();
        tick();

        hero(0).up();
        hero(1).up();
        tick();

        hero(0).left();
        hero(1).right();
        tick();
    }

    private void crack(int leftPrey, int rightPrey) {
        // when
        // простреливают ямки
        hero(0).crack(LEFT);
        hero(1).crack(RIGHT);
        // падают в ямки
        if (leftPrey != -1) {
            hero(leftPrey).right();
        }
        if (rightPrey != -1) {
            hero(rightPrey).left();
        }
        tick();
    }

    private void tickAndRemoveDied() {
        // when
        tick();
        removeAllDied();
    }
}
