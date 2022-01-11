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

import com.codenjoy.dojo.clifford.TestGameSettings;
import com.codenjoy.dojo.clifford.model.items.Brick;
import com.codenjoy.dojo.clifford.model.items.robber.RobberJoystick;
import com.codenjoy.dojo.clifford.services.Event;
import com.codenjoy.dojo.clifford.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.multiplayer.TriFunction;
import com.codenjoy.dojo.utils.gametest.AbstractBaseGameTest;
import org.junit.After;
import org.junit.Before;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.codenjoy.dojo.clifford.model.HandGun.UNLIM_CLIP_SIZE;
import static com.codenjoy.dojo.clifford.model.HandGun.SHOOT_WITHOUT_RECHARGE;
import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static java.util.stream.Collectors.toList;

public abstract class AbstractGameTest
        extends AbstractBaseGameTest<Player, Clifford, GameSettings, Level, Hero> {

    protected List<RobberJoystick> robbers;

    @Before
    public void setup() {
        super.setup();

        robbers = new LinkedList<>();
        Brick.CRACK_TIMER = 13;
    }

    @After
    public void after() {
        super.after();
    }

    @Override
    protected void beforeCreateField() {
        settings().integer(CLUE_COUNT_KNIFE, level().clueKnife().size())
                .integer(CLUE_COUNT_GLOVE, level().clueGlove().size())
                .integer(CLUE_COUNT_RING, level().clueRing().size())
                .integer(MASK_POTIONS_COUNT, level().potions().size())
                .integer(BACKWAYS_COUNT, level().backways().size())
                .integer(ROBBERS_COUNT, level().robbers().size())
                .integer(HANDGUN_CLIP_SIZE, UNLIM_CLIP_SIZE)
                .integer(HANDGUN_TICKS_PER_SHOOT, SHOOT_WITHOUT_RECHARGE);
    }

    @Override
    protected void afterCreateField() {
        reloadAllRobbers();
    }

    @Override
    protected GameSettings setupSettings() {
        return new TestGameSettings();
    }

    @Override
    protected Function<String, Level> createLevel() {
        return Level::new;
    }

    @Override
    protected BiFunction<EventListener, GameSettings, Player> createPlayer() {
        return Player::new;
    }

    @Override
    protected TriFunction<Dice, Level, GameSettings, Clifford> createField() {
        return Clifford::new;
    }

    @Override
    protected Class<?> eventClass() {
        return Event.class;
    }

    // other methods

    public RobberJoystick robber() {
        return robbers.get(0);
    }

    public RobberJoystick robber(int index) {
        return robbers.get(index);
    }

    protected void reloadAllRobbers() {
        robbers = field().robbers().stream()
                .map(RobberJoystick::new)
                .collect(toList());
    }

    protected void assertBullets(String expected) {
        assertEquals(expected, field().bullets().toString());
    }
}
