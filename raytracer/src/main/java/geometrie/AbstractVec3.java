package geometrie;

/**
 * Classe abstraite de base pour tous les objets à 3 composantes (Points, Vecteurs, Couleurs).
 * Gère les opérations mathématiques communes.
 */
public abstract class AbstractVec3 {

    protected double x;
    protected double y;
    protected double z;

    protected static final double EPSILON = 1e-9;

    /** Constructeur par défaut (valeurs nulles). */
    protected AbstractVec3() {
        this(0, 0, 0);
    }

    /** Constructeur avec coordonnées explicites. */
    protected AbstractVec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Additionne ce vecteur avec un autre.
     * @param vectorToAdd Le vecteur à ajouter.
     * @return Un nouveau vecteur résultant de la somme.
     */
    public AbstractVec3 add(AbstractVec3 vectorToAdd) {
        return create(
                this.x + vectorToAdd.x,
                this.y + vectorToAdd.y,
                this.z + vectorToAdd.z
        );
    }

    /**
     * Soustrait un autre vecteur à ce vecteur (this - vectorToSubtract).
     * @param vectorToSubtract Le vecteur à soustraire.
     * @return Un nouveau vecteur résultant de la soustraction.
     */
    public AbstractVec3 sub(AbstractVec3 vectorToSubtract) {
        return create(
                this.x - vectorToSubtract.x,
                this.y - vectorToSubtract.y,
                this.z - vectorToSubtract.z
        );
    }

    /**
     * Multiplie ce vecteur par un facteur scalaire.
     * @param scalarFactor Le facteur de multiplication.
     * @return Un nouveau vecteur mis à l'échelle.
     */
    public AbstractVec3 mul(double scalarFactor) {
        return create(
                this.x * scalarFactor,
                this.y * scalarFactor,
                this.z * scalarFactor
        );
    }

    /**
     * Produit de Schur (multiplication composante par composante).
     * @param otherVector L'autre vecteur.
     * @return Un nouveau vecteur où x = x1*x2, etc.
     */
    public AbstractVec3 schur(AbstractVec3 otherVector) {
        return create(
                this.x * otherVector.x,
                this.y * otherVector.y,
                this.z * otherVector.z
        );
    }

    /**
     * Produit scalaire (Dot Product).
     * Représente la projection d'un vecteur sur un autre.
     * @param otherVector Le vecteur avec lequel faire le produit.
     * @return Une valeur scalaire (double).
     */
    public double dot(AbstractVec3 otherVector) {
        return (this.x * otherVector.x) + (this.y * otherVector.y) + (this.z * otherVector.z);
    }

    /**
     * Produit vectoriel (Cross Product).
     * Crée un vecteur perpendiculaire aux deux vecteurs d'origine.
     * @param otherVector Le second vecteur du produit.
     * @return Le vecteur normal au plan formé par this et otherVector.
     */
    public AbstractVec3 cross(AbstractVec3 otherVector) {
        double crossProductX = (this.y * otherVector.z) - (this.z * otherVector.y);
        double crossProductY = (this.z * otherVector.x) - (this.x * otherVector.z);
        double crossProductZ = (this.x * otherVector.y) - (this.y * otherVector.x);
        return create(crossProductX, crossProductY, crossProductZ);
    }

    /**
     * Calcule la norme (longueur) du vecteur.
     */
    public double length() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    /**
     * Normalise ce vecteur (le rend de longueur 1) en gardant sa direction.
     * @return Un nouveau vecteur unitaire.
     */
    public AbstractVec3 normalize() {
        double currentLength = length();
        if (currentLength < EPSILON) {
            return create(0, 0, 0);
        }
        return create(x / currentLength, y / currentLength, z / currentLength);
    }

    /** Méthode factory pour permettre le polymorphisme dans les calculs. */
    protected abstract AbstractVec3 create(double x, double y, double z);

    // Accesseurs
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractVec3 other = (AbstractVec3) obj;
        return almostEqual(x, other.x)
                && almostEqual(y, other.y)
                && almostEqual(z, other.z);
    }

    protected boolean almostEqual(double value1, double value2) {
        return Math.abs(value1 - value2) < EPSILON;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f)", x, y, z);
    }
}