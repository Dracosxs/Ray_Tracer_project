package raytracer.forme;

import geometrie.Point;
import raytracer.Intersection;
import raytracer.Ray;

import java.util.Optional;

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

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        return Optional.empty();
    }
}
