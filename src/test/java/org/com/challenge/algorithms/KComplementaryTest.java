package org.com.challenge.algorithms;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class KComplementaryTest {

    private static KComplementary kComplementary;

    @BeforeAll
    static void beforeAll() {
        kComplementary = new KComplementary();
    }

    @Test
    void testIsPalindrome() {
        List<Pair> result = Arrays.asList(new Pair(-1, 8),
                new Pair(1, 6), new Pair(3, 4));
        assertThat(kComplementary.kComplementary(new int[]{1, 1, 8, 6, 5, 6, -1, 4, 3}, 7), is(result));
    }

    @Test
    void testIsPalindrome_noFound() {
        List<Pair> result = Arrays.asList(new Pair(1, 6));
        assertThat(kComplementary.kComplementary(new int[]{1, 1}, 7), is(Collections.emptyList()));
    }

}