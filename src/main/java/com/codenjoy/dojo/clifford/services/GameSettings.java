package com.codenjoy.dojo.clifford.services;

/*-
 * #%L
 * expansion - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 - 2020 Codenjoy
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


import com.codenjoy.dojo.clifford.model.levels.Level;
import com.codenjoy.dojo.clifford.services.levels.Big;
import com.codenjoy.dojo.services.round.RoundSettings;
import com.codenjoy.dojo.services.semifinal.SemifinalSettings;
import com.codenjoy.dojo.services.settings.SettingsImpl;
import com.codenjoy.dojo.services.settings.SettingsReader;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;

public class GameSettings extends SettingsImpl
        implements SettingsReader<GameSettings>,
                    RoundSettings<GameSettings>,
                    SemifinalSettings<GameSettings> {

    public static final String MAP_PATH_NONE = "none";

    public enum Keys implements Key {

        MASK_POTIONS_COUNT("[Game] Mask potions count"),
        MASK_TICKS("[Game] Mask ticks"),

        BACKWAYS_COUNT("[Game] Backways count"),
        BACKWAY_TICKS("[Game] Backway ticks"),

        ROBBERS_COUNT("[Game] Robbers count"),

        GENERATE_KEYS("[Game] Generate picked keys"),

        CLUE_COUNT_GLOVE("[Game] Glove clue count"),
        CLUE_SCORE_GLOVE("[Score] Glove clue score"),
        CLUE_SCORE_GLOVE_INCREMENT("[Score] Glove clue score increment"),

        CLUE_COUNT_KNIFE("[Game] Knife clue count"),
        CLUE_SCORE_KNIFE("[Score] Knife clue score"),
        CLUE_SCORE_KNIFE_INCREMENT("[Score] Knife clue score increment"),

        CLUE_COUNT_RING("[Game] Ring clue count"),
        CLUE_SCORE_RING("[Score] Ring clue score"),
        CLUE_SCORE_RING_INCREMENT("[Score] Ring clue score increment"),

        KILL_HERO_SCORE("[Score] Kill hero score"),
        KILL_ENEMY_SCORE("[Score] Kill enemy score"),
        HERO_DIE_PENALTY("[Score] Hero die penalty"),
        SUICIDE_PENALTY("[Score] Suicide penalty"),

        LEVEL_MAP("[Level] Map");

        private String key;

        Keys(String key) {
            this.key = key;
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
        initRound();
        initSemifinal();

        integer(MASK_POTIONS_COUNT, 0);
        integer(MASK_TICKS, 15);

        integer(BACKWAYS_COUNT, 0);
        integer(BACKWAY_TICKS, 10);

        integer(ROBBERS_COUNT, 5);

        bool(GENERATE_KEYS, false);

        integer(CLUE_COUNT_GLOVE, 40);
        integer(CLUE_SCORE_GLOVE, 1);
        integer(CLUE_SCORE_GLOVE_INCREMENT, 1);

        integer(CLUE_COUNT_KNIFE, 20);
        integer(CLUE_SCORE_KNIFE, 2);
        integer(CLUE_SCORE_KNIFE_INCREMENT, 1);

        integer(CLUE_COUNT_RING, 10);
        integer(CLUE_SCORE_RING, 5);
        integer(CLUE_SCORE_RING_INCREMENT, 1);

        integer(KILL_HERO_SCORE, 20);
        integer(KILL_ENEMY_SCORE, 50);
        integer(HERO_DIE_PENALTY, 1);
        integer(SUICIDE_PENALTY, 10);

        multiline(LEVEL_MAP, Big.all().get(0));
    }

    public Level level() {
        return new Level(string(LEVEL_MAP));
    }

}
