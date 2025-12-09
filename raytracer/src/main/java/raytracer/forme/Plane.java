package raytracer.forme;

import java.util.Optional;

import geometrie.Point;
import geometrie.Vector;
import raytracer.Intersection;
import raytracer.Ray;

/**
 * Représente un plan infini dans l’espace 3D.
 *
 * Défini par un point appartenant au plan et une normale.
 * Fournit une structure pour calculer les intersections avec des rayons
 * et obtenir la normale au point d’impact.
 */


public class Plane extends Shape {
    private final Point point;
    private final Vector normal;

    public Plane(Point point, Vector normal) {
        this.point = point;
        this.normal = (Vector) normal.normalize();
    }

    public Point getPoint() { return point; }
    public Vector getNormal() { return normal; }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        return Optional.empty();
    }
}
