package org.com.challenge.tfidf.runnable;

import org.com.challenge.tfidf.service.TfIdfService;

import java.io.File;
import java.util.List;

public class TfIdfRunnable implements Runnable {

    private TfIdfService tfIdfService;

    public TfIdfRunnable(String urlDir, String terms, int n) {
        tfIdfService = new TfIdfService(urlDir, terms, n);
    }

    @Override
    public void run() {
        List<File> files = tfIdfService.getFiles();
        //Check if there is a new file
        //if so, the new files are read and then the tfidfs of all the files are updated
        if (files.size() != tfIdfService.getDocs().size()) {
            tfIdfService.readNewFiles(files);
            tfIdfService.calculateTfIdf();
        }
        tfIdfService.printResult();
    }
}
