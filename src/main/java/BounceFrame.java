import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BounceFrame extends JFrame {
    private final BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;

    private final JTextField textField;

    private final AtomicInteger destroyedBalls = new AtomicInteger(0);

    private final BallThread.OnBallInHole onBallInHole = new BallThread.OnBallInHole() {

        @Override
        public void onBallInHole() {
            textField.setText("Destroyed " + destroyedBalls.incrementAndGet() + " balls");
        }

    };

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton buttonRed = new JButton("Red^ ball");
        JButton buttonBlue = new JButton("Blue ball");

        JButton buttonStart1000 = new JButton("Start 1000");
        JButton buttonStop = new JButton("Stop");

        buttonRed.addActionListener(e -> createRedBall());
        buttonBlue.addActionListener(e -> createBlueBall());

        buttonStart1000.addActionListener(e -> {

            Ball b = new Ball(canvas, Color.RED);
            canvas.add(b);

            BallThread thread = new BallThread(b, onBallInHole);
            thread.setPriority(8); // the default is 5
            thread.start();

            Ball b2 = new Ball(canvas, Color.BLUE);
            canvas.add(b2);

            Thread blueBallThread = new Thread(() -> {
                try {
                    thread.join();
                    for (int i = 1; i < 10000; i++) {
                        b2.move();
                        Thread.sleep(5);
                        if (b2.isInHole()) {
                            onBallInHole.onBallInHole();
                            b2.remove();
                            return; // stopping the thread
                        }
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();// set the interrupted flag
                }
            });

            blueBallThread.start();
        });

        buttonStop.addActionListener(e -> System.exit(0));

        textField = new JTextField("Destroyed 0 balls");

        buttonPanel.add(buttonRed);
        buttonPanel.add(buttonBlue);
        buttonPanel.add(buttonStart1000);
        buttonPanel.add(buttonStop);
        buttonPanel.add(textField);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    /*public void createBall() {
        Ball b = new Ball(canvas);
        canvas.add(b);

        BallThread thread = new BallThread(b, onBallInHole);
        thread.start();
        System.out.println("Thread name = " +
                thread.getName());
    }*/

    public void createRedBall() {
        Ball b = new Ball(canvas, Color.RED);
        canvas.add(b);

        BallThread thread = new BallThread(b, onBallInHole);
        thread.setPriority(8); // the default is 5
        thread.start();
    }

    public void createBlueBall() {
        Ball b = new Ball(canvas, Color.BLUE);
        canvas.add(b);

        BallThread thread = new BallThread(b, onBallInHole);
        thread.setPriority(2);
        thread.start();
    }

}