package raytracer.forme;

import java.util.Optional;

import geometrie.Color;
import geometrie.Point;
import geometrie.Vector;
import raytracer.Intersection;
import raytracer.Ray;

/**
 * Classe abstraite pour toutes les formes géométriques de la scène.
 *
 * Gère les propriétés de matériau (diffuse, spéculaire, shininess) et
 * définit les méthodes abstraites pour obtenir la normale en un point
 * et calculer l’intersection avec un rayon.
 */


public abstract class Shape {
    protected Color diffuse = new Color();
    protected Color specular = new Color();
    protected double shininess = 0.0;

    public void setDiffuse(Color diffuse) { this.diffuse = diffuse; }
    public void setSpecular(Color specular) { this.specular = specular; }
    public void setShininess(double shininess) { this.shininess = shininess; }

    public Color getDiffuse() { return diffuse; }
    public Color getSpecular() { return specular; }
    public abstract Vector getNormal(Point p);
    public double getShininess() { return shininess; }

    /**
     * Calcule l'intersection entre un rayon et cette forme.
     * @param ray Le rayon lancé.
     * @return Un Optional contenant l'intersection si elle existe, sinon vide.
     */
    public abstract Optional<Intersection> intersect(Ray ray);
}