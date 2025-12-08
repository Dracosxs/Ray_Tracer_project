package raytracer;

import geometrie.Color;
import geometrie.Point;
import geometrie.Vector;

public abstract class Light {
    protected final Color color;

    protected Light(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Calcule le vecteur direction L allant du point P vers la source de lumière.
     * @param p Le point de la scène qui reçoit la lumière.
     * @return Le vecteur L normalisé.
     */
    public abstract Vector getL(Point p);
}
