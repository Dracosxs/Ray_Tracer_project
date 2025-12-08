package raytracer.forme;

import geometrie.Color;
import geometrie.Point;
import geometrie.Vector;
import raytracer.Intersection;
import raytracer.Ray;

import java.util.Optional;

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