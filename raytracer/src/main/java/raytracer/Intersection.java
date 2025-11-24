package raytracer;

import geometrie.Point;
import raytracer.forme.Shape;

/**
 * Représente une intersection entre un rayon et une forme.
 * Stocke la distance t et le point d'impact p.
 */
public class Intersection {
    private final double t;
    private final Point position;
    private final Shape shape;

    public Intersection(double t, Point position, Shape shape) {
        this.t = t;
        this.position = position;
        this.shape = shape;
    }

    public double getTime() {
        return t;
    }

    public Point getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }
}