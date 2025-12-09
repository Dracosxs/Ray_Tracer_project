package raytracer;

import geometrie.Point;
import geometrie.Vector;

/**
 * Représente la caméra de la scène, définie par une position, une cible,
 * un vecteur « up » et un champ de vision. 
 *
 * Fournit les paramètres nécessaires au calcul des rayons primaires
 * lors du rendu.
 */


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
