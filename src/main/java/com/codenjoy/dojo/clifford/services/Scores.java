package com.codenjoy.dojo.clifford.services;

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


import com.codenjoy.dojo.services.PlayerScores;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;

public class Scores implements PlayerScores {

    private volatile int score;
    private volatile int rings;
    private volatile int gloves;
    private volatile int knives;
    private GameSettings settings;

    public Scores(int startScore, GameSettings settings) {
        this.score = startScore;
        this.settings = settings;
    }

    @Override
    public int clear() {
        clearSeries();
        return score = 0;
    }

    private void clearSeries() {
        rings = 0;
        gloves = 0;
        knives = 0;
    }

    @Override
    public Integer getScore() {
        return score;
    }

    @Override
    public void event(Object event) {
        score += scoreFor(settings, event);
        score = Math.max(0, score);

        process(event);
    }

    public int scoreFor(GameSettings settings, Object event) {
        if (event.equals(Events.GET_CLUE_KNIFE)) {
            return settings.integer(CLUE_SCORE_KNIFE) + knives;
        }

        if (event.equals(Events.GET_CLUE_GLOVE)) {
            return settings.integer(CLUE_SCORE_GLOVE) + gloves;
        }

        if (event.equals(Events.GET_CLUE_RING)) {
            return settings.integer(CLUE_SCORE_RING) + rings;
        }

        if (event.equals(Events.KILL_HERO)) {
            return settings.integer(KILL_HERO_SCORE);
        }

        if (event.equals(Events.KILL_ENEMY)) {
            return settings.integer(KILL_ENEMY_SCORE);
        }

        if (event.equals(Events.HERO_DIE)) {
            return - settings.integer(HERO_DIE_PENALTY);
        }

        if (event.equals(Events.SUICIDE)) {
            return - settings.integer(SUICIDE_PENALTY);
        }

        return 0;
    }

    public void process(Object event) {
        if (event.equals(Events.GET_CLUE_KNIFE)) {
            knives += settings.integer(CLUE_SCORE_KNIFE_INCREMENT);
            return;
        }

        if (event.equals(Events.GET_CLUE_GLOVE)) {
            gloves += settings.integer(CLUE_SCORE_GLOVE_INCREMENT);
            return;
        }

        if (event.equals(Events.GET_CLUE_RING)) {
            rings += settings.integer(CLUE_SCORE_RING_INCREMENT);
            return;
        }

        if (event.equals(Events.HERO_DIE)
                || event.equals(Events.SUICIDE))
        {
            clearSeries();
            return;
        }
    }

    @Override
    public void update(Object score) {
        this.score = Integer.valueOf(score.toString());
    }

    @Override
    public String toString() {
        return "Scores{" +
                "score=" + score +
                ", ring=" + rings +
                ", glove=" + gloves +
                ", knife=" + knives +
                '}';
    }
}