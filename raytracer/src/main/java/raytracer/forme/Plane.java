package raytracer.forme;

import geometrie.Point;
import geometrie.Vector;

public class Plane extends Shape {
    private final Point point;
    private final Vector normal;

    public Plane(Point point, Vector normal) {
        this.point = point;
        this.normal = (Vector) normal.normalize();
    }

    public Point getPoint() { return point; }
    public Vector getNormal() { return normal; }
}
