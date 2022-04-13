public class Counter {

    private int counter;

    // the synchronized blocks are in Main class
    public void increment() { counter++; }

    public void decrement() { counter--; }

    public int get() { return counter; }

}
