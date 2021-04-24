package com.codenjoy.dojo.loderunner.model;

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


import com.codenjoy.dojo.loderunner.TestSettings;
import com.codenjoy.dojo.loderunner.services.Events;
import com.codenjoy.dojo.loderunner.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.Game;
import com.codenjoy.dojo.services.multiplayer.Single;
import com.codenjoy.dojo.services.printer.PrinterFactory;
import com.codenjoy.dojo.services.printer.PrinterFactoryImpl;
import com.codenjoy.dojo.utils.events.EventsListenersAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Arrays;

import static com.codenjoy.dojo.loderunner.services.GameSettings.Keys.ENEMIES_COUNT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class EnemyMultiplayerTest {

    private Dice dice;
    private EventListener listener;
    private Game game;
    private Loderunner field;
    private PrinterFactory printerFactory;
    private GameSettings settings;
    private EventsListenersAssert events;

    @Before
    public void setUp() {
        dice = mock(Dice.class);
        printerFactory = new PrinterFactoryImpl();
        settings = new TestSettings();
        events = new EventsListenersAssert(() -> Arrays.asList(listener), Events.class);
    }

    @After
    public void tearDown() {
        events.verifyNoEvents();
    }

    // чертик идет за тобой
    @Test
    public void shouldEnemyGoToHero() {
        settings.integer(ENEMIES_COUNT, 1);
        setupGm("☼☼☼☼☼☼☼☼" +
                "☼     »☼" +
                "☼H#####☼" +
                "☼H     ☼" +
                "☼###H  ☼" +
                "☼►  H  ☼" +
                "☼######☼" +
                "☼☼☼☼☼☼☼☼");

        dice(0); // охотимся за первым игроком
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼    « ☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        field.tick();
        field.tick();
        field.tick();
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼«     ☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        field.tick();
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼Q     ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        field.tick();
        field.tick();
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼H  »  ☼\n" +
                "☼###H  ☼\n" +
                "☼►  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        field.tick();
        field.tick();
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼► «H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        field.tick();
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼H     ☼\n" +
                "☼###H  ☼\n" +
                "☼Ѡ  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        events.verifyAllEvents("KILL_HERO");
        assertTrue(game.isGameOver());

        dice(1, 4);
        game.newGame();

        dice(0); // охотимся за первым игроком
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H#####☼\n" +
                "☼H  ►  ☼\n" +
                "☼###H  ☼\n" +
                "☼ » H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // чертик стоит на месте, если ко мне нет пути
    @Test
    public void shouldEnemyStop_whenNoPathToHero() {
        settings.integer(ENEMIES_COUNT, 1);
        setupGm("☼☼☼☼☼☼☼☼" +
                "☼     ►☼" +
                "☼     #☼" +
                "☼      ☼" +
                "☼###H  ☼" +
                "☼»  H  ☼" +
                "☼######☼" +
                "☼☼☼☼☼☼☼☼");

        dice(0); // охотимся за первым игроком
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼     ►☼\n" +
                "☼     #☼\n" +
                "☼      ☼\n" +
                "☼###H  ☼\n" +
                "☼»  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        field.tick();
        field.tick();
        field.tick();
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼     ►☼\n" +
                "☼     #☼\n" +
                "☼      ☼\n" +
                "☼###H  ☼\n" +
                "☼»  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // чертик идет за тобой по более короткому маршруту
    @Test
    public void shouldEnemyGoToHeroShortestWay() {
        settings.integer(ENEMIES_COUNT, 1);
        setupGm("☼☼☼☼☼☼☼☼" +
                "☼     »☼" +
                "☼H####H☼" +
                "☼H    H☼" +
                "☼###H##☼" +
                "☼  ►H  ☼" +
                "☼######☼" +
                "☼☼☼☼☼☼☼☼");

        dice(0); // охотимся за первым игроком
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼H####Q☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

    }

    @Test
    public void shouldEnemyGoToHeroShortestWay2() {
        settings.integer(ENEMIES_COUNT, 1);
        setupGm("☼☼☼☼☼☼☼☼" +
                "☼»     ☼" +
                "☼H####H☼" +
                "☼H    H☼" +
                "☼###H##☼" +
                "☼  ►H  ☼" +
                "☼######☼" +
                "☼☼☼☼☼☼☼☼");

        dice(0); // охотимся за первым игроком
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼Q####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

    }

    // другой чертик чертику не помеха
    @Test
    public void shouldEnemyGoToHeroShortestWayGetRoundOther() {
        settings.integer(ENEMIES_COUNT, 2);
        setupGm("☼☼☼☼☼☼☼☼" +
                "☼»    »☼" +
                "☼H####H☼" +
                "☼H    H☼" +
                "☼###H##☼" +
                "☼  ►H  ☼" +
                "☼######☼" +
                "☼☼☼☼☼☼☼☼");

        dice(0); // охотимся за первым игроком
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼Q####Q☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

    }

    private void dice(int... ints) {
        OngoingStubbing<Integer> when = when(dice.next(anyInt()));
        for (int i : ints) {
            when = when.thenReturn(i);
        }
    }

    @Test
    public void shouldEnemyGoToHeroShortestWayGetRoundOther2() {
        settings.integer(ENEMIES_COUNT, 2);
        setupGm("☼☼☼☼☼☼☼☼" +
                "☼» »   ☼" +
                "☼H####H☼" +
                "☼H    H☼" +
                "☼###H##☼" +
                "☼  ►H  ☼" +
                "☼######☼" +
                "☼☼☼☼☼☼☼☼");

        dice(0); // охотимся за первым игроком
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼ «    ☼\n" +
                "☼Q####H☼\n" +
                "☼H    H☼\n" +
                "☼###H##☼\n" +
                "☼  ►H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

    }

    // если чертику не достать одного он бежит за другим а не зависает
    @Test
    public void shouldEnemyGoToNewHeroIfOneIsHidden() {
        settings.integer(ENEMIES_COUNT, 1);
        setupGm("☼☼☼☼☼☼☼☼" +
                "☼   ►  ☼" +
                "☼######☼" +
                "☼      ☼" +
                "☼###H##☼" +
                "☼»  H  ☼" +
                "☼######☼" +
                "☼☼☼☼☼☼☼☼");

        dice(0); // охотимся за первым игроком
        field.tick();
        field.tick();
        field.tick();
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼   ►  ☼\n" +
                "☼######☼\n" +
                "☼      ☼\n" +
                "☼###H##☼\n" +
                "☼»  H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        setupPlayer(1, 4);
        dice(0); // охотимся за первым игроком
        field.tick();
        field.tick();
        field.tick();
        field.tick();
        field.tick();
        field.tick();
        field.tick();
        field.tick();
        field.tick();

        atBoard("☼☼☼☼☼☼☼☼\n" +
                "☼   (  ☼\n" +
                "☼######☼\n" +
                "☼Ѡ     ☼\n" +
                "☼###H##☼\n" +
                "☼   H  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }


    private void atBoard(String expected) {
        assertEquals(expected, game.getBoardAsString());
    }

    private void setupPlayer(int x, int y) {
        listener = mock(EventListener.class);
        game = new Single(new Player(listener, settings), printerFactory);
        game.on(field);
        dice(x, y);
        game.newGame();
    }

    private void setupGm(String map) {
        Level level = new LevelImpl(map, dice);
        field = new Loderunner(level, dice, settings);

        int px = level.getHeroes().get(0).getX();
        int py = level.getHeroes().get(0).getY();
        setupPlayer(px, py);
    }
}
