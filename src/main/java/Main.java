

public class Main {

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

}