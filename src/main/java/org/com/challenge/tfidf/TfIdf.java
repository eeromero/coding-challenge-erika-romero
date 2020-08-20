package org.com.challenge.tfidf;

import org.com.challenge.tfidf.runnable.TfIdfRunnable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TfIdf {

    public static void processFilesScheduled(String urlDir, String terms, int n, int period) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new TfIdfRunnable(urlDir, terms, n), 5, period, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        if (args.length != 8) {
            System.out.println("Invalid arguments");
            System.out.println("Make sure to send next parameters");
            System.out.println("-d :url directory");
            System.out.println("-t :terms");
            System.out.println("-n :n top result to display");
            System.out.println("-p :period how often the result will be displayed(in seconds)");
            System.out.println("example: ./tdIdf -d dir -n 5 -p 300 -t 'password try again");
            return;
        }

        String url = "";
        String terms = "";
        int n = 0;
        int period = 0;

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
                        period = Integer.parseInt(args[i + 1]);
                        break;
                    case "-t":
                        terms = args[i + 1];
                        break;
                }
            }
        }

        processFilesScheduled(url, terms, n, period);
    }
}
