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

    // вор идет за тобой
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
        tick(); // new game

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

    // вор стоит на месте, если ко мне нет пути
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

    // вор идет за тобой по более короткому маршруту
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

    // другой вор вору не помеха
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

    // если вору не достать одного он бежит за другим, а не зависает
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

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   »  ☼\n" +
                "☼######☼\n" +
                "☼►)    ☼\n" +
                "☼###H##☼\n" +
                "☼   H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");

        // when
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

        // when
        dice(1, 2); // new hero position
        tick(); // new game

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   »  ☼\n" +
                "☼######☼\n" +
                "☼ (    ☼\n" +
                "☼###H##☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n", 1);

        verifyAllEvents("");
    }

    // каждый вор бежит за своим героем, даже если к нему занятый уже герой ближе
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
                " ###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼ ( ►  ☼\n" +
                "☼H####X☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  »H  ☼\n" +
                " ###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼  (►  ☼\n" +
                "☼H####H☼\n" +
                "☼H    X☼\n" +
                "☼###H##☼\n" +
                "☼  »H  ☼\n" +
                " ###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        verifyAllEvents(
                "listener(0) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   O  ☼\n" +
                "☼H####H☼\n" +
                "☼H   )H☼\n" +
                "☼###H##☼\n" +
                "☼  »H  ☼\n" +
                " ###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        dice(0, 1); // new hero position
        tick(); // new game

        // then
        // второй вор стоит на месте, его герой спрятан
        // TODO хотя должен переключиться на другого героя
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   (  ☼\n" +
                "☼H####H☼\n" +
                "☼H  ) H☼\n" +
                "☼###H##☼\n" +
                "☼  »H  ☼\n" +
                "►###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   (  ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###X##☼\n" +
                "☼  »H  ☼\n" +
                "►###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   (  ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  »X  ☼\n" +
                "►###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        verifyAllEvents(
                "listener(1) => [HERO_DIED]\n");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   (  ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  CH  ☼\n" +
                "►###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        dice(7, 1); // new hero position
        tick(); // new game

        // then
        // оба вора стоят на месте, им не за кем охотиться
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   (  ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  )H  ☼\n" +
                "►######»\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   (  ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  )H  ☼\n" +
                "►######»\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // но стоит двоим героям появиться на поле в доступности
        // как вдруг воры начнут охотиться каждый за своим
        hero(0).move(pt(1, 6));
        hero(1).move(pt(6, 2));

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼►  (  ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  )H »☼\n" +
                " ###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼► )   ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼   X »☼\n" +
                " ###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // если один вдруг пропадет, то его вор переключится
        // и за оставшимся героем будут гнаться уже оба
        remove(0);

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼  )   ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼   X ►☼\n" +
                " ###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   (  ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼   H(►☼\n" +
                " ###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // и после того как нагонят оставшегося, снова зависнут
        tick();

        // then
        verifyAllEvents("[HERO_DIED]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ( ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼   H O☼\n" +
                " ###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        dice(0, 1); // new hero position
        tick(); // new game

        // then
        verifyAllEvents("");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ( ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼   H (☼\n" +
                "►###### \n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼    ( ☼\n" +
                "☼H####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼   H (☼\n" +
                "►###### \n" +
                "☼☼☼☼☼☼☼☼\n");
    }
}