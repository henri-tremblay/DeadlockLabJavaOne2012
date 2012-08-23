package eu.javaspecialists.deadlock.lab1;

/**
 * Launcher to test whether the symposium ends in a deadlock.  You might need to
 * run it a few times on your machine before the deadlock surfaces.
 * <p/>
 * DO NOT CHANGE THIS CODE!
 *
 * @author Henri Tremblay, Heinz Kabutz
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Symposium symposium = new Symposium(5);
        symposium.run();
        System.out.println("No deadlock detected!");
    }
}
