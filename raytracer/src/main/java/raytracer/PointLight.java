package raytracer;

import geometrie.Color;
import geometrie.Point;
import geometrie.Vector;

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
