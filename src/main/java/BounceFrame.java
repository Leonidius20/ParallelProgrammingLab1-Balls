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
        JButton buttonStart = new JButton("Start");
        JButton buttonStart1000 = new JButton("Start 1000");
        JButton buttonStop = new JButton("Stop");

        buttonStart.addActionListener(e -> createBall());

        buttonStart1000.addActionListener(e -> {
            for (int i = 0; i < 1000; i++) {
                createBall();
            }
        });

        buttonStop.addActionListener(e -> System.exit(0));

        textField = new JTextField("Destroyed 0 balls");

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStart1000);
        buttonPanel.add(buttonStop);
        buttonPanel.add(textField);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void createBall() {
        Ball b = new Ball(canvas);
        canvas.add(b);

        BallThread thread = new BallThread(b, onBallInHole);
        thread.start();
        System.out.println("Thread name = " +
                thread.getName());
    }

}