import org.w3c.dom.xpath.XPathResult;

public class Bounce {

    public boolean flag = false;

    public static void main(String[] args) throws InterruptedException {

        boolean b = true;

        Thread hyphen;

        var ob = new Bounce();

        var bar = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                ob.printBar();
            }
        });

        hyphen = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 10; j++) {
                    ob.printHyphen(j == 9);
                }
            }
        });


        bar.start();
        hyphen.start();
    }

    public synchronized void printBar() {
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
    }
}