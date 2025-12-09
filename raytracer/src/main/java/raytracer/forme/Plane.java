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

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        Point rayOrigin = ray.getOrigin();       // o
        Vector rayDirection = ray.getDirection(); // d

        // Calcul du dénominateur : d . n
        double denominator = rayDirection.dotProduct(normal);

        // Si le dénominateur est proche de 0, le rayon est parallèle au plan -> Pas d'intersection
        if (Math.abs(denominator) < 1e-6) {
            return Optional.empty();
        }

        // Calcul de t = ((q - o) . n) / (d . n)
        Vector originToPlane = (Vector) point.subtract(rayOrigin); // (q - o)
        double t = originToPlane.dotProduct(normal) / denominator;

        // Si t < 0, l'intersection est derrière la caméra
        if (t < 1e-6) {
            return Optional.empty();
        }

        // Création de l'intersection
        Point hitPosition = (Point) rayOrigin.addVector(rayDirection.multiply(t));

        return Optional.of(new Intersection(t, hitPosition, this, normal));
    }
}
