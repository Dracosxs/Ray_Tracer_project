package raytracer;

import geometrie.Point;
import geometrie.Vector;

/**
 * Représente un rayon dans l’espace 3D, défini par une origine et 
 * une direction normalisée.
 *
 * Utilisé pour le lancer de rayons dans le moteur de ray tracing afin 
 * de calculer les intersections et l’éclairage.
 */


public class Ray {

    private final Point origin;
    private final Vector direction;

    public Ray(Point origin, Vector direction) {
        this.origin = origin;
        this.direction = (Vector) direction.normalize();
    }

    public Point getOrigin() {
        return origin;
    }

    public Vector getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Ray(origin=" + origin + ", dir=" + direction + ")";
    }
}
