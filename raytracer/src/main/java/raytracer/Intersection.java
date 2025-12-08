package raytracer;

import geometrie.Color;
import geometrie.Point;
import geometrie.Vector;
import raytracer.forme.Shape;

/**
 * Représente une intersection entre un rayon et une forme.
 * Stocke la distance t et le point d'impact p.
 */
public class Intersection {
    private final double t;
    private final Point position;
    private final Shape shape;

    private final Vector normal;

    public Intersection(double t, Point position, Shape shape, Vector normal) {
        this.t = t;
        this.position = position;
        this.shape = shape;
        this.normal = normal;
    }



    public double getTime() {
        return t;
    }

    public Point getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }

    public Vector getNormal() {
        return normal;
    }

    /**
     * Calcule la couleur diffusée (Lambert) pour une lumière donnée.
     * Formule : Ld = max(n . L, 0) * lightColor * diffuseColor
     */
    public Color computeColor(Light light) {
        //Récupérer le vecteur L (vers la lumière)
        Vector vecteurL = light.getL(this.position);

        //Calculer le produit scalaire
        double DotDeVecL = this.normal.dot(vecteurL);

        //Appliquer la loi de Lambert (max(0, n.L))
        double intensity = Math.max(0.0, DotDeVecL);

        //Mélanger les couleurs Lumière * Objet * Intensité
        Color lightColor = light.getColor();
        Color objectColor = shape.getDiffuse();

        return lightColor.mul(objectColor).mul(intensity);
    }

}