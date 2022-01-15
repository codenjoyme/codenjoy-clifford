package com.codenjoy.dojo.clifford.services.ai;

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


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.games.clifford.Board;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.algs.DeikstraFindWay;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.games.clifford.Element.clues;
import static com.codenjoy.dojo.services.Direction.DOWN;
import static com.codenjoy.dojo.services.Direction.UP;

public class AISolver implements Solver<Board> {

    private DeikstraFindWay way;

    public AISolver(Dice dice) {
        this.way = new DeikstraFindWay();
    }

    public DeikstraFindWay.Possible possible(Board board) {
        return new DeikstraFindWay.Possible() {
            @Override
            public boolean possible(Point from, Direction where) {
                if (where == UP && !board.isLadderAt(from)) return false;

                Point under = DOWN.change(from);
                if (where != DOWN
                        && !under.isOutOf(board.size())
                        && !board.isWallAt(under)
                        && !board.isLadderAt(under)
                        && !board.isLadderAt(from)
                        && !board.isPipeAt(from)) return false;

                return true;
            }

            @Override
            public boolean possible(Point pt) {
                if (pt.isOutOf(board.size())) return false;
                if (board.isWallAt(pt)) return false;
                if (board.isRobberAt(pt)) return false;
                if (board.isOtherHeroAt(pt)) return false;
                if (board.isEnemyHeroAt(pt)) return false;
                return true;
            }
        };
    }

    @Override
    public String get(Board board) {
        if (board.isGameOver()) return "";
        List<Direction> result = getDirections(board);
        if (result.isEmpty()) return "";
        return result.get(0).toString();
    }

    public List<Direction> getDirections(Board board) {
        int size = board.size();
        Point from = board.getHero();
        if (from == null) {
            return Arrays.asList();
        }
        List<Point> to = board.get(clues);
        DeikstraFindWay.Possible map = possible(board);
        return way.getShortestWay(size, from, to, map);
    }

    public DeikstraFindWay getWay() {
        return way;
    }
}