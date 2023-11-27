import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread {

    private Lock leftFork;
    private Lock rightFork;
    private String name;

    public Philosopher(String name, Lock leftFork, Lock rightFork) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void eat() {
        leftFork.lock();
        rightFork.lock();
        System.out.println(name + " is eating");
        leftFork.unlock();
        rightFork.unlock();
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(name + " is thinking");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eat();
        }
    }

    public static void main(String[] args) {
        Lock[] forks = new Lock[5];
        for (int i = 0; i < 5; i++) {
            forks[i] = new ReentrantLock();
        }
        Philosopher[] philosophers = new Philosopher[5];
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher("Philosopher " + (i + 1), forks[i], forks[(i + 1) % 5]);
        }
        for (int i = 0; i < 5; i++) {
            philosophers[i].start();
        }
    }
}