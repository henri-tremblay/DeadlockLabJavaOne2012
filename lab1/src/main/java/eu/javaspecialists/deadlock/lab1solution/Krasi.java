package eu.javaspecialists.deadlock.lab1solution;

import java.util.concurrent.atomic.*;

public class Krasi implements Comparable<Krasi> {
    private final static AtomicInteger nextCupNumber = new AtomicInteger();
    private final int cupNumber = nextCupNumber.incrementAndGet();

    @Override
    public int compareTo(Krasi o) {
        return Integer.compare(cupNumber, o.cupNumber);
    }

    @Override
    public String toString() {
        return "Krasi{cupNumber=" + cupNumber + '}';
    }
}
