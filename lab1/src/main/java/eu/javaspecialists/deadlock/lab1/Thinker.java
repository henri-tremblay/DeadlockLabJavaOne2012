package eu.javaspecialists.deadlock.lab1;

import java.util.concurrent.*;

public class Thinker implements Callable<String> {
  private final int id;
  private final Krasi left, right;

  public Thinker(int id, Krasi left, Krasi right) {
    this.id = id;
    this.left = left;
    this.right = right;
  }

  public String call() throws Exception {
    for (int i = 0; i < 1000; i++) {
      drink();
      think();
    }
    return "Java is fun";
  }

  public void drink() {
    synchronized (left) {
      synchronized (right) {
        System.out.printf("(%d) Drinking%n", id);
      }
    }
  }

  public void think() {
    System.out.printf("(%d) Thinking%n", id);
  }
}
