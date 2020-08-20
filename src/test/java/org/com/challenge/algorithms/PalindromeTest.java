package org.com.challenge.algorithms;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

class PalindromeTest {

    private static Palindrome palindrome;

    @BeforeAll
    static void beforeAll() {
        palindrome = new Palindrome();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "eve", "Taco, cat", "A man, a plan, a canal: Panama!"})
    void testIsPalindrome(String word) {
        assertThat(palindrome.isPalindrome(word), is(Boolean.TRUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"television", "table"})
    void testIsPalindrome_noPalindrome(String word) {
        assertThat(palindrome.isPalindrome(word), is(Boolean.FALSE));
    }
}