package eu.javaspecialists.deadlock.lab1;

import eu.javaspecialists.deadlock.util.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Tests whether the Symposium ends in a deadlock.  You might need to run it a
 * few times on your machine before the deadlock surfaces.
 * <p/>
 * DO NOT CHANGE THIS CODE!
 *
 * @author Heinz Kabutz
 */
public class JavaSpecialistsSymposium2012Crete {
    @Test
    public void runSymposium() throws InterruptedException {
        DeadlockTester tester = new DeadlockTester();
        try {
            tester.checkThatCodeDoesNotDeadlock(
                    new Runnable() {
                        @Override
                        public void run() {
                            Symposium symposium = new Symposium(5);
                            try {
                                symposium.run();
                            } catch (InterruptedException e) {
                                return;
                            }
                        }
                    }
            );
        } catch (DeadlockError er) {
            fail("One of the threads you started has deadlocked - " +
                    er.getThread());
        }
    }
}