package eu.javaspecialists.deadlock.lab2;

import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class JavaSpecialistsSymposium2012Crete {

    @Test
    public void runSymposium() throws InterruptedException {
        Symposium symposium = new Symposium(5);
        symposium.run();
    }
}