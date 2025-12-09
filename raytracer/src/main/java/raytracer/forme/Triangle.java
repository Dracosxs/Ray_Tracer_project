package raytracer.forme;

import java.util.Optional;

import geometrie.Point;
import geometrie.Vector;
import raytracer.Intersection;
import raytracer.Ray;

/**
 * Représente un triangle dans l’espace 3D.
 *
 * Défini par ses trois sommets, il fournit une structure pour calculer
 * les intersections avec des rayons et obtenir la normale au point
 * d’impact. Hérite des propriétés de matériau de Shape.
 */


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
    public Vector getNormal(Point p) {
        return null;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        return Optional.empty();
    }
}
