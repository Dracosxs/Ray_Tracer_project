package raytracer;

import geometrie.Point;
import geometrie.Vector;

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
