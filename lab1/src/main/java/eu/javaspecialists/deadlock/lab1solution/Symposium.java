package eu.javaspecialists.deadlock.lab1solution;

import java.util.concurrent.*;

public class Symposium {
    private final Krasi[] cups;
    private final Thinker[] thinkers;

    public Symposium(int delegates) {
        cups = new Krasi[delegates];
        thinkers = new Thinker[delegates];
        for (int i = 0; i < cups.length; i++) {
            cups[i] = new Krasi();
        }
        for (int i = 0; i < delegates; i++) {
            Krasi left = cups[i];
            Krasi right = cups[(i + 1) % delegates];
            thinkers[i] = new Thinker(i, left, right);
        }
    }

    public void run() throws InterruptedException {
        // do this after we created the symposium, so that we do not
        // let the reference to the Symposium escape.
        ExecutorService exec = Executors.newCachedThreadPool();
        CompletionService<String> results =
                new ExecutorCompletionService<>(exec);
        for (Thinker thinker : thinkers) {
            results.submit(thinker);
        }
        System.out.println("Waiting for results");
        for (Thinker thinker : thinkers) {
            try {
                System.out.println(results.take().get());
            } catch (ExecutionException e) {
                e.getCause().printStackTrace();
            }
        }
        exec.shutdown();
    }
}