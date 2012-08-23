package eu.javaspecialists.deadlock.lab2solutioncounting;

import java.util.concurrent.*;

/**
 * At the symposium, we create a bunch of thinkers and place cups of wine
 * between them.  We then run them in a cached thread pool.  Unfortunately when
 * all the Thinker instances try to drink at the same time, they cause a
 * deadlock.
 * <p/>
 * DO NOT CHANGE THIS CODE!
 *
 * @author Heinz Kabutz
 */
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

    @SuppressWarnings("boxing")
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

        for (int i = 0; i < thinkers.length; i++) {
            System.out.printf("thinker[%d] = %,3d%n", i,
                    thinkers[i].getRetry());
        }
    }
}