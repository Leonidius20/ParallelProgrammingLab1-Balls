

public class Bounce {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        var incrementor = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                synchronized (counter) {
                    counter.increment();
                }
            }
        });

        var decrementor = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                synchronized (counter) {
                    counter.decrement();
                }

            }
        });

        incrementor.start();
        decrementor.start();

        incrementor.join();
        decrementor.join();

        System.out.println(counter.get());
    }

    /*public synchronized void printBar() {
        while (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread Interrupted");
            }
        }
        flag = true;

        System.out.print("|");
        notifyAll();
    }

    public synchronized void printHyphen(boolean isLastInLine) {
        while (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread Interrupted");
            }
        }
        flag = false;

        System.out.print("-");
        if (isLastInLine) System.out.println();
        notifyAll();
    }*/
}