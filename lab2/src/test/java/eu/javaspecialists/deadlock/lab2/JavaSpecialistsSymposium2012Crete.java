package eu.javaspecialists.deadlock.lab2;

import org.junit.*;

import java.lang.management.*;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class JavaSpecialistsSymposium2012Crete {
    @Test
    public void runSymposium() throws InterruptedException {
        ThreadGroup group = new ThreadGroup("testGroup");
        Thread runner = new Thread(group, "runner") {
            public void run() {
                Symposium symposium = new Symposium(5);
                try {
                    symposium.run();
                } catch (InterruptedException e) {
                    return;
                }
            }
        };
        runner.start();

        while (runner.isAlive()) {
            runner.join(100);

            Thread[] threads = new Thread[group.activeCount()];
            group.enumerate(threads);

            ThreadMXBean tmb = ManagementFactory.getThreadMXBean();
            long[] deadlocks = tmb.findDeadlockedThreads();
            if (deadlocks != null) {
                for (long deadlock : deadlocks) {
                    for (Thread thread : threads) {
                        if (thread.getId() == deadlock)
                            fail("One of the threads you started has deadlocked");
                    }
                }

            }
        }
    }
}