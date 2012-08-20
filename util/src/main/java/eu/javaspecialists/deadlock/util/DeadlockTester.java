package eu.javaspecialists.deadlock.util;

import java.lang.management.*;

public class DeadlockTester {
    private final static ThreadMXBean tmb =
            ManagementFactory.getThreadMXBean();

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