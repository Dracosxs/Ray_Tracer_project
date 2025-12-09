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
    private final double distance;
    private final Point intersectionPoint;
    private final Shape shape;

    private final Vector normal;



    public Intersection(double distance, Point intersectionPoint, Shape shape, Vector normal) {
        this.distance = distance;
        this.intersectionPoint = intersectionPoint;
        this.shape = shape;
        this.normal = normal;

    }

    public double getDistance() {
        return distance;
    }

    public Point getPosition() {
        return intersectionPoint;
    }

    public Shape getShape() {
        return shape;
    }

    public Vector getNormal() {
        return normal;
    }


    /**
     * Calcule la couleur (Diffuse + Spéculaire)
     * @param light La lumière
     * @param viewDirection Le vecteur allant du point d'impact vers la caméra (V)
     *                      @return La couleur résultante au point d'intersection
     */
    public Color computeColor(Light light, Vector viewDirection) {
        Vector directionToLight = light.getL(this.intersectionPoint); // L (vers la lumière)

        // Lambert
        double dotProductNormalLight = this.normal.dotProduct(directionToLight);
        double diffuseIntensity = Math.max(0.0, dotProductNormalLight);
        Color diffuseTerm = light.getColor()
                .schurProduct(shape.getDiffuse())
                .multiply(diffuseIntensity);

        // Blinn-Phong
        Color specularTerm = new Color(0, 0, 0); // Noir par défaut (si pas de reflet)


        // On ne calcule le reflet spéculaire que si :
        //La lumière touche la surface (dotProduct > 0)
        //L'objet est brillant (shininess > 0)
        if (dotProductNormalLight > 0 && shape.getShininess() > 0) {

            // Calcul du vecteur médian (Halfway vector H)
            // H est la moyenne entre la direction de la lumière (L) et la direction du regard (V)
            // H = (L + V) / ||L + V||
            Vector halfwayVector = (Vector) directionToLight.addVector(viewDirection).normalize();

            // Produit scalaire entre la Normale et le vecteur Médian (N.H)
            double dotProductNormalHalfway = this.normal.dotProduct(halfwayVector);

            // Calcul du facteur spéculaire : (N.H)^shininess
            double specularFactor = Math.pow(Math.max(0.0, dotProductNormalHalfway), shape.getShininess());

            // Couleur Spéculaire = Couleur Lumière * Couleur Spéculaire Objet * Facteur
            specularTerm = light.getColor()
                    .schurProduct(shape.getSpecular())
                    .multiply(specularFactor);
        }

        // On additionne la lumière diffuse (couleur de l'objet) et la lumière spéculaire (reflet brillant)
        return diffuseTerm.addVector(specularTerm);    }
}