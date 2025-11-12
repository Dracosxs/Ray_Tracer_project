package geometrie;

/**
 * Représente un vecteur en 3D.
 */
public class Vector extends AbstractVec3 {

    /** Constructeur par défaut (vecteur nul). */
    public Vector() {
        super(0, 0, 0);
    }

    /** Constructeur complet. */
    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    /** Redéfinition de la méthode de création pour le polymorphisme. */
    @Override
    protected AbstractVec3 create(double x, double y, double z) {
        return new Vector(x, y, z);
    }

    /** Retourne un vecteur nul. */
    public static Vector zero() {
        return new Vector(0, 0, 0);
    }

    /** Retourne le vecteur unitaire X. */
    public static Vector unitX() {
        return new Vector(1, 0, 0);
    }

    /** Retourne le vecteur unitaire Y. */
    public static Vector unitY() {
        return new Vector(0, 1, 0);
    }

    /** Retourne le vecteur unitaire Z. */
    public static Vector unitZ() {
        return new Vector(0, 0, 1);
    }

    /** Inverse ce vecteur. */
    public Vector negate() {
        return new Vector(-x, -y, -z);
    }

    /** Vérifie si le vecteur est presque nul. */
    public boolean isZero() {
        return length() < EPSILON;
    }

    /** Produit scalaire spécifique pour Vector. */
    public double dot(Vector v) {
        return super.dot(v);
    }

    /** Produit vectoriel spécifique pour Vector. */
    public Vector cross(Vector v) {
        return (Vector) super.cross(v);
    }
}