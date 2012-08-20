package eu.javaspecialists.deadlock.lab3.ideas;

import java.io.*;
import java.nio.channels.*;

public class FileCopier {
    public void copy(String from, String to) throws IOException {
        RandomAccessFile fromRAF = new RandomAccessFile(from, "rw");
        RandomAccessFile toRAF = new RandomAccessFile(to, "rw");
        FileChannel fromFC = fromRAF.getChannel();
        FileChannel toFC = toRAF.getChannel();
        FileLock fromLock = fromFC.lock();
        try {
            FileLock toLock = toFC.lock();
            try {
                System.out.println(fromRAF);
                System.out.println(toRAF);
                fromFC.transferTo(0, fromFC.size(), toFC);
            } finally {
                toLock.release();
            }

        } finally {
            fromLock.release();
        }
    }

    public static void main(String[] args) throws IOException {
//        Thread t1 = new Thread("t1") {
//            @Override
//            public void run() {
//                while(true) {
//                    try {
//                        FileCopier fc = new FileCopier();
//                        fc.copy("README.md", "README.md.bak");
//                    } catch (IOException e) {
//                        System.err.println(e);
//                    }
//                }
//            }
//        };
//        t1.start();
//        Thread t2 = new Thread("t2") {
//            @Override
//            public void run() {
//                while(true) {
//                    try {
//                        FileCopier fc = new FileCopier();
//                        fc.copy("README.md.bak", "README.md");
//                    } catch (IOException e) {
//                        System.err.println(e);
//                    }
//                }
//            }
//        };
//        t2.start();
//
        FileCopier fc = new FileCopier();
        fc.copy("README.md", "README.md");
        System.out.println("copied file to itself");

    }
}
