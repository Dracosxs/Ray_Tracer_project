package raytracer;

import geometrie.Color;
import geometrie.Vector;

public class DirectionalLight extends Light {
    private final Vector direction;

    public DirectionalLight(Vector direction, Color color) {
        super(color);
        this.direction = (Vector) direction.normalize();
    }

    public Vector getDirection() { return direction; }
}
