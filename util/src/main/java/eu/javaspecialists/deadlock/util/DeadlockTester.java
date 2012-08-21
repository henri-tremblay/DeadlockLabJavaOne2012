package eu.javaspecialists.deadlock.util;

import java.lang.management.*;

public class DeadlockTester {
    private final static ThreadMXBean tmb =
            ManagementFactory.getThreadMXBean();

    /**
     * In this method we check whether any of the threads started in the
     * runnable deadlock.  We do this by starting all the threads in a single
     * group and then checking if any of those threads have deadlocked.  This
     * way we don't get interference from other deadlocks.
     *
     * @param runnable
     */
    public void checkThatCodeDoesNotDeadlock(Runnable runnable)
            throws InterruptedException {
        ThreadGroup group = new ThreadGroup("testGroup");
        Thread runner = new Thread(group, runnable, "runner");
        runner.start();

        while (runner.isAlive()) {
            runner.join(100);

            Thread[] threads = new Thread[group.activeCount()];
            group.enumerate(threads);

            long[] deadlocks = tmb.findDeadlockedThreads();
            if (deadlocks != null) {
                for (long deadlock : deadlocks) {
                    for (Thread thread : threads) {
                        if (thread.getId() == deadlock) {
                            throw new DeadlockError(thread);
                        }
                    }
                }
            }
        }
    }

    public void checkThatThreadTerminates(Thread thread)
            throws InterruptedException {
        for (int i = 0; i < 2000; i++) {
            thread.join(50);
            if (!thread.isAlive()) return;
            if (isThreadDeadlocked(thread.getId())) {
                throw new IllegalStateException("Deadlock detected!");
            }
        }
        throw new IllegalStateException(thread + " did not terminate in time");
    }

    private boolean isThreadDeadlocked(long tid) {
        long[] ids = tmb.findDeadlockedThreads();
        if (ids == null) return false;
        for (long id : ids) {
            if (id == tid) return true;
        }
        return false;
    }
}