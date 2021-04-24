package com.codenjoy.dojo.loderunner.services;

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

import static com.codenjoy.dojo.loderunner.services.GameSettings.Keys.*;

public class Scores implements PlayerScores {

    private volatile int score;
    private GameSettings settings;
    private volatile int countRed;
    private volatile int countGreen;
    private volatile int countYellow;

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
        countRed = 0;
        countGreen = 0;
        countYellow = 0;
    }

    @Override
    public Integer getScore() {
        return score;
    }

    @Override
    public void event(Object event) {
        if (event.equals(Events.GET_YELLOW_GOLD)) {
            score += settings.integer(GOLD_SCORE_YELLOW) + countYellow;
            countYellow += settings.integer(GOLD_SCORE_YELLOW_INCREMENT);
        } else if (event.equals(Events.GET_GREEN_GOLD)) {
            score += settings.integer(GOLD_SCORE_GREEN) + countGreen;
            countGreen += settings.integer(GOLD_SCORE_GREEN_INCREMENT);
        } else if (event.equals(Events.GET_RED_GOLD)) {
            score += settings.integer(GOLD_SCORE_RED) + countRed;
            countRed += settings.integer(GOLD_SCORE_RED_INCREMENT);
        } else if (event.equals(Events.KILL_ENEMY)) {
            score += settings.integer(KILL_ENEMY_SCORE);
        } else if (event.equals(Events.KILL_HERO)) {
            clearSeries();
            score -= settings.integer(KILL_HERO_PENALTY);
        } else if (event.equals(Events.SUICIDE)) {
            clearSeries();
            score -= settings.integer(SUICIDE_PENALTY);
        }
        score = Math.max(0, score);
    }

    @Override
    public void update(Object score) {
        this.score = Integer.valueOf(score.toString());
    }

    @Override
    public String toString() {
        return "Scores{" +
                "score=" + score +
                ", red=" + countRed +
                ", green=" + countGreen +
                ", yellow=" + countYellow +
                '}';
    }
}