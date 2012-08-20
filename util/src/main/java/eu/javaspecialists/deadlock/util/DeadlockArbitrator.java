package eu.javaspecialists.deadlock.util;

import java.lang.management.*;
import java.util.concurrent.*;

public class DeadlockArbitrator {
  private static final ThreadMXBean tmb =
      ManagementFactory.getThreadMXBean();

  public boolean tryResolveDeadlock() throws InterruptedException {
    return tryResolveDeadlock(3, 1, TimeUnit.SECONDS);
  }

  public boolean tryResolveDeadlock(
      int attempts, long timeout, TimeUnit unit)
      throws InterruptedException {
    for (int i = 0; i < attempts; i++) {
      long[] ids = tmb.findDeadlockedThreads();
      if (ids == null) return true;
      Thread t = findThread(ids[i % ids.length]);
      if (t == null)
        throw new IllegalStateException("Could not find thread");
      t.stop(new DeadlockVictimError(t));
      unit.sleep(timeout);
    }
    return false;
  }

  private Thread findThread(long id) {
    for (Thread thread : Thread.getAllStackTraces().keySet()) {
      if (thread.getId() == id) return thread;
    }
    return null;
  }
}