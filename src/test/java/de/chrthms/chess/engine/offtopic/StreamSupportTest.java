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

package de.chrthms.chess.engine.offtopic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian on 02.01.17.
 */
public class StreamSupportTest {

    private List<String> strings = null;

    @Before
    public void prepare() {

        strings = new ArrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");

    }

// compile error with util.List and required Spliterator in stream method
//    @Test
//    public void testStdStreamSupport() {
//
//        java.util.stream.StreamSupport
//                .stream(strings, false)
//                .forEach(str -> System.out.println("str = " + str));
//
//    }

    @Test
    public void testJava8BackportStreamSupport() {

        java8.util.stream.StreamSupport
                .stream(strings)
                .forEach(str -> System.out.println("str = " + str));

    }

}
