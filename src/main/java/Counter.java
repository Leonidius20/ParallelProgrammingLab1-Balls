public class Counter {

    private int counter;

    // it's synchronized
    public synchronized void increment() { counter++; }

    public synchronized void decrement() { counter--; }

    public int get() { return counter; }

}
