package eu.javaspecialists.deadlock.lab3;

import static org.junit.Assert.*;

import org.junit.Test;

public class TransactionManagerTest {

    @Test
    public void testTransactionManager() throws Exception {
        TransactionManager manager = new TransactionManager();
        Thread.sleep(60000);
    }

    @Test
    public void testAllocateTransaction() {
        fail("Not yet implemented");
    }

}
