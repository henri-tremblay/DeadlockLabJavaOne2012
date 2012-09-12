package eu.javaspecialists.deadlock.util;

public class DeadlockError extends Error {
    private final Thread thread;

    public DeadlockError(Thread thread) {
        super("Deadlock involving thread: " + thread);
        this.thread = thread;
    }

    public Thread getThread() {
        return thread;
    }
}
