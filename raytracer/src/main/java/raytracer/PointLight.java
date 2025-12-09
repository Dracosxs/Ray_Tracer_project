package raytracer;

import geometrie.Color;
import geometrie.Point;
import geometrie.Vector;

/**
 * Lumière ponctuelle définie par une position dans l’espace et une couleur.
 *
 * Fournit le vecteur direction L allant du point éclairé vers la lumière,
 * utilisé pour le calcul de l’éclairage local (diffuse et spéculaire).
 */


public class PointLight extends Light {
    private final Point position;

    public PointLight(Point position, Color color) {
        super(color);
        this.position = position;
    }

    public Point getPosition() { return position; }

    @Override
    public Vector getL(Point p) {
        // L = PositionLumière - Point
        return (Vector) position.subtract(p).normalize();
    }

}
