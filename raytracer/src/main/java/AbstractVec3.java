public abstract class AbstractVec3 {

    protected double x;
    protected double y;
    protected double z;

    protected static final double EPSILON = 1e-9;

    /** Constructeur par défaut (valeurs nulles). */
    protected AbstractVec3() {
        this(0, 0, 0);
    }

    /** Constructeur complet. */
    protected AbstractVec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /** Additionne ce vecteur avec un autre. */
    public AbstractVec3 add(AbstractVec3 other) {
        return create(x + other.x, y + other.y, z + other.z);
    }

    /** Soustrait un autre vecteur à ce vecteur. */
    public AbstractVec3 sub(AbstractVec3 other) {
        return create(x - other.x, y - other.y, z - other.z);
    }

    /** Multiplie ce vecteur par un scalaire. */
    public AbstractVec3 mul(double scalar) {
        return create(x * scalar, y * scalar, z * scalar);
    }

    /** Produit de Schur (multiplication composante par composante). */
    public AbstractVec3 schur(AbstractVec3 other) {
        return create(x * other.x, y * other.y, z * other.z);
    }

    /** Produit scalaire. */
    public double dot(AbstractVec3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /** Produit vectoriel. */
    public AbstractVec3 cross(AbstractVec3 other) {
        double cx = y * other.z - z * other.y;
        double cy = z * other.x - x * other.z;
        double cz = x * other.y - y * other.x;
        return create(cx, cy, cz);
    }

    /** Norme (longueur) du vecteur. */
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /** Normalise ce vecteur (longueur 1). */
    public AbstractVec3 normalize() {
        double len = length();
        if (len < EPSILON) return create(0, 0, 0);
        return create(x / len, y / len, z / len);
    }

    /** Crée une nouvelle instance du bon type (implémenté dans les sous-classes). */
    protected abstract AbstractVec3 create(double x, double y, double z);

    /** Accesseurs */
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    /** Vérifie l’égalité avec tolérance. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractVec3 other = (AbstractVec3) obj;
        return almostEqual(x, other.x)
                && almostEqual(y, other.y)
                && almostEqual(z, other.z);
    }

    protected boolean almostEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f)", x, y, z);
    }
}