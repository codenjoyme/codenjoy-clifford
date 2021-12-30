package com.codenjoy.dojo.clifford.model;

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


import com.codenjoy.dojo.clifford.model.items.robber.RobberJoystick;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.ROBBERS_COUNT;

public class RobberMultiplayerTest extends AbstractGameTest {

    @Override
    public void givenFl(String... maps) {
        super.givenFl(maps);

        robbers.forEach(RobberJoystick::disableMock);
    }

    // чертик идет за тобой
    @Test
    public void shouldRobberGoToHero() {
        settings().integer(ROBBERS_COUNT, 1);
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼     (☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ) ☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼)     ☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼X     ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼H  (  ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼► )H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼O  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIED]");
        assertEquals(true, game().isGameOver());

        dice(1, 4);
        game().newGame();

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼H  ►  ☼\n" +
                "☼###H  ☼\n" +
                "☼ ( H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // чертик стоит на месте, если ко мне нет пути
    @Test
    public void shouldRobberStop_whenNoPathToHero() {
        settings().integer(ROBBERS_COUNT, 1);
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼     ►☼\n" +
                "☼     #☼\n" +
                "☼      ☼\n" +
                "☼###H  ☼\n" +
                "☼(  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼     ►☼\n" +
                "☼     #☼\n" +
                "☼      ☼\n" +
                "☼###H  ☼\n" +
                "☼(  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼     ►☼\n" +
                "☼     #☼\n" +
                "☼      ☼\n" +
                "☼###H  ☼\n" +
                "☼(  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // чертик идет за тобой по более короткому маршруту
    @Test
    public void shouldRobberGoToHeroShortestWay() {
        settings().integer(ROBBERS_COUNT, 1);
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼     (☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H####X☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

    }

    @Test
    public void shouldRobberGoToHeroShortestWay2() {
        settings().integer(ROBBERS_COUNT, 1);
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼(     ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼X####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

    }

    // другой чертик чертику не помеха
    @Test
    public void shouldRobberGoToHeroShortestWayGetRoundOther() {
        settings().integer(ROBBERS_COUNT, 2);
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼(    (☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼X####X☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

    }

    @Test
    public void shouldRobberGoToHeroShortestWayGetRoundOther2() {
        settings().integer(ROBBERS_COUNT, 2);
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼( (   ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼ )    ☼\n" +
                "☼X####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

    }

    // если чертику не достать одного он бежит за другим а не зависает
    @Test
    public void shouldRobberGoToNewHeroIfOneIsHidden() {
        settings().integer(ROBBERS_COUNT, 1);
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼   ►  ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼###H##☼\n" +
                "☼(  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ►  ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼###H##☼\n" +
                "☼(  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        givenPlayer(1, 4);
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   »  ☼\n" +
                "☼######☼\n" +
                "☼►     ☼\n" +
                "☼###H##☼\n" +
                "☼ ( H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   »  ☼\n" +
                "☼######☼\n" +
                "☼O     ☼\n" +
                "☼###H##☼\n" +
                "☼   H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents(
                "listener(1) => [HERO_DIED]\n");
    }

    // каждый чертик бежит за своим героем, даже если к нему занятый уже герой ближе
    @Test
    public void shouldEveryRobberRunsAfterHisHero_evenIfThereIsAnotherHeroNearbyWhoIsAlreadyBeingHunted() {
        settings().integer(ROBBERS_COUNT, 2);
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼(  ► (☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼ ( »  ☼\n" +
                "☼H####X☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼  (»  ☼\n" +
                "☼H####H☼\n" +
                "☼H    X☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        verifyAllEvents(
                "listener(0) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   C  ☼\n" +
                "☼H####H☼\n" +
                "☼H   )H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   C( ☼\n" +
                "☼H####H☼\n" +
                "☼H  ) H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   C (☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###X##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   C  ☼\n" +
                "☼H####X☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►X  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        verifyAllEvents(
                "listener(1) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   C  ☼\n" +
                "☼H####H☼\n" +
                "☼H    X☼\n" +
                "☼###H##☼\n" +
                "☼  OH  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // больше не за кем охотитья - воры стоят на месте
        tick();

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   C  ☼\n" +
                "☼H####H☼\n" +
                "☼H    X☼\n" +
                "☼###H##☼\n" +
                "☼  OH  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   C  ☼\n" +
                "☼H####H☼\n" +
                "☼H    X☼\n" +
                "☼###H##☼\n" +
                "☼  OH  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // даже если на поле никого нет, чертики стоят на месте
        remove(1);
        remove(0);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H####H☼\n" +
                "☼H    X☼\n" +
                "☼###H##☼\n" +
                "☼  )H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // но стоит двоим ребятам появиться на поле
        // как вдруг воры начнут охотиться каждый за своим
        givenPlayer(1, 2);
        givenPlayer(5, 6);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ► ☼\n" +
                "☼H####H☼\n" +
                "☼H    X☼\n" +
                "☼###H##☼\n" +
                "☼» )H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 3);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ► ☼\n" +
                "☼H####X☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼») H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 3);

        // если один вдруг пропадет, то его воры переключится
        remove(0);

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ► ☼\n" +
                "☼H####X☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼ ) H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 3);

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ►(☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  (H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 3);

        // и после того как нагонят оставшегося, снова зависнут
        tick();

        verifyAllEvents("[HERO_DIED]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    O ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  (H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 3);

        tick();

        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    O ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  (H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 3);
    }
}
