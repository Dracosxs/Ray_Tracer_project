package raytracer;

import geometrie.Point;
import geometrie.Vector;

public class Camera {
    private final Point lookFrom;
    private final Point lookAt;
    private final Vector up;
    private final double fov;

    public Camera(Point lookFrom, Point lookAt, Vector up, double fov) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
    }

    public Point getLookFrom() { return lookFrom; }
    public Point getLookAt() { return lookAt; }
    public Vector getUp() { return up; }
    public double getFov() { return fov; }
}
