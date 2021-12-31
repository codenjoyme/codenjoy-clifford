package com.codenjoy.dojo.clifford.model;

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

import com.codenjoy.dojo.clifford.TestGameSettings;
import com.codenjoy.dojo.clifford.model.items.Brick;
import com.codenjoy.dojo.clifford.model.items.robber.RobberJoystick;
import com.codenjoy.dojo.clifford.services.Event;
import com.codenjoy.dojo.clifford.services.GameSettings;
import com.codenjoy.dojo.games.clifford.Element;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.Game;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.multiplayer.LevelProgress;
import com.codenjoy.dojo.services.printer.PrinterFactory;
import com.codenjoy.dojo.services.printer.PrinterFactoryImpl;
import com.codenjoy.dojo.utils.events.EventsListenersAssert;
import com.codenjoy.dojo.utils.smart.SmartAssert;
import com.codenjoy.dojo.whatsnext.WhatsNextUtils;
import org.junit.After;
import org.junit.Before;
import org.mockito.stubbing.OngoingStubbing;

import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.utils.TestUtils.asArray;
import static com.codenjoy.dojo.utils.TestUtils.collectHeroesData;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractGameTest {

    private List<EventListener> listeners;
    protected List<Player> players;
    private List<Game> games;
    private Dice dice;
    private PrinterFactory<Element, Player> printer;
    private Clifford field;
    private GameSettings settings;
    private EventsListenersAssert events;
    protected List<RobberJoystick> robbers;
    private Level level;

    @Before
    public void setup() {
        listeners = new LinkedList<>();
        players = new LinkedList<>();
        games = new LinkedList<>();
        dice = mock(Dice.class);
        printer = new PrinterFactoryImpl<>();
        settings = new TestGameSettings();
        setupSettings();
        events = new EventsListenersAssert(() -> listeners, Event.class);
        robbers = new LinkedList<>();
        Brick.CRACK_TIMER = 13;
    }

    @After
    public void after() {
        verifyAllEvents("");
        SmartAssert.checkResult();
    }

    protected void dice(int... ints) {
        if (ints.length == 0) return;
        OngoingStubbing<Integer> when = when(dice.next(anyInt()));
        for (int i : ints) {
            when = when.thenReturn(i);
        }
    }

    public void givenFl(String... maps) {
        int levelNumber = LevelProgress.levelsStartsFrom1;
        settings.setLevelMaps(levelNumber, maps);
        level = settings.level(levelNumber, dice, Level::new);

        beforeCreateField();

        field = new Clifford(dice, null, settings);
        field.load(level.map(), this::givenPlayer);

        setupHeroesDice();

        games = WhatsNextUtils.newGameForAll(players, printer, field);

        afterCreateField();
    }

    private void setupHeroesDice() {
        dice(asArray(level.heroes()));
    }

    private void beforeCreateField() {
        settings.integer(CLUE_COUNT_KNIFE, level.clueKnife().size())
                .integer(CLUE_COUNT_GLOVE, level.clueGlove().size())
                .integer(CLUE_COUNT_RING, level.clueRing().size())
                .integer(MASK_POTIONS_COUNT, level.potions().size())
                .integer(BACKWAYS_COUNT, level.backways().size())
                .integer(ROBBERS_COUNT, level.robbers().size());
    }

    private void afterCreateField() {
        reloadAllRobbers();
        level.heroes().forEach(hero ->
                field.heroes().getAt(hero).get(0)
                        .setDirection(hero.getDirection()));
        dice(0); // всегда дальше выбираем нулевой индекс
    }

    protected Player givenPlayer() {
        EventListener listener = mock(EventListener.class);
        listeners.add(listener);

        Player player = new Player(listener, settings);
        players.add(player);
        return player;
    }

    public Player givenPlayer(Point pt) {
        Player player = givenPlayer();

        dice(asArray(asList(pt)));
        Game game = WhatsNextUtils.newGame(player, printer, field);
        games.add(game);

        return players.get(players.size() - 1);
    }

    protected void setupSettings() {
        // do something with settings
    }

    public void tick() {
        field.tick();
    }

    public void assertF(String expected) {
        assertF(expected, 0);
    }

    /**
     * Проверяет одну борду с заданным индексом
     *
     * @param expected ожидаемое значение
     * @param index    индекс
     */
    public void assertF(String expected, int index) {
        assertEquals(expected, game(index).getBoardAsString());
    }

    public void verifyAllEvents(String expected) {
        assertEquals(expected, events().getEvents());
    }

    public void assertScores(String expected) {
        assertEquals(expected,
                collectHeroesData(players, "scores", true));
    }

    public void assertEquals(String message, Object expected, Object actual) {
        SmartAssert.assertEquals(message, expected, actual);
    }

    public void assertEquals(Object expected, Object actual) {
        SmartAssert.assertEquals(expected, actual);
    }

    public GameSettings settings() {
        return settings;
    }

    public Clifford field() {
        return field;
    }

    public EventsListenersAssert events() {
        return events;
    }

    public Game game() {
        return games.get(0);
    }

    public Game game(int index) {
        return games.get(index);
    }

    public EventListener listener() {
        return listeners.get(0);
    }

    public EventListener listener(int index) {
        return listeners.get(index);
    }

    public Hero hero() {
        return hero(0);
    }

    public Hero hero(int index) {
        return (Hero) game(index).getPlayer().getHero();
    }

    public Player player() {
        return player(0);
    }

    public Player player(int index) {
        return players.get(index);
    }

    public void remove(int index) {
        field.remove(players.get(index));
        players.remove(index);
        listeners.remove(index);
    }

    public RobberJoystick robber() {
        return robbers.get(0);
    }

    public RobberJoystick robber(int index) {
        return robbers.get(index);
    }

    protected void reloadAllRobbers() {
        robbers = field.robbers().stream()
                .map(RobberJoystick::new)
                .collect(toList());
    }

    protected void assertBullets(String expected) {
        assertEquals(expected, field().bullets().toString());
    }
}
