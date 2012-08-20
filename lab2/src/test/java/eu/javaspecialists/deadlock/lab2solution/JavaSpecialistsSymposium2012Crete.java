package eu.javaspecialists.deadlock.lab2solution;

import eu.javaspecialists.deadlock.lab2.Symposium;
import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class JavaSpecialistsSymposium2012Crete {

    @Test
    public void runSymposium() throws InterruptedException {
        eu.javaspecialists.deadlock.lab2.Symposium symposium = new Symposium(5);
        symposium.run();
    }
}