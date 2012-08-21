package eu.javaspecialists.deadlock.labideas.webapp;

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
