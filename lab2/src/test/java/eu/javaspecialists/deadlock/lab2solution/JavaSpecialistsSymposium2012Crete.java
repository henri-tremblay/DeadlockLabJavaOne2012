package eu.javaspecialists.deadlock.lab2solution;

import org.junit.Test;
import eu.javaspecialists.deadlock.util.*;

/**
 * Unit test for simple App.
 */
public class JavaSpecialistsSymposium2012Crete {
    @Test
    public void runSymposium() throws InterruptedException {
        Thread runner = new Thread() {
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

        DeadlockTester tester = new  DeadlockTester();
        tester.checkThatThreadTerminates(runner);
    }
}