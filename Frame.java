package Boids;

import java.awt.Color;
import java.awt.FlowLayout;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

// import javax.management.timer.Timer;
import javax.swing.JFrame;

// import DS_IMPLEMENTATION.QuadTree;
import DS_IMPLEMENTATION.QuadTreeYT;
// import DS_IMPLEMENTATION.QuadTree.Node;

public class Frame extends JFrame {
    // public static final int FRAME_WIDTH = 800;
    // public static final int FRAME_HEIGHT = 600;
    Timer timer;
    TimerTask task;
    Canvas canvas;
    Boid[] boids;
    int N;

    Frame(int N) {
        this.N = N;
        this.boids = new Boid[N];
        // for (int i = 0; i < N; i++) {
        // this.boids[i] = new Boid();
        // }

        canvas = new Canvas(this.boids);
        this.add(canvas);

        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                // System.out.println("TIMER RAN");
                long start = System.nanoTime();

                for (int i = 0; i < N; i++) {

                    canvas.boids[i].move();
                    // canvas.boids[i].setColor(Color.red);

                    // QuadTree<Boid> qTree = new QuadTree<Boid>(canvas.boids);

                    // QuadTreeYT<Boid> quadTreeYT = new QuadTreeYT<>();

                    // for (Boid b : canvas.boids)
                    // quadTreeYT.insert(b);

                    // Iterable<Boid> neighbourBoids =
                    // quadTreeYT.rangeSearch(canvas.boids[i].getBoidRect());
                    // // System.out.println("BOID RECT=" + canvas.boids[i].getBoidRect());
                    // ArrayList<Boid> neighbourBoidsArray = new ArrayList<>();
                    // // // // int count = 0;
                    // for (Boid b : neighbourBoids) {
                    // neighbourBoidsArray.add(b);
                    // // count++;
                    // }
                    // Boid[] array = neighbourBoidsArray.toArray(new
                    // Boid[neighbourBoidsArray.size()]);
                    // for (Boid b : array) {
                    // if (b != canvas.boids[i])
                    // b.setColor(Color.GREEN);

                    // }
                    // System.out.println("CURR POINT=" + canvas.boids[i]);
                    // System.out.println("ARRAY SIZE=" + (array.length));
                    // canvas.repaint();
                    // System.out.println("SIZE OF ITERABEL=" + sizeOfIterable + " " + "COUNT=" +
                    // (count));

                    // canvas.repaint();

                    canvas.boids[i].separation(canvas.boids);
                    // canvas.boids[i].separation(array);
                    // canvas.repaint();
                    // }
                    canvas.boids[i].alignment(canvas.boids);
                    // canvas.boids[i].alignment(array);
                    // canvas.repaint();
                    canvas.boids[i].cohesion(canvas.boids);
                    // canvas.boids[i].cohesion(array);
                    // canvas.repaint();
                    canvas.repaint();

                    // System.out.println(timeElapsed);
                    // try {
                    // Thread.sleep(10000);
                    // } catch (InterruptedException e) {

                    // e.printStackTrace();
                    // }
                    // // qTree = null;
                    // canvas.boids[i].setColor(Color.WHITE);
                    // for (Boid b : array) {
                    // b.setColor(Color.WHITE);

                    // }
                    // qTree = null;
                }
                // long finish = System.nanoTime();
                // long timeElapsed = finish - start;
                // System.out.println(timeElapsed);
            }
        };

        timer.schedule(task, 5, 20);

        // this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setVisible(true);
        this.setLayout(new FlowLayout());
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

    }

    // public void start() {
    // while (true) {
    // for (int i = 0; i < N; i++) {
    // // System.out.println("IN");

    // canvas.boids[i].move();
    // System.out.println(canvas.boids[i]);

    // // canvas.repaint();
    // // boids[i].alignment(boids);
    // // canvas.repaint();
    // // boids[i].cohesion(boids);
    // // canvas.repaint();

    // for (int j = 0; j < N; j++)
    // canvas.boids[i].separation(canvas.boids[j]);
    // // // canvas.repaint();
    // try {
    // Thread.sleep(1000);
    // } catch (InterruptedException e) {

    // e.printStackTrace();
    // }
    // }
    // canvas.repaint();

    // }

    // }

    public static void main(String[] args) {
        Frame frame = new Frame(1000);
        // frame.start();
    }

}
