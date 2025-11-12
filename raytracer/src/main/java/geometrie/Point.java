package geometrie;

/**
 * Représente un point en 3D.
 * Sert à positionner des objets ou des caméras dans la scène.
 */
public class Point extends AbstractVec3 {

    /** Constructeur par défaut (point à l’origine). */
    public Point() {
        super(0, 0, 0);
    }

    /** Constructeur complet. */
    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    /** Redéfinition de la méthode de création pour le polymorphisme. */
    @Override
    protected AbstractVec3 create(double x, double y, double z) {
        return new Point(x, y, z);
    }

    /** Soustraction entre deux points → renvoie un vecteur (direction entre eux). */
    public Vector sub(Point p) {
        return new Vector(x - p.x, y - p.y, z - p.z);
    }

    /** Addition d’un vecteur à un point → renvoie un nouveau point. */
    public Point add(Vector v) {
        return new Point(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    /** Distance euclidienne entre deux points. */
    public double distance(Point p) {
        return this.sub(p).length();
    }

    /** Conversion en vecteur depuis l’origine. */
    public Vector toVector() {
        return new Vector(x, y, z);
    }

    /** Point moyen entre ce point et un autre. */
    public Point midpoint(Point p) {
        return new Point((x + p.x) / 2.0, (y + p.y) / 2.0, (z + p.z) / 2.0);
    }
}