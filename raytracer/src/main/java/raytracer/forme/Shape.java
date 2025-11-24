package raytracer.forme;

import geometrie.Color;
import raytracer.Intersection;
import raytracer.Ray;

import java.util.Optional;

public abstract class Shape {
    protected Color diffuse = new Color();
    protected Color specular = new Color();

    public void setDiffuse(Color diffuse) { this.diffuse = diffuse; }
    public void setSpecular(Color specular) { this.specular = specular; }

    public Color getDiffuse() { return diffuse; }
    public Color getSpecular() { return specular; }

    /**
     * Calcule l'intersection entre un rayon et cette forme.
     * @param ray Le rayon lancé.
     * @return Un Optional contenant l'intersection si elle existe, sinon vide.
     */
    public abstract Optional<Intersection> intersect(Ray ray);
}