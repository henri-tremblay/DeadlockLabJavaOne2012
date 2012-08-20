package eu.javaspecialists.deadlock.lab3;

public class TransactionCreator {

    private static int FILES = 5000;

    public static void main(String[] args) {
        TransactionManager manager = new TransactionManager();
        for(int i = 0; i < FILES; i++) {
            manager.allocateTransaction();
        }
    }
}
