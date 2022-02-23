
public class BallThread extends Thread {

    private final Ball b;

    private final OnBallInHole callback;

    public BallThread(Ball ball, OnBallInHole callback) {
        b = ball;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < 10000; i++) {
                b.move();
                //System.out.println("Thread name = "
                //        + Thread.currentThread().getName());
                Thread.sleep(5);
                if (b.isInHole()) {
                    callback.onBallInHole();
                    b.remove();
                    return; // stopping the thread
                }
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();// set the interrupted flag
            return;//Stop doing whatever I am doing and terminate

        }
    }

    public static interface OnBallInHole {

        public void onBallInHole();

    }

}