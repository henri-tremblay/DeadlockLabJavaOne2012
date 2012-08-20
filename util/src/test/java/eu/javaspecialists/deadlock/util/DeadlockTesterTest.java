package eu.javaspecialists.deadlock.util;

import org.junit.Test;

import java.util.concurrent.locks.*;

import static org.junit.Assert.fail;

public class DeadlockTesterTest {

    @Test
    public void testDeadlockIsDetected() throws InterruptedException {
        final Lock lock1 = new ReentrantLock();
        final Lock lock2 = new ReentrantLock();
        Thread t1 = new Thread("t1") {
            public void run() {
                try {
                    lock1.lockInterruptibly();
                    try {
                        Thread.sleep(100);
                        lock2.lockInterruptibly();
                        try {
                            Thread.sleep(100);
                        } finally {
                            lock2.unlock();
                        }
                    } finally {
                        lock1.unlock();
                    }
                } catch (InterruptedException consumeAndExit) {
                }
            }
        };
        t1.start();
        Thread t2 = new Thread("t2") {
            public void run() {
                try {
                    lock2.lockInterruptibly();
                    try {
                        Thread.sleep(100);
                        lock1.lockInterruptibly();
                        try {
                            Thread.sleep(100);
                        } finally {
                            lock1.unlock();
                        }
                    } finally {
                        lock2.unlock();
                    }
                } catch (InterruptedException consumeAndExit) {
                }
            }
        };
        t2.start();

        Thread.sleep(200);

        DeadlockTester tester = new DeadlockTester();
        try {
            tester.checkThatThreadTerminates(t1);
            fail("Expected to see a deadlock");
        } catch (IllegalStateException expected) {
        }
        t1.interrupt();
        t2.interrupt();

        Thread.sleep(50); // give time to resolve deadlock
        tester.checkThatThreadTerminates(t1); // should not deadlock
        tester.checkThatThreadTerminates(t2); // should not deadlock

    }
}
