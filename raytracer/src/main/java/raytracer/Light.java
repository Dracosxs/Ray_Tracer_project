package raytracer;

import geometrie.Color;

public abstract class Light {
    protected final Color color;

    protected Light(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
