package com.codenjoy.dojo.clifford.game;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
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
import com.codenjoy.dojo.clifford.model.items.Brick;
import com.codenjoy.dojo.clifford.model.items.Potion.PotionType;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.Events.Event.*;
import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.round.RoundSettings.Keys.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class MultiplayerTest extends AbstractGameCheckTest {

    // появляется другие игроки, игра становится мультипользовательской
    @Test
    public void shouldMultipleGame() { // TODO разделить тест на части
        givenFl("☼☼☼☼☼☼\n" +
                "☼► ► ☼\n" +
                "☼####☼\n" +
                "☼ ► $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼\n" +
                "☼► ( ☼\n" +
                "☼####☼\n" +
                "☼ ( $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼( ► ☼\n" +
                "☼####☼\n" +
                "☼ ( $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼( ( ☼\n" +
                "☼####☼\n" +
                "☼ ► $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 2);

        hero(0).right();
        hero(1).right();
        hero(2).left();

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼ ► (☼\n" +
                "☼####☼\n" +
                "☼)  $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼ ( ►☼\n" +
                "☼####☼\n" +
                "☼)  $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼ ( (☼\n" +
                "☼####☼\n" +
                "☼◄  $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 2);

        hero(0).act();
        game(1).close();

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼ ►  ☼\n" +
                "☼##*#☼\n" +
                "☼)  $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼ (  ☼\n" +
                "☼##*#☼\n" +
                "☼◄  $☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 2);

        try {
            assertF("☼☼☼☼☼☼\n" +
                    "☼ (  ☼\n" +
                    "☼##*#☼\n" +
                    "☼)  $☼\n" +
                    "☼####☼\n" +
                    "☼☼☼☼☼☼\n", 1);
        } catch (IllegalStateException e) {
            assertEquals("No board for this player", e.getMessage());
        }

        hero(0).right();

        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼## #☼\n" +
                "☼) ►$☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼## #☼\n" +
                "☼◄ ($☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 2);

        hero(0).left();
        hero(0).act();
        hero(2).right();

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼## #☼\n" +
                "☼ ⊐◄$☼\n" +
                "☼#*##☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼## #☼\n" +
                "☼ ])$☼\n" +
                "☼#*##☼\n" +
                "☼☼☼☼☼☼\n", 2);

        for (int c = 2; c < Brick.CRACK_TIMER; c++) {
            tick();
        }

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼####☼\n" +
                "☼  ◄$☼\n" +
                "☼#Z##☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼####☼\n" +
                "☼  )$☼\n" +
                "☼#Ѡ##☼\n" +
                "☼☼☼☼☼☼\n", 2);

        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [HERO_DIE]\n" +
                "listener(2) => [HERO_DIE]\n");

        assertEquals(true, game(1).isGameOver());

        dice(1, 4);
        game(2).newGame();

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼(   ☼\n" +
                "☼####☼\n" +
                "☼  ◄$☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼►   ☼\n" +
                "☼####☼\n" +
                "☼  )$☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 2);

        hero(0).right();

        dice(1, 2);

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼(   ☼\n" +
                "☼####☼\n" +
                "☼$  ►☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼►   ☼\n" +
                "☼####☼\n" +
                "☼$  (☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 2);

        verifyAllEvents(
                "listener(0) => [GET_CLUE_KNIFE(1)]\n");
    }

    @Test
    public void thatRobbersDoNotHauntMaskPlayers() {
        settings().integer(ROBBERS_COUNT, 1)
                .integer(MASK_POTIONS_COUNT, 1);
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼► » ► ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().disableMock();
        hero(1).pick(PotionType.MASK_POTION);

        dice(0); // охотимся за первым игроком // TODO потестить когда поохотимся за вторым
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼(«  ⊳ ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void thatTwoMasksWalkThroughEachOther() {
        settings().integer(MASK_POTIONS_COUNT, 1);
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero(0).pick(PotionType.MASK_POTION);
        hero(1).pick(PotionType.MASK_POTION);

        hero(0).right();

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ⊳    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ⊳    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verify(listener(0), never()).event(KILL_HERO);
        verify(listener(0), never()).event(KILL_ENEMY);
        verify(listener(1), never()).event(HERO_DIE);
    }

    @Test
    public void thatMaskKillsNonMaskPlayer_killHero() {
        settings().integer(MASK_POTIONS_COUNT, 1);
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero().pick(PotionType.MASK_POTION);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼⊳(    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼⋉►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        hero().right();

        tick();

        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [HERO_DIE]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ⊳    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ Ѡ    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        field().remove(player(1)); // он геймовер его уберут
        tick();

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ⊳    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ Ѡ    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void thatMaskKillsNonMaskPlayer_killEnemy() {
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

        hero().pick(PotionType.MASK_POTION);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼⊳❪    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼⧑►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        hero().right();

        tick();

        verifyAllEvents(
                "listener(0) => [KILL_ENEMY]\n" +
                "listener(1) => [HERO_DIE]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ⊳    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ Ѡ    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        field().remove(player(1)); // он геймовер его уберут
        tick();

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ⊳    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ Ѡ    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void thatMaskFallsAtTheRegularPlayerAndKillsHim() {
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
                "☼⊅     ☼\n" +
                "☼(     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼⋣     ☼\n" +
                "☼►     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        dice(3, 3); // new potion
        tick();

        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [HERO_DIE]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  S   ☼\n" +
                "☼⊳     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  S   ☼\n" +
                "☼Ѡ     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        field().remove(player(1)); // он геймовер его уберут
        tick();

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  S   ☼\n" +
                "☼⊳     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  S   ☼\n" +
                "☼Ѡ     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void thatMaskStairsUpTheLadderAtTheRegularPlayerAndKillsHim() {
        settings().integer(MASK_POTIONS_COUNT, 1);
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►   ☼\n" +
                "☼ ►H   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero(1).pick(PotionType.MASK_POTION);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►   ☼\n" +
                "☼ ⋉H   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  (   ☼\n" +
                "☼ ⊳H   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        hero(1).right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►   ☼\n" +
                "☼  ⋕   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  (   ☼\n" +
                "☼  ⍬   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        hero(1).up();
        tick();

        verifyAllEvents(
                "listener(0) => [HERO_DIE]\n" +
                "listener(1) => [KILL_HERO]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  Ѡ   ☼\n" +
                "☼  H   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ⊳   ☼\n" +
                "☼  H   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        field().remove(player(0)); // он геймовер его уберут
        tick();

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  Ѡ   ☼\n" +
                "☼  H   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ⊳   ☼\n" +
                "☼  H   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
    }

    // можно ли проходить героям друг через дурга? Нет
    @Test
    public void shouldICantGoViaAnotherPlayer_whenAtWay() {
        givenFl("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►►  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►(  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero(0).right();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►(  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero(1).left();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►)  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void shouldICantGoViaAnotherPlayer_whenAtLadder() {
        givenFl("☼☼☼☼☼☼\n" +
                "☼ ►  ☼\n" +
                "☼ H  ☼\n" +
                "☼►H  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼\n" +
                "☼ (  ☼\n" +
                "☼ H  ☼\n" +
                "☼►H  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);

        hero(0).down();
        hero(1).right();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ U  ☼\n" +
                "☼ Y  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);

        hero(0).down();
        hero(1).up();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ U  ☼\n" +
                "☼ Y  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);

        hero(0).down();
        hero(1).up();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ U  ☼\n" +
                "☼ Y  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);
    }

    @Test
    public void shouldICantGoViaAnotherPlayer_whenAtPipe() {
        givenFl("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼►~~►☼\n" +
                "☼#  #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼►~~(☼\n" +
                "☼#  #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero(0).right();
        hero(1).left();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ {Э ☼\n" +
                "☼#  #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ Э{ ☼\n" +
                "☼#  #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 1);

        hero(0).right();
        hero(1).left();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ {Э ☼\n" +
                "☼#  #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero(0).right();
        hero(1).left();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ {Э ☼\n" +
                "☼#  #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // могу ли я прострелить под другим героем? Нет
    @Test
    public void shouldICantCrackUnderOtherHero() {
        givenFl("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►►  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►(  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero(0).act();
        hero(1).left();
        hero(1).act();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼►)  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // если я прыгаю сверху на героя, то я должен стоять у него на голове
    @Test
    public void shouldICanStayAtOtherHeroHead() {
        givenFl("☼☼☼☼☼☼\n" +
                "☼ ►  ☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        assertF("☼☼☼☼☼☼\n" +
                "☼ ]  ☼\n" +
                "☼    ☼\n" +
                "☼ (  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ (  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero().down();  //и даже если я сильно захочу я не смогу впрыгнуть в него

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ (  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // если я прыгаю сверху на героя который на трубе, то я должен стоять у него на голове
    @Test
    public void shouldICantStayAtOtherHeroHeadWhenOnPipe() {
        givenFl("☼☼☼☼☼☼\n" +
                "☼ ►  ☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ ~  ☼\n" +
                "☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ ]  ☼\n" +
                "☼    ☼\n" +
                "☼ Э  ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ Э  ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero().down();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ Э  ☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void shouldCanMoveWhenAtOtherHero() {
        shouldICantStayAtOtherHeroHeadWhenOnPipe();

        hero(1).left();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ ]  ☼\n" +
                "☼)~  ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼){  ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero(1).right();  // нельзя входить в друг в друга :) даже на трубе
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼({  ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero(0).left();  // нельзя входить в друг в друга :) даже на трубе
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼({  ☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // когда два героя на трубе, они не могут друг в друга войти
    @Test
    public void shouldStopOnPipe() {
        givenFl("☼☼☼☼☼☼\n" +
                "☼ ►► ☼\n" +
                "☼~~~~☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼~{Э~☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero(1).left();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼~{Э~☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero(0).right();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼~{Э~☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero(0).right();
        hero(1).left();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼~{Э~☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void shouldICanGoWhenIAmAtOtherHero() {
        shouldICanStayAtOtherHeroHead();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ (  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼]   ☼\n" +
                "☼ (  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼◄(  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void shouldICanGoWhenAtMeIsOtherHero() {
        shouldICanStayAtOtherHeroHead();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ ►  ☼\n" +
                "☼ (  ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        hero(1).right();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ ]  ☼\n" +
                "☼  ( ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ ►( ☼\n" +
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
        tick();

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼Ѡ Z   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼Z Ѡ   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        // попытка переместиться
        hero(0).right();
        hero(1).right();

        tick();

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼Ѡ Z   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼Z Ѡ   ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        givenPlayer(5, 1);

        // вот а тут уже укомплектована комната - погнали!
        tick();

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
                "☼► ( ( ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼( ► ( ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼( ( ► ☼\n" +
                "☼☼☼☼☼☼☼☼\n", 2);

        // when
        // попытка переместиться
        hero(0).right();
        hero(1).right();
        hero(2).right();

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ► ( (☼\n" +
                "☼☼☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ( ► (☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ( ( ►☼\n" +
                "☼☼☼☼☼☼☼☼\n", 2);
    }

    @Test
    public void winnerIsTheOneWhoBuriedTheMostPlayers() {
        settings().bool(ROUNDS_ENABLED, true)
                .integer(ROUNDS_TIME_BEFORE_START, 1)
                .integer(ROUNDS_PER_MATCH, 1)
                .integer(ROUNDS_TIME, 30)
                .integer(ROUNDS_TIME_FOR_WINNER, 5)
                .integer(ROUNDS_PLAYERS_PER_ROOM, 8)
                .integer(KILL_HERO_SCORE, 1);

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

        givenPlayer(3, 2); // соревнующиеся ребята
        givenPlayer(6, 2);

        givenPlayer(1, 2); // 1 этаж
        givenPlayer(8, 2);

        givenPlayer(1, 5); // 2 этаж
        givenPlayer(8, 5);

        givenPlayer(1, 8); // 3 этаж
        givenPlayer(8, 8);

        tick();

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
                "☼(      (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼(  HH  (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼( ►HH( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        crack(2, 3);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼(      (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼(  HH  (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ ⊐◄HH(⊐ ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        goUp();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼(      (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼( ◄HH( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        crack(4, 5);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼(      (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ ⊐◄HH(⊐ ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        goUp();

        assertScores("");
        
        verifyAllEvents("");

        tick();

        assertScores(
                "hero(0)=1\n" +
                "hero(1)=1");
        
        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [KILL_HERO]\n" +
                "listener(2) => [HERO_DIE]\n" +
                "listener(3) => [HERO_DIE]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼( ◄  ( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#Z#HH#Z#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼( ◄  ( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        crack(6, 7);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼ ⊐◄  (⊐ ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  (  ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();
        tick();

        assertScores(
                "hero(0)=1\n" +
                "hero(1)=1");
        
        verifyAllEvents("");

        tick();

        assertScores(
                "hero(0)=2\n" +
                "hero(1)=2");
        
        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [KILL_HERO]\n" +
                "listener(4) => [HERO_DIE]\n" +
                "listener(5) => [HERO_DIE]\n");


        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  (  ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#Z#HH#Z#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  (  ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertScores(
                "hero(0)=2\n" +
                "hero(1)=2");
        
        verifyAllEvents("");

        tick();

        assertScores(
                "hero(0)=3\n" +
                "hero(1)=3");
        
        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [KILL_HERO]\n" +
                "listener(6) => [HERO_DIE]\n" +
                "listener(7) => [HERO_DIE]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  (  ☼\n" +
                "☼#Z#HH#Z#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertScores(
                "hero(0)=3\n" +
                "hero(1)=3");
        
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  (  ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  (  ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertScores(
                "hero(0)=3\n" +
                "hero(1)=3");
        
        verifyAllEvents(
                "listener(0) => [WIN_ROUND]\n" +
                "listener(1) => [WIN_ROUND]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  Ѡ  Z  ☼\n" +
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

        dice(1, 2);
        game(0).newGame();

        dice(8, 2);
        game(1).newGame();

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
                "☼Ѡ  HH  Z☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

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
                "☼►  HH  (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void twoWinnersIfTheyHaveEqualKillsBeforeTimeout_caseOneRounds() {
        settings().bool(ROUNDS_ENABLED, true)
                .integer(ROUNDS_TIME_BEFORE_START, 1)
                .integer(ROUNDS_PER_MATCH, 1)
                .integer(ROUNDS_TIME, 30)
                .integer(ROUNDS_TIME_FOR_WINNER, 5)
                .integer(ROUNDS_PLAYERS_PER_ROOM, 8)
                .integer(KILL_HERO_SCORE, 1);

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

        givenPlayer(3, 2); // соревнующиеся ребята
        givenPlayer(6, 2);

        givenPlayer(1, 2); // 1 этаж
        givenPlayer(8, 2);

        givenPlayer(1, 5); // 2 этаж
        givenPlayer(8, 5);

        givenPlayer(1, 8); // 3 этаж
        givenPlayer(8, 8);

        tick();

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
                "☼(      (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼(  HH  (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼( ►HH( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        crack(2, 3);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼(      (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼(  HH  (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ ⊐◄HH(⊐ ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        goUp();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼(      (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼( ◄HH( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        crack(4, 5);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼(      (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ ⊐◄HH(⊐ ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        goUp();

        assertScores("");
       
        verifyAllEvents("");

        tick();

        assertScores(
                "hero(0)=1\n" +
                "hero(1)=1");
       
        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [KILL_HERO]\n" +
                "listener(2) => [HERO_DIE]\n" +
                "listener(3) => [HERO_DIE]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼( ◄  ( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#Z#HH#Z#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼( ◄  ( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        crack(6, -1);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼ ⊐◄  ( (☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  ( (☼\n" +
                "☼#ᗉ#HH# #☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();
        tick();

        assertScores(
                "hero(0)=1\n" +
                "hero(1)=1");
      
        verifyAllEvents("");

        tick();

        assertScores(
                "hero(0)=2\n" +
                "hero(1)=2");
     
        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [KILL_HERO]\n" +
                "listener(4) => [HERO_DIE]\n" +
                "listener(5) => [HERO_DIE]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  ( (☼\n" +
                "☼#ᗉ#HH# #☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#Z#HH#Z#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  ( (☼\n" +
                "☼#ᗉ#HH# #☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertScores(
                "hero(0)=2\n" +
                "hero(1)=2");
      
        verifyAllEvents("");

        tick();

        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");
     
        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(6) => [HERO_DIE]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  ( (☼\n" +
                "☼#Z#HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");
      
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  ( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");

        tick();

        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");

        verifyAllEvents(
                "listener(0) => [WIN_ROUND]\n" +
                "listener(1) => [[Time is over]]\n" +
                "listener(7) => [[Time is over]]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  Ѡ  Z Z☼\n" +
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


        assertScores("");
        assertEquals(8, field().countPlayers());
     
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ZZ HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ѠZZHHZZZ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

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
                "☼(( HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼►((HH(((☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void twoWinnersIfTheyHaveEqualKillsBeforeTimeout_caseTwoRounds() {
        settings().bool(ROUNDS_ENABLED, true)
                .integer(ROUNDS_TIME_BEFORE_START, 1)
                .integer(ROUNDS_PER_MATCH, 2)
                .integer(ROUNDS_TIME, 30)
                .integer(ROUNDS_TIME_FOR_WINNER, 5)
                .integer(ROUNDS_PLAYERS_PER_ROOM, 8)
                .integer(KILL_HERO_SCORE, 1);

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

        givenPlayer(3, 2); // соревнующиеся ребята
        givenPlayer(6, 2);

        givenPlayer(1, 2); // 1 этаж
        givenPlayer(8, 2);

        givenPlayer(1, 5); // 2 этаж
        givenPlayer(8, 5);

        givenPlayer(1, 8); // 3 этаж
        givenPlayer(8, 8);

        tick();

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
                "☼(      (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼(  HH  (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼( ►HH( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        crack(2, 3);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼(      (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼(  HH  (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ ⊐◄HH(⊐ ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        goUp();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼(      (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼( ◄HH( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        crack(4, 5);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼(      (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ ⊐◄HH(⊐ ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        goUp();

        assertScores("");
      
        verifyAllEvents("");

        tick();

        assertScores(
                "hero(0)=1\n" +
                "hero(1)=1");
        
        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [KILL_HERO]\n" +
                "listener(2) => [HERO_DIE]\n" +
                "listener(3) => [HERO_DIE]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼( ◄  ( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#Z#HH#Z#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼( ◄  ( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        crack(6, -1);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼ ⊐◄  ( (☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  ( (☼\n" +
                "☼#ᗉ#HH# #☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();
        tick();

        assertScores(
                "hero(0)=1\n" +
                "hero(1)=1");
       
        verifyAllEvents("");

        tick();

        assertScores(
                "hero(0)=2\n" +
                "hero(1)=2");
        
        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [KILL_HERO]\n" +
                "listener(4) => [HERO_DIE]\n" +
                "listener(5) => [HERO_DIE]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  ( (☼\n" +
                "☼#ᗉ#HH# #☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#Z#HH#Z#☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  ( (☼\n" +
                "☼#ᗉ#HH# #☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertScores(
                "hero(0)=2\n" +
                "hero(1)=2");
       
        verifyAllEvents("");

        tick();

        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");
        
        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(6) => [HERO_DIE]\n");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  ( (☼\n" +
                "☼#Z#HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");
      
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼  ◄  ( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertScores(
                "hero(0)=3\n" +
                "hero(1)=2");

        dice(3, 2,
            6, 2,
            8, 2);

        tick();

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
                "☼  ѠHHZ Z☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

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
                "☼  ►HH( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertScores("");
       
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼  ►HH( (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);
    }

    @Test
    public void heroKillHeroAndKillEnemy() {
        settings().bool(ROUNDS_ENABLED, true)
                .integer(ROUNDS_TIME_BEFORE_START, 1)
                .integer(ROUNDS_PLAYERS_PER_ROOM, 4)
                .integer(KILL_HERO_SCORE, 1)
                .integer(KILL_ENEMY_SCORE, 10);

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

        givenPlayer(3, 2).inTeam(1);
        givenPlayer(6, 2).inTeam(2);

        givenPlayer(1, 2).inTeam(1);
        givenPlayer(8, 2).inTeam(1);

        tick();

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
                "☼( ►HH❪ (☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        crack(2, 3);

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼ ⊐◄HH❪⊐ ☼\n" +
                "☼#*#HH#*#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        goUp();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼  ◄HH❪  ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        tick();

        assertF("☼☼☼☼☼☼☼☼☼☼\n" +
                "☼        ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼  ◄HH❪  ☼\n" +
                "☼###HH###☼\n" +
                "☼☼☼☼HH☼☼☼☼\n" +
                "☼   HH   ☼\n" +
                "☼#ᗉ#HH#ᗉ#☼\n" +
                "☼☼☼☼☼☼☼☼☼☼\n", 0);

        goUp();

        assertScores("");
        
        verifyAllEvents("");

        tick();

        assertScores(
                "hero(0)=1\n" +
                "hero(1)=10");
        
        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [KILL_ENEMY]\n" +
                "listener(2) => [HERO_DIE]\n" +
                "listener(3) => [HERO_DIE]\n");
    }

    private void goUp() {
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
        // простреливают ямки
        hero(0).left();
        hero(0).act();
        hero(1).right();
        hero(1).act();
        // падают в ямки
        if (leftPrey != -1) {
            hero(leftPrey).right();
        }
        if (rightPrey != -1) {
            hero(rightPrey).left();
        }
        tick();
    }

    @Override
    public void tick() {
        removeAllDied();
        // эмуляция проверки загрузки комнаты, если комната недогружена то не тикаем
        // вообще это делает фреймворк, тут лишь эмулируем
        if (settings().isRoundsDisabled() ||
                settings().getPlayersPerRoom() == players.size())
        {
            super.tick();
        }
    }

    // TODO тут дублирование с mollymage, может продумать единую архитектуру
    //      тестов работающую и для rounds и реализовать во всех играх начиная
    //      с mollymage, clifford, snakebattle и battlecity
    private void removeAllDied() {
        players.forEach(player -> {
            if (!player.isAlive()) {
                field().remove(player);
            }
        });
    }
}
