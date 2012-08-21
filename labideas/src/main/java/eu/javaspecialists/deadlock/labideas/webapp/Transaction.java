package eu.javaspecialists.deadlock.labideas.webapp;

import java.io.File;

public class Transaction {

    private File handler;

    public Transaction(File handler) {
        this.handler = handler;
    }

    public void commit() {
        // First check if the transaction hasn't timed-out
        if (!handler.exists()) {
            throw new RuntimeException("Transaction " + handler.getName() + " has timed-out");
        }
        handler.delete();
    }

    public void rollback() {
        if (handler.exists()) {
            handler.delete();
        }
    }
}
