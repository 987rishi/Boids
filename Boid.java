package Boids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;

import DS_IMPLEMENTATION.QuadTreeInterface;

public class Boid implements QuadTreeInterface<Boid> {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double visibleRadius;
    private double protectedRadius;
    private double angle;
    private double MAX_SPEED = 0.04;
    private double MIN_SPEED = 0.008;
    private double AVOID_FACTOR = 0.8;// FOR COLLISION RESOLUTION
    private double MATCHING_FACTOR = 0.1;// FOR ALIGNMENT
    private double STEERING_FACTOR = 0.005;// FOR STEERING TOWARDS COM
    private double TURN_FACTOR = 0.0008;
    private double OUTER_FRAME_WIDTH = 2;
    private double INNER_FRAME_RIGHT_MARGIN = 1;
    private double INNER_FRAME_LEFT_MARGIN = 0;
    private double INNER_FRAME_TOP_MARGIN = 0;
    private double INNER_FRAME_BOTTOM_MARGIN = 1;
    private Color color;

    public Boid() {
        this.color = Color.WHITE;
        this.x = Math.random();
        this.y = Math.random();
        this.vx = 0.008;//
        this.vy = 0.004;//
        // this.vx = Math.random();
        // this.vy = Math.random();

        this.protectedRadius = 0.004;
        this.visibleRadius = 0.05;
        // this.AVOID_FACTOR = 0.8;//
        // this.STEERING_FACTOR = 0.005;
        // this.MATCHING_FACTOR = 0.1;
        // this.TURN_FACTOR = 0.0008;//

    }

    public Boid(double x, double y) {
        this();
        this.x = x;
        this.y = y;
    }

    public Boid(Boid b) {
        x = b.x;
        y = b.y;
        vx = b.vx;
        vy = b.vy;
        visibleRadius = b.visibleRadius;
        protectedRadius = b.protectedRadius;
        angle = b.angle;
        AVOID_FACTOR = b.AVOID_FACTOR;// FOR COLLISION RESOLUTION
        MATCHING_FACTOR = b.MATCHING_FACTOR;// FOR ALIGNMENT
        STEERING_FACTOR = b.STEERING_FACTOR;// FOR STEERING TOWARDS COM
        TURN_FACTOR = b.TURN_FACTOR;
        color = b.color;

    }

