package Boids;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Canvas extends JPanel {
    public static final int CANVAS_INNER_WIDTH = 400;
    public static final int CANVAS_INNER_HEIGHT = 400;
    public static final int CANVAS_OUTER_WIDTH = 600;
    public static final int CANVAS_OUTER_HEIGHT = 600;
    public Boid[] boids;

    Canvas(Boid[] boids) {
        this.boids = new Boid[boids.length];
        for (int i = 0; i < this.boids.length; i++) {
            this.boids[i] = new Boid();
        }

        // for (int i = 0; i < boids.length; i++) {
        // this.add()
        // }

        this.setPreferredSize(new Dimension(CANVAS_OUTER_WIDTH, CANVAS_OUTER_HEIGHT));
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setOpaque(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(100, 100, CANVAS_INNER_WIDTH, CANVAS_INNER_HEIGHT);
        // System.out.println("bOIDS LENGTH IN CANVAS " + boids.length);
        for (int i = 0; i < boids.length; i++) {
            // System.out.println("in compdarw");
            this.boids[i].drawing(g);
        }

    }

    static int boxXtoCanvasX(double x) {
        return (int) Math.ceil((1 * CANVAS_INNER_WIDTH * x));
    }

    static int boxYtoCanvasY(double y) {
        return (int) Math.ceil((1 * CANVAS_INNER_HEIGHT * y));
    }

    static double canvasYToboxY(int y) {
        return (double) ((1.0 / CANVAS_INNER_HEIGHT) * y);
    }

    static double canvasXToboxX(int x) {
        return (double) ((1.0 / CANVAS_INNER_WIDTH) * x);
    }

    public static void main(String[] args) {
        System.out.println(Canvas.boxXtoCanvasX(0.8358051521733435));
    }

}
