package org.com.challenge.tfidf.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

public class TfIdfCalculator {
    /**
     * Term frequency
     * tf(t,d) = n/N
     * n is the number of times term t appears in the document d.
     * N is the total number of terms in the document d.
     *
     * @param doc  Map of words with the number of times the word appears in the document.
     * @param term String represents a term
     * @return term frequency of term in document
     */
    public static double tf(String term, Map<String, Integer> doc) {
        double termCount = doc.getOrDefault(term, 0);
        int totalWords = doc.entrySet().stream().mapToInt(e -> e.getValue()).sum();
        return totalWords != 0 ? termCount / totalWords : 0.0;
    }

    /**
     * Inverse document frequency
     * idf(t,D) = log (N/( n))
     * N is the number of documents in the data set.
     * n is the number of documents that contain the term t among the data set.
     * n is adjusted to 1 when the number of documents is 0 to avoid zero division
     *
     * @param docs A map of maps that represents the data set
     * @param term String represents a term
     * @return the inverse term frequency of term in documents
     */
    public static double idf(String term, Map<String, Map<String, Integer>> docs) {
        double n = docs.entrySet().stream().filter(entry -> entry.getValue().containsKey(term)).count();
        double nAdjusted = n != 0 ? n : 1;
        return Math.log10(docs.size() / nAdjusted);
    }

    /**
     * TFIDF(t,d,D) = tf(t,d) * idf(t,D)
     *
     * @param doc  a text document (a map)
     * @param docs all documents (a map of maps)
     * @param term term
     * @return the TF/IDF of term. Result is rounded with 2 decimals
     */
    public static double tfIdfT(Map<String, Integer> doc, Map<String, Map<String, Integer>> docs, String term) {
        return BigDecimal.valueOf(tf(term, doc) * idf(term, docs)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * It calculates the tfIdf of a list of terms
     * tfIdfTT =  tfIdfT(term1) + tfIdfT(term2)...
     *
     * @param doc   a text document (a map)
     * @param docs  all documents (a map of maps)
     * @param terms String with more than 1 term
     * @return the TF/IDF of term. Result is rounded with 2 decimals
     */
    public static double tfIdfTT(Map<String, Integer> doc, Map<String, Map<String, Integer>> docs, String terms) {
        String[] termList = terms.toLowerCase().split(" ");
        return Arrays.stream(termList).mapToDouble(term -> tfIdfT(doc, docs, term)).sum();
    }
}
