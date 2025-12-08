package raytracer;

import geometrie.Color;
import geometrie.Point;
import geometrie.Vector;

public class DirectionalLight extends Light {
    private final Vector direction;

    public DirectionalLight(Vector direction, Color color) {
        super(color);
        // On normalise dès la construction pour ne pas le refaire à chaque pixel
        this.direction = (Vector) direction.normalize();
    }

    public Vector getDirection() { return direction; }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
