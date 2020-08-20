package org.com.challenge.tfidf.service;

import com.google.common.base.Strings;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TfIdfService {

    Logger log = Logger.getLogger(this.getClass().getName());

    private static final String FILE_EXTENSION = ".txt";
    private Map<String, Map<String, Integer>> docs;
    private Map<Double, List<String>> ranking;
    private String url;
    private String terms;
    private int n;

    public TfIdfService(String urlDir, String terms, int n) {
        docs = new HashMap<>();
        ranking = new TreeMap(Collections.reverseOrder());
        this.url = urlDir;
        this.terms = terms;
        this.n = n;
    }

    /**
     * read new files with extension FILE_EXTENSION
     *
     * @param files files paths
     */
    public void readNewFiles(List<File> files) {
        for (File file : files) {
            String fileName = file.getName().toLowerCase();
            if (!file.isDirectory() && !docs.containsKey(fileName)) {
                docs.put(fileName, readFile(file));
            }
        }
    }

    /**
     * calculate tfidf for each file in the directory
     */
    public void calculateTfIdf() {
        ranking.clear();
        for (Map.Entry<String, Map<String, Integer>> entry : docs.entrySet()) {
            double tfIdf = TfIdfCalculator.tfIdfTT(entry.getValue(), docs, terms);
            ranking.put(tfIdf, ranking.getOrDefault(tfIdf, new ArrayList<>()));
            ranking.get(tfIdf).add(entry.getKey());
        }
    }

    /**
     * Print only the n files with the highest ftidf
     * files with the same ftidf are also printed
     */
    public void printResult() {
        System.out.println("Results:");
        Iterator<Map.Entry<Double, List<String>>> it = ranking.entrySet().iterator();
        int count = 0;
        while (it.hasNext() && count < n) {
            Map.Entry<Double, List<String>> tfidtIt = it.next();
            Double tfidt = tfidtIt.getKey();
            List<String> files = tfidtIt.getValue(); // files with the same tfidt
            List<String> filesToPrint;
            if (count + files.size() <= n) {
                filesToPrint = files;
                count += files.size();
            } else {
                filesToPrint = files.subList(0, n - count);
                count = n;
            }
            filesToPrint.forEach(fileName -> System.out.println(tfidt + " " + fileName));
        }
    }

    /**
     * Reads a file
     *
     * @param file file path
     * @return a hasMap with the file's name as a key and a map with all the words and their
     * respective frequency in the file as a value
     */
    public Map<String, Integer> readFile(File file) {
        Map<String, Integer> doc = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s");
                for (String word : words) {
                    if (!Strings.isNullOrEmpty(word.trim())) {
                        String wordLowerCase = word.trim().toLowerCase();
                        doc.put(wordLowerCase, doc.getOrDefault(wordLowerCase, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            log.severe("Error when reading a file " + e.getMessage());
        }
        return doc;
    }

    public List<File> getFiles() {
        File folder = new File(getUrl());
        if (folder.listFiles() == null || folder.listFiles().length == 0) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.stream(folder.listFiles())
                .filter(file -> file.getName().toLowerCase().endsWith(FILE_EXTENSION))
                .collect(Collectors.toList());
    }

    public Map<String, Map<String, Integer>> getDocs() {
        return docs;
    }

    public void setDocs(Map<String, Map<String, Integer>> docs) {
        this.docs = docs;
    }

    public Map<Double, List<String>> getRanking() {
        return ranking;
    }

    public void setRanking(Map<Double, List<String>> ranking) {
        this.ranking = ranking;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
