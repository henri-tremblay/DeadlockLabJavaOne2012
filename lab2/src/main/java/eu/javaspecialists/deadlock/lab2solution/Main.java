package eu.javaspecialists.deadlock.lab2solution;

/**
 * Launcher to test whether the symposium ends in a deadlock.  Hopefully the
 * deadlock has now disappeared.
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