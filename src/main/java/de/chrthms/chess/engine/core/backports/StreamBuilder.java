/*
 *    ct-chess-engine, a chess engine playing and evaluating chess moves.
 *    Copyright (C) 2016-2017 Christian Thomas
 *
 *    This program ct-chess-engine is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.chrthms.chess.engine.core.backports;

import java8.util.stream.Stream;
import java8.util.stream.StreamSupport;

import java.util.Collection;

/**
 * Sadly the android Java Stream Support isn't given. So a backport to classic Java 7 is needed. That is the reason
 * to use this StreamBuilder instead of the Java 8 Stream API.
 *
 * TODO If Stream API is completely supported by Android, this one can be refactored/killed! (NOTE: Do not forget the RetroLambda Maven Plugin)
 *
 * Created by christian on 02.01.17.
 */
public abstract class StreamBuilder {

    public static <T> Stream<T> stream(Collection<? extends T> collection) {
         return StreamSupport.stream(collection);
    }

}
