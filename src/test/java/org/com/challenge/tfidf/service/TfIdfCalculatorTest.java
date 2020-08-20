package org.com.challenge.tfidf.service;

import com.google.common.collect.ImmutableMap;

import static org.com.challenge.tfidf.service.TfIdfCalculator.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TfIdfCalculatorTest {

    @ParameterizedTest
    @MethodSource("tfProvider")
    void testTf(Map<String, Integer> doc, String term, Double expectedResult) {
        assertThat(tf(term, doc), is(expectedResult));
    }

    @ParameterizedTest
    @MethodSource("idfProvider")
    void testIdf(Map<String, Map<String, Integer>> docs, String term, Double expectedResult) {
        assertThat(idf(term, docs), is(expectedResult));
    }

    @ParameterizedTest
    @MethodSource("tfIdfTermProvider")
    void tfIdfTerm(Map<String, Integer> doc, Map<String, Map<String, Integer>> docs, String term, Double expectedResult) {
        assertThat(tfIdfT(doc, docs, term), is(expectedResult));
    }

    @ParameterizedTest
    @MethodSource("tfIdfTTProvider")
    void testTfIdfTT(Map<String, Integer> doc, Map<String, Map<String, Integer>> docs, String term, Double expectedResult) {
        assertThat(tfIdfTT(doc, docs, term), is(expectedResult));
    }

    static Stream<Arguments> tfProvider() {
        Map doc1 = ImmutableMap.of("this", 1, "is", 1, "a", 2, "sample", 1);
        Map doc2 = ImmutableMap.of("this", 1, "is", 1, "another", 2, "example", 3);
        Map doc3 = ImmutableMap.of("at", 2, "table", 1);
        Map doc4 = ImmutableMap.of();
        return Stream.of(
                arguments(doc3, "at", 0.6666666666666666),
                arguments(doc1, "this", 0.2),
                arguments(doc2, "this", 0.14285714285714285),
                arguments(doc1, "example", 0.0),
                arguments(doc2, "example", 0.42857142857142855),
                arguments(doc4, "example", 0.0)
        );
    }

    static Stream<Arguments> idfProvider() {
        Map doc1 = ImmutableMap.of("this", 1, "is", 1, "a", 2, "sample", 1);
        Map doc2 = ImmutableMap.of("this", 1, "is", 1, "another", 2, "example", 3);
        Map docs = ImmutableMap.of("docs/doc1.txt", doc1, "doc2.txt", doc2);

        return Stream.of(
                arguments(docs, "this", 0.0),
                arguments(docs, "example", 0.3010299956639812),
                arguments(docs, "table", 0.3010299956639812)
        );
    }

    static Stream<Arguments> tfIdfTermProvider() {
        Map doc1 = ImmutableMap.of("this", 1, "is", 1, "a", 2, "sample", 1);
        Map doc2 = ImmutableMap.of("this", 1, "is", 1, "another", 2, "example", 3);
        Map docs = ImmutableMap.of("docs/doc1.txt", doc1, "doc2.txt", doc2);
        return Stream.of(
                arguments(doc1, docs, "this", 0.0),
                arguments(doc1, docs, "sample", 0.06),
                arguments(doc2, docs, "example", 0.13),
                arguments(doc2, docs, "another", 0.09)
        );
    }


    static Stream<Arguments> tfIdfTTProvider() {
        Map doc1 = ImmutableMap.of("this", 1, "is", 1, "a", 2, "sample", 1);
        Map doc2 = ImmutableMap.of("this", 1, "is", 1, "another", 2, "example", 3);
        Map docs = ImmutableMap.of("docs/doc1.txt", doc1, "doc2.txt", doc2);
        return Stream.of(
                arguments(doc1, docs, "this sample", 0.06),// = tfidf(doc1, docs, "this")+tfidf(doc1, docs, "sample")
                arguments(doc2, docs, "another example", 0.22)// = tfidf(doc2, docs, "another")+tfidf(doc2, docs, "example")
        );
    }

}