package eu.javaspecialists.deadlock.lab1;

public class Main {

    public static void main(String[] args) {
        Symposium symposium = new Symposium(5);
        try {
            symposium.run();
        } catch (InterruptedException e) {
            return;
        }
    }
}
