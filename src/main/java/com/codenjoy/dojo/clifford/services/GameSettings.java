package com.codenjoy.dojo.clifford.services;

/*-
 * #%L
 * expansion - it's a dojo-like platform from developers to developers.
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


import com.codenjoy.dojo.services.event.Calculator;
import com.codenjoy.dojo.services.settings.AllSettings;
import com.codenjoy.dojo.services.settings.PropertiesKey;
import com.codenjoy.dojo.services.settings.SettingsImpl;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;

public class GameSettings extends SettingsImpl implements AllSettings<GameSettings> {

    public enum Keys implements PropertiesKey {

        MASK_POTIONS_COUNT,
        MASK_TICKS,
        BACKWAYS_COUNT,
        BACKWAY_TICKS,
        ROBBERS_COUNT,
        GENERATE_KEYS,
        CLUE_COUNT_GLOVE,
        CLUE_SCORE_GLOVE,
        CLUE_SCORE_GLOVE_INCREMENT,
        CLUE_COUNT_KNIFE,
        CLUE_SCORE_KNIFE,
        CLUE_SCORE_KNIFE_INCREMENT,
        CLUE_COUNT_RING,
        CLUE_SCORE_RING,
        CLUE_SCORE_RING_INCREMENT,
        HANDGUN_TICKS_PER_SHOOT,
        HANDGUN_CLIP_SIZE,
        HANDGUN_UNLIMITED_AMMO,
        ROUND_WIN_SCORE,
        KILL_OTHER_HERO_SCORE,
        KILL_ENEMY_HERO_SCORE,
        HERO_DIED_PENALTY,
        SUICIDE_PENALTY,
        SCORE_COUNTING_TYPE;

        private String key;

        Keys() {
            this.key = key(GameRunner.GAME_NAME);
        }

        @Override
        public String key() {
            return key;
        }
    }

    @Override
    public List<Key> allKeys() {
        return Arrays.asList(Keys.values());
    }

    public GameSettings() {
        initAll();

        integer(MASK_POTIONS_COUNT, 0);
        integer(MASK_TICKS, 15);

        integer(BACKWAYS_COUNT, 5);
        integer(BACKWAY_TICKS, 50);

        integer(ROBBERS_COUNT, 3);

        bool(GENERATE_KEYS, false);

        integer(CLUE_COUNT_GLOVE, 20);
        integer(CLUE_SCORE_GLOVE, 1);
        integer(CLUE_SCORE_GLOVE_INCREMENT, 1);

        integer(CLUE_COUNT_KNIFE, 10);
        integer(CLUE_SCORE_KNIFE, 2);
        integer(CLUE_SCORE_KNIFE_INCREMENT, 1);

        integer(CLUE_COUNT_RING, 5);
        integer(CLUE_SCORE_RING, 5);
        integer(CLUE_SCORE_RING_INCREMENT, 1);

        integer(HANDGUN_CLIP_SIZE, 12);
        integer(HANDGUN_TICKS_PER_SHOOT, 0);
        bool(HANDGUN_UNLIMITED_AMMO, true);

        integer(ROUND_WIN_SCORE, 20);
        integer(KILL_OTHER_HERO_SCORE, 20);
        integer(KILL_ENEMY_HERO_SCORE, 50);
        integer(HERO_DIED_PENALTY, -1);
        integer(SUICIDE_PENALTY, -10);

        Levels.setup(this);
    }

    public Calculator<Integer> calculator() {
        return new Calculator<>(new Scores(this));
    }
}
