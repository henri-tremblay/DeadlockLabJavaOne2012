package eu.javaspecialists.deadlock.labideas.walkingcollection;

/**
 * DO NOT CHANGE.
 */
public class PrintProcessor implements Processor<Object> {
    public boolean process(Object o) {
        System.out.println(">>> " + o);
        return true;
    }
}
