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


import com.codenjoy.dojo.clifford.model.items.robber.RobberJoystick;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.ROBBERS_COUNT;
import static com.codenjoy.dojo.services.PointImpl.pt;

public class RobberMultiplayerTest extends AbstractGameTest {

    @Override
    public void givenFl(String... maps) {
        super.givenFl(maps);

        robbers.forEach(RobberJoystick::disableMock);
    }

    // чертик идет за тобой
    @Test
    public void shouldRobberGoToHero() {
        // given
        settings().integer(ROBBERS_COUNT, 1);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼     (☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ) ☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();
        tick();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼)     ☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼X     ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼H  (  ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼► )H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();
        tick();

        // then
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

        // when
        dice(1, 4);
        game().newGame();

        tick();

        // then
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
        // given
        settings().integer(ROBBERS_COUNT, 1);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼     ►☼\n" +
                "☼     #☼\n" +
                "☼      ☼\n" +
                "☼###H  ☼\n" +
                "☼(  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼     ►☼\n" +
                "☼     #☼\n" +
                "☼      ☼\n" +
                "☼###H  ☼\n" +
                "☼(  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();
        tick();
        tick();
        tick();

        // then
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
        // given
        settings().integer(ROBBERS_COUNT, 1);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼     (☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
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
        // given
        settings().integer(ROBBERS_COUNT, 1);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼(     ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
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
        // given
        settings().integer(ROBBERS_COUNT, 2);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼(    (☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
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
        // given
        settings().integer(ROBBERS_COUNT, 2);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼( (   ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼ )    ☼\n" +
                "☼X####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

    }

    // если чертику не достать одного он бежит за другим, а не зависает
    @Test
    public void shouldRobberGoToNewHeroIfOneIsHidden() {
        // given
        settings().integer(ROBBERS_COUNT, 1);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼   ►  ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼###H##☼\n" +
                "☼(  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();
        tick();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ►  ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼###H##☼\n" +
                "☼(  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        givenPlayer(pt(1, 4));
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   »  ☼\n" +
                "☼######☼\n" +
                "☼►     ☼\n" +
                "☼###H##☼\n" +
                "☼ ( H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        // then
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
        // given
        settings().integer(ROBBERS_COUNT, 2);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼(  ► (☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼ ( »  ☼\n" +
                "☼H####X☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼  (»  ☼\n" +
                "☼H####H☼\n" +
                "☼H    X☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
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

        // when
        tick();

        // then
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   C( ☼\n" +
                "☼H####H☼\n" +
                "☼H  ) H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   C (☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###X##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   C  ☼\n" +
                "☼H####X☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►X  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
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

        // when
        // больше не за кем охотиться - воры стоят на месте
        tick();

        // then
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   C  ☼\n" +
                "☼H####H☼\n" +
                "☼H    X☼\n" +
                "☼###H##☼\n" +
                "☼  OH  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   C  ☼\n" +
                "☼H####H☼\n" +
                "☼H    X☼\n" +
                "☼###H##☼\n" +
                "☼  OH  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        // даже если на поле никого нет, чертики стоят на месте
        remove(1);
        remove(0);

        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H####H☼\n" +
                "☼H    X☼\n" +
                "☼###H##☼\n" +
                "☼  )H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        // when
        // но стоит двоим ребятам появиться на поле
        // как вдруг воры начнут охотиться каждый за своим
        givenPlayer(pt(1, 2));
        givenPlayer(pt(5, 6));

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ► ☼\n" +
                "☼H####H☼\n" +
                "☼H    X☼\n" +
                "☼###H##☼\n" +
                "☼» )H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 3);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ► ☼\n" +
                "☼H####X☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼») H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 3);

        // when
        // если один вдруг пропадет, то его воры переключится
        remove(0);

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ► ☼\n" +
                "☼H####X☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼ ) H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 3);

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ►(☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  (H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 3);

        // when
        // и после того как нагонят оставшегося, снова зависнут
        tick();

        // then
        verifyAllEvents("[HERO_DIED]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    O ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  (H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 3);

        // when
        tick();

        // then
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