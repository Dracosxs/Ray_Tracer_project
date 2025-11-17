package raytracer;

import geometrie.Color;
import geometrie.Point;

public class PointLight extends Light {
    private final Point position;

    public PointLight(Point position, Color color) {
        super(color);
        this.position = position;
    }

    public Point getPosition() { return position; }
}
