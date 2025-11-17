package raytracer;

import geometrie.Point;

public class Triangle extends Shape {
    private final Point a, b, c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Point getA() { return a; }
    public Point getB() { return b; }
    public Point getC() { return c; }
}
