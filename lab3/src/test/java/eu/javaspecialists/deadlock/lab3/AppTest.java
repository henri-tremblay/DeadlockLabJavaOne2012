package eu.javaspecialists.deadlock.lab3;

import eu.javaspecialists.deadlock.lab3.*;
import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class AppTest
{
    private App app = new App();

    @Test
    public void testF()
    {
        app.f();
    }

    @Test
    public void testG() {
        app.g();
    }

    @Test
    public void testRun() {
        app.run();
    }
}
