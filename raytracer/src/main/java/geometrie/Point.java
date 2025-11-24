package geometrie;

/**
 * Représente une position fixe dans l'espace 3D.
 */
public class Point extends AbstractVec3 {

    public Point() {
        super(0, 0, 0);
    }

    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    @Override
    protected AbstractVec3 create(double x, double y, double z) {
        return new Point(x, y, z);
    }

    /**
     * Soustraire deux points donne un Vecteur (la flèche qui va de point2 à this).
     */
    public Vector sub(Point otherPoint) {
        return new Vector(this.x - otherPoint.x, this.y - otherPoint.y, this.z - otherPoint.z);
    }

    /**
     * Ajouter un vecteur à un point déplace ce point et donne un nouveau Point.
     */
    public Point add(Vector vectorToAdd) {
        return new Point(this.x + vectorToAdd.getX(), this.y + vectorToAdd.getY(), this.z + vectorToAdd.getZ());
    }

    public double distance(Point otherPoint) {
        return this.sub(otherPoint).length();
    }

    public Vector toVector() {
        return new Vector(x, y, z);
    }

    public Point midpoint(Point otherPoint) {
        return new Point((this.x + otherPoint.x) / 2.0, (this.y + otherPoint.y) / 2.0, (this.z + otherPoint.z) / 2.0);
    }
}