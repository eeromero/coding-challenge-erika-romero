package org.com.challenge.tfidf.service;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInRelativeOrder;

class TfIdfServiceTest {
    private static TfIdfService tfIdfService;

    @BeforeAll
    static void beforeAll() {
        tfIdfService = new TfIdfService(getPathFileName("docs/"), "", 5);
    }


    @Test
    void testGetFiles_filterTxt() {
        List<File> fileList = tfIdfService.getFiles();
        assertThat(fileList, hasSize(1));
    }

    @Test
    void testReadNewFiles() throws IOException {
        List<File> files = tfIdfService.getFiles();
        ;
        tfIdfService.readNewFiles(files);
        assertThat(tfIdfService.getDocs(), aMapWithSize(1));
        assertThat(tfIdfService.getDocs(), hasKey("doc1.txt"));
        Map doc1 = ImmutableMap.of("this", 1, "is", 1, "a", 1, "example", 2);
        assertThat(tfIdfService.getDocs(), hasValue(doc1));
        //create a new file
        File newFile = createNewFile(files.get(0).getAbsoluteFile().toString().replace("doc1", "doc3"));
        //read new file
        files = tfIdfService.getFiles();
        ;
        tfIdfService.readNewFiles(files);
        Map doc3 = ImmutableMap.of("this", 1, "is", 1, "another", 1, "line", 1, "example", 2);
        assertThat(tfIdfService.getDocs(), aMapWithSize(2));
        assertThat(tfIdfService.getDocs(), hasKey("doc1.txt"));
        assertThat(tfIdfService.getDocs(), hasValue(doc1));
        assertThat(tfIdfService.getDocs(), hasKey("doc3.txt"));
        assertThat(tfIdfService.getDocs(), hasValue(doc3));
        //remove new file
        newFile.delete();
    }

    @Test
    void testCalculateFtIdf() {
        Map doc1 = ImmutableMap.of("this", 1, "is", 1, "a", 2, "sample", 1);
        Map doc2 = ImmutableMap.of("this", 1, "is", 1, "another", 2, "example", 3);
        Map docs = ImmutableMap.of("doc1.txt", doc1, "doc2.txt", doc2);
        tfIdfService.setDocs(docs);
        tfIdfService.setTerms("sample");
        tfIdfService.calculateTfIdf();
        assertThat(tfIdfService.getRanking(), aMapWithSize(2));
        assertThat(tfIdfService.getRanking().keySet(), containsInRelativeOrder(0.06, 0.0));
        assertThat(tfIdfService.getRanking().values(), containsInRelativeOrder(Arrays.asList("doc1.txt"), Arrays.asList("doc2.txt")));
    }

    private File createNewFile(String url) throws IOException {
        File newFile = new File(url);
        newFile.createNewFile();
        List<String> lines = Arrays.asList("this is another line example", "example");
        Files.write(newFile.toPath(), lines, StandardCharsets.UTF_8);
        return newFile;
    }

    private static String getPathFileName(String fileName) {
        return TfIdfServiceTest.class.getClassLoader().getResource(fileName).getPath();
    }
}