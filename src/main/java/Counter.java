public class Counter {

    private int counter;

    public synchronized void increment() { counter++; }

    public synchronized void decrement() { counter--; }

    public int get() { return counter; }

}
