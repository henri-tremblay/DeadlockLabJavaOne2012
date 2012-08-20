package eu.javaspecialists.deadlock.lab3.ideas;


import org.junit.*;

import java.util.*;
import java.util.concurrent.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * DO NOT CHANGE.
 */
public class WalkingCollectionTest {
  @Test
  public void testIteratorMethods() throws Exception {
    WalkingCollection<Long> ages = new WalkingCollection<>(
        new ArrayList<Long>()
    );

    ages.add(10L);
    ages.add(35L);
    ages.add(12L);
    ages.add(33L);

    PrintProcessor pp = new PrintProcessor();
    ages.iterate(pp);

    AddProcessor<Long> ap = new AddProcessor<>();
    ages.iterate(ap);
    assertEquals("Total does not match up", 90.0, ap.getTotal());

    // composite
    System.out.println("Testing Composite");
    ap.reset();

    CompositeProcessor<Long> composite =
        new CompositeProcessor<>();
    composite.add(new Processor<Long>() {
      private long previous = Long.MIN_VALUE;

      public boolean process(Long current) {
        boolean result = current >= previous;
        previous = current;
        return result;
      }
    });
    composite.add(ap);
    composite.add(pp);
    ages.iterate(composite);
    assertEquals("Total does not match up", 45.0, ap.getTotal());
  }

  @Test
  public void testModifyingWhilstIterating() throws InterruptedException {
    final WalkingCollection<String> names =
        new WalkingCollection<>(new ArrayList<String>());

    names.add("Maximilian");
    names.add("Constance");
    names.add("Priscilla");
    names.add("Evangeline");

    final Processor<String> pp = new Processor<String>() {
      public boolean process(String s) {
        if ("Priscilla".equals(s)) names.remove(s);
        return true;
      }
    };
    ExecutorService pool = Executors.newSingleThreadExecutor();
    pool.submit(new Runnable() {
      public void run() {
        names.iterate(pp);
      }
    });
    pool.shutdown();
    assertTrue("Iteration did not complete in time",
        pool.awaitTermination(1, TimeUnit.MINUTES));
  }

  @Test
  public void testReadWriteLocks() throws Exception {
    final WalkingCollection<Long> ages = new WalkingCollection<Long>(
        new ArrayList<Long>()
    );

    ages.add(10L);
    ages.add(35L);
    ages.add(12L);
    ages.add(33L);

    new Thread("slow iterating thread") {
      public void run() {
        // make a slow processor
        ages.iterate(new Processor<Object>() {
          public boolean process(Object o) {
            try {
              System.out.println("Processing: " + o);
              Thread.sleep(100);
              System.out.println("Done: " + o);
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
            return true;
          }
        });
      }
    }.start();

    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    long time = System.currentTimeMillis();
    ages.add(32L);
    time = System.currentTimeMillis() - time;
    assertTrue("We were able to update whilst another processor " +
        "was iterating: time=" + time, time > 300);

    AddProcessor<Long> ap = new AddProcessor<>();
    ages.iterate(ap);
    assertEquals("Total does not match up", 122.0, ap.getTotal());
  }

  @Test
  public void testReadLocks() throws Exception {
    final WalkingCollection<Long> ages = new WalkingCollection<Long>(
        new ArrayList<Long>()
    );

    ages.add(10L);
    ages.add(35L);
    ages.add(12L);
    ages.add(33L);

    Thread[] iteratingThreads = new Thread[3];
    for (int i = 0; i < iteratingThreads.length; i++) {
      iteratingThreads[i] = new Thread("slow iterating thread " + i) {
        public void run() {
          // make a slow processor
          ages.iterate(new Processor<Object>() {
            public boolean process(Object o) {
              try {
                System.out.println("Processing: " + o);
                Thread.sleep(100);
                System.out.println("Done: " + o);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
              return true;
            }
          });
        }
      };
      iteratingThreads[i].start();
    }

    long time = System.currentTimeMillis();
    try {
      // wait for all the threads to stop
      for (Thread iteratingThread : iteratingThreads) {
        iteratingThread.join();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    time = System.currentTimeMillis() - time;
    assertTrue("We were not able to iterate concurrently: time=" + time, time < 800);
  }
}
