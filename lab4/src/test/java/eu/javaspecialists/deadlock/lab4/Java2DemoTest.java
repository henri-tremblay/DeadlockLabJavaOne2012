package eu.javaspecialists.deadlock.lab4;

import org.junit.*;

import javax.swing.*;
import java.util.concurrent.*;

public class Java2DemoTest {
    @Test
    public void startJava2Demo() throws InterruptedException {
        Java2Demo.main(new String[0]);
        final BlockingQueue<Thread> q = new SynchronousQueue<>();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                q.add(Thread.currentThread());
            }
        });
        Thread swingThread = q.take();
        swingThread.join(60 * 60 * 1000); // one hour
    }
}
