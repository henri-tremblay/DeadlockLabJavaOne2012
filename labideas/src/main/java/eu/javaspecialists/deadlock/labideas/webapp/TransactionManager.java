package eu.javaspecialists.deadlock.labideas.webapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionManager {

    private static final long TIMEOUT = 1000L * 60L * 60L * 24L * 365L; // one year... long transaction timeout

    private Timer timer;

    private Lock lock = new ReentrantLock();

    public TransactionManager() {
        final File dir = new File("data");
        dir.mkdirs();

        timer = new Timer("TransactionTimeoutThread", false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                lock.lock();
                try {
                    long start = System.currentTimeMillis();
                    for (File file : dir.listFiles()) {
                        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                            String line = in.readLine();
                            long timestamp = Long.parseLong(line);
                            long now = System.currentTimeMillis();
                            // Check if the transaction has timed-out and if yes, kill it
                            if (timestamp + now > TIMEOUT) {
                                file.delete();
                            }
                        } catch (IOException e) {
                            file.delete(); // kill the transaction
                        }
                    }
                    long stop = System.currentTimeMillis();
                    System.out.printf("Time: %d%n", stop - start);
                } finally {
                    lock.unlock();
                }
                TransactionManager.this.notifyAll();
            }
        }, 0, 30000);
    }

    public void stop() {
        timer.cancel();
    }

    public Transaction allocateTransaction() {
        while (!lock.tryLock()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            File file = new File("data", UUID.randomUUID().toString());
            try {
                saveTransactionCreationTimestamp(file);
            } catch (IOException e) {
                throw new RuntimeException("Failed to allocate transaction", e);
            }
            return new Transaction(file);
        } finally {
            lock.unlock();
        }
    }

    private File saveTransactionCreationTimestamp(File file) throws IOException {
        try (FileWriter out = new FileWriter(file)) {
            out.write(Long.toString(System.currentTimeMillis()));
        }
        return file;
    }

}
