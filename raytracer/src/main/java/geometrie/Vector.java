package geometrie;

/**
 * Représente un vecteur 3D, utilisé pour décrire une direction ou un déplacement.
 *
 * Cette classe étend AbstractVec3 et fournit des utilitaires spécifiques
 * aux vecteurs : constantes unitaires, négation, test de nullité, ainsi que
 * des surcharges pour les produits scalaire et vectoriel.
 */

public class Vector extends AbstractVec3 {

    public Vector() {
        super(0, 0, 0);
    }

    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    @Override
    protected AbstractVec3 create(double x, double y, double z) {
        return new Vector(x, y, z);
    }

    public static Vector zero() { return new Vector(0, 0, 0); }
    public static Vector unitX() { return new Vector(1, 0, 0); }
    public static Vector unitY() { return new Vector(0, 1, 0); }
    public static Vector unitZ() { return new Vector(0, 0, 1); }

    public Vector negate() {
        return new Vector(-x, -y, -z);
    }

    public boolean isZero() {
        return length() < EPSILON;
    }

    // Surcharges pour éviter les casts explicites dans le code principal
    public double dot(Vector otherVector) {
        return super.dot(otherVector);
    }

    public Vector cross(Vector otherVector) {
        return (Vector) super.cross(otherVector);
    }
}