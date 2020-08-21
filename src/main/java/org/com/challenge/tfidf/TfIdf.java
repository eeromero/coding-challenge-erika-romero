package org.com.challenge.tfidf;

import org.com.challenge.tfidf.runnable.TfIdfRunnable;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TfIdf {

    public static void processFilesScheduled(String urlDir, String terms, int n, int period) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new TfIdfRunnable(urlDir, terms, n), 0, period, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        //Validacion de argumentos
        if (args.length != 8) {
            System.out.println("Invalid arguments!");
            errorMessage();
            return;
        }

        String url = "", terms = "";
        int n = 0, p = 0;

        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0) {
                switch (args[i]) {
                    case "-d":
                        url = args[i + 1];
                        break;
                    case "-n":
                        n = Integer.parseInt(args[i + 1]);
                        break;
                    case "-p":
                        p = Integer.parseInt(args[i + 1]);
                        break;
                    case "-t":
                        terms = args[i + 1];
                        break;
                }
            }
        }

        if (!isValidURL(url)) {
            errorMessage();
            return;
        }
        if (terms.isEmpty()) {
            System.out.println("invalid value for -t");
            errorMessage();
            return;
        }
        if (n <= 0) {
            System.out.println("invalid value for -n");
            errorMessage();
            return;
        }
        if (p <= 0) {
            System.out.println("invalid value for -p");
            errorMessage();
            return;
        }
        processFilesScheduled(url, terms, n, p);
    }

    private static boolean isValidURL(String url) {
        if (url.isEmpty() || (new File(url)).listFiles() == null) {
            System.out.println("invalid value for -d");
            return false;
        }
        return true;
    }

    private static void errorMessage() {
        System.out.println("This app needs next parameters");
        System.out.println("-d :url directory");
        System.out.println("-t :terms");
        System.out.println("-n :number of file names to display with the best ftidf");
        System.out.println("-p :how often the result will be displayed(in seconds)");
        System.out.println("example: ./tdIdf -d dir -n 5 -p 300 -t 'password try again");
    }
}