    public Point2D.Double boidToPoint() {
        return new Point2D.Double(this.x, this.y);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Rectangle2D.Double getBoidRect() {
        return new Rectangle2D.Double(this.x - this.visibleRadius, this.y - this.visibleRadius, 2 * this.visibleRadius,
                2 * this.visibleRadius);
    }

    private double euclideanDistSq(Boid thisB, Boid thatB) {
        if (thisB == null || thatB == null)
            throw new IllegalArgumentException("NULL ARGUMENTS");
        double XDistSq = Math.pow(Math.abs(thisB.x - thatB.x), 2);
        double YDistSq = Math.pow(Math.abs(thisB.y - thatB.y), 2);
        double euclidDistSq = XDistSq + YDistSq;
        assert euclidDistSq > 0 : "Euclid dist is negative";
        return euclidDistSq;

    }

    /* PREVENTS COLLISION OF 2 BOIDS GRACEFULLY */
    // public void separation(Boid that) {
    // if (that == null)
    // throw new IllegalArgumentException("NULL ARGUMENT");
    // if (euclideanDistSq(this, that) > Math.pow(this.protectedRadius, 2))
    // return;
    // this.vx += (this.x - that.x) * AVOID_FACTOR;
    // this.vy += (this.y - that.y) * AVOID_FACTOR;

    // }
    public void separation(Boid[] that) {
        if (that == null)
            throw new IllegalArgumentException("NULL ARGUMENT");
        for (int i = 0; i < that.length; i++) {
            if (euclideanDistSq(this, that[i]) > Math.pow(this.protectedRadius, 2))
                continue;
            this.vx += (this.x - that[i].x) * AVOID_FACTOR;
            this.vy += (this.y - that[i].y) * AVOID_FACTOR;
        }

    }

    /* ALIGNS THE BOID TO MATCH WITH THE AVG VELOCITY OF THE NEIGHBOURING BOIDS */
    public void alignment(Boid[] that) {
        if (that == null)
            throw new IllegalArgumentException("NULL ARGUMENT");
        double neighbouringVXSum = 0;
        double neighbouringVYSum = 0;
        int neighbouringBoids = 0;
        for (int i = 0; i < that.length; i++) {
            // System.out.println("EUCLID DIST" + euclideanDistSq(this, that[i]));

            if (euclideanDistSq(this, that[i]) > this.visibleRadius * this.visibleRadius)
                continue;
            else {

                neighbouringVXSum += that[i].vx;
                neighbouringVYSum += that[i].vy;
                neighbouringBoids++;
            }
        }
        if (neighbouringBoids <= 0)
            return;
        double neighVxAvg = neighbouringVXSum / neighbouringBoids;
        double neighVyAvg = neighbouringVYSum / neighbouringBoids;
        this.vx += (neighVxAvg - this.vx) * MATCHING_FACTOR;
        this.vy += (neighVyAvg - this.vy) * MATCHING_FACTOR;
    }

    /* STEER THE BOID TOWARDS THE CENTER OF MASS OF NEIGHBOURS GRACEFULLY */
    public void cohesion(Boid[] that) {
        if (that == null)
            throw new IllegalArgumentException("NULL ARGUMENT");
        double xDistSum = 0;
        double yDistSum = 0;
        int neighbouringBoids = 0;
        for (int i = 0; i < that.length; i++) {
            if (euclideanDistSq(this, that[i]) > this.visibleRadius * this.visibleRadius)
                continue;
            xDistSum += that[i].x;
            yDistSum += that[i].y;
            neighbouringBoids++;

        }
        if (neighbouringBoids <= 0)
            return;
        double avgX = xDistSum / neighbouringBoids;
        double avgY = yDistSum / neighbouringBoids;
        this.vx += (avgX - this.x) * STEERING_FACTOR;
        this.vy += (avgY - this.y) * STEERING_FACTOR;

    }

    public void move() {

        // System.out.println("in move");
        // if (dt <= 0)
        // throw new IllegalArgumentException("dt is negative");

        // if (vx > MAX_SPEED)
        // vx -= SPEED_FACTOR;
        // if (vy > MAX_SPEED)
        // vy -= SPEED_FACTOR;
        // if (vx < MIN_SPEED)
        // vx += SPEED_FACTOR;
        // if (vy < MIN_SPEED)
        // vy += SPEED_FACTOR;

        double speed = Math.sqrt(vx * vx + vy * vy);

        if (speed > MAX_SPEED) {
            vx = (vx / speed) * MAX_SPEED;
            vy = (vy / speed) * MIN_SPEED;
        }
        if (speed < MIN_SPEED) {
            vx = (vx / speed) * MIN_SPEED;
            vy = (vy / speed) * MIN_SPEED;
        }
        if (x + vx >= INNER_FRAME_RIGHT_MARGIN)
            vx -= TURN_FACTOR;
        if (x + vx <= INNER_FRAME_LEFT_MARGIN)
            vx += TURN_FACTOR;
        if (y + vy >= INNER_FRAME_BOTTOM_MARGIN)
            vy -= TURN_FACTOR;
        if (y + vy <= INNER_FRAME_TOP_MARGIN)
            vy += TURN_FACTOR;

        this.x += this.vx;
        this.y += this.vy;
        // drawing();

    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("X=" + this.x + " " + "Y=" + this.y);
        output.append("\n");
        return output.toString();
    }

    public void drawing(Graphics g) {
        // Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);
        // if(this.vx>0)
        // System.out.println("IN BOID DRAW");
        // System.out.println("x" + this.x);
        // System.out.println("y" + this.y);
        // System.out.println("VX=" + vx);
        // System.out.println("VY=" + vy);
        // System.out.println("w" + this.);
        // System.out.println("h" + this.);
        // g.drawOval(
        // Canvas.boxXtoCanvasX(this.x - visibleRadius),
        // Canvas.boxYtoCanvasY(this.y - visibleRadius),
        // 2 * Canvas.boxXtoCanvasX(visibleRadius),
        // 2 * Canvas.boxYtoCanvasY(visibleRadius));
        // g.drawRect(
        // Canvas.boxXtoCanvasX(this.getBoidRect().x),
        // Canvas.boxYtoCanvasY(this.getBoidRect().y),
        // Canvas.boxXtoCanvasX(this.getBoidRect().width),
        // Canvas.boxYtoCanvasY(this.getBoidRect().height));
        g.fillOval(
                100 + Canvas.boxXtoCanvasX(this.x - this.protectedRadius),
                100 + Canvas.boxYtoCanvasY(this.y - this.protectedRadius),
                Canvas.boxXtoCanvasX(2 * this.protectedRadius),
                Canvas.boxYtoCanvasY(2 * this.protectedRadius));

    }

    @Override
    public int decideWhichQuad(Boid object) {
        if (object == null)
            throw new IllegalArgumentException("NULL ARGUMENT");
        Point2D.Double that = object.boidToPoint();
        Point2D.Double thisObj = this.boidToPoint();

        if (thisObj.x <= that.x && thisObj.y >= that.y)
            return 1;
        if (thisObj.x >= that.x && thisObj.y >= that.y)
            return 2;
        if (thisObj.x >= that.x && thisObj.y <= that.y)
            return 3;
        if (thisObj.x <= that.x && thisObj.y <= that.y)
            return 4;
        return 5;// WILL NEVER BE EXECUTED BUT FOR COMPILER SATISFACTION

    }

    @Override
    public Point2D.Double getPoint() {
        return this.boidToPoint();

    }

}
