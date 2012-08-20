package eu.javaspecialists.deadlock.lab1solution;

import eu.javaspecialists.deadlock.lab1.Symposium;

public class JavaSpecialistsSymposium2012Crete {
    public static void main(String[] args)
            throws InterruptedException {
        eu.javaspecialists.deadlock.lab1.Symposium symposium = new Symposium(5);
        symposium.run();
    }
}