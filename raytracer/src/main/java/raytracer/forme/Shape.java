package raytracer.forme;

import geometrie.Color;

public abstract class Shape {
    protected Color diffuse = new Color();
    protected Color specular = new Color();

    public void setDiffuse(Color diffuse) { this.diffuse = diffuse; }
    public void setSpecular(Color specular) { this.specular = specular; }

    public Color getDiffuse() { return diffuse; }
    public Color getSpecular() { return specular; }
}
