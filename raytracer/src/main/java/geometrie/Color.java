package geometrie;

/**
 * Représente une couleur RGB normalisée (valeurs entre 0.0 et 1.0).
 *
 * Cette classe étend AbstractVec3 pour offrir des opérations spécifiques 
 * aux couleurs : clamp automatique des composantes, mélange (produit 
 * composante par composante), conversion vers un format RGB 0–255, etc. 
 * Elle permet de manipuler les couleurs du moteur de rendu de façon 
 * cohérente et sécurisée.
 */

public class Color extends AbstractVec3 {

    public Color() {
        super(0, 0, 0);
    }

    /**
     * Crée une couleur en limitant (clamping) automatiquement les valeurs entre 0 et 1.
     */
    public Color(double red, double green, double blue) {
        super(clamp(red), clamp(green), clamp(blue));
    }

    @Override
    protected AbstractVec3 create(double red, double green, double blue) {
        return new Color(red, green, blue);
    }

    // Accesseurs sémantiques (plus clairs que getX/getY/getZ pour une couleur)
    public double getRed() { return x; }
    public double getGreen() { return y; }
    public double getBlue() { return z; }

    @Override
    public Color mul(double scalarFactor) {
        return new Color(x * scalarFactor, y * scalarFactor, z * scalarFactor);
    }

    /**
     * Mélange deux couleurs (produit composante par composante).
     * Exemple : Une lumière blanche (1,1,1) sur un mur rouge (1,0,0) donne du rouge.
     */
    public Color mul(Color otherColor) {
        return new Color(x * otherColor.x, y * otherColor.y, z * otherColor.z);
    }

    @Override
    public Color add(AbstractVec3 otherColor) {
        return new Color(x + otherColor.x, y + otherColor.y, z + otherColor.z);
    }

    /**
     * Convertit la couleur (0.0-1.0) en entier RGB standard (0-255) pour l'écriture d'image.
     */
    public int toRGB() {
        int redInt = (int) Math.round(x * 255);
        int greenInt = (int) Math.round(y * 255);
        int blueInt = (int) Math.round(z * 255);

        // Bitwise operations pour assembler l'entier final
        return ((redInt & 0xff) << 16) | ((greenInt & 0xff) << 8) | (blueInt & 0xff);
    }

    private static double clamp(double value) {
        return Math.max(0, Math.min(1, value));
    }

    @Override
    public String toString() {
        return String.format("Color(%.3f, %.3f, %.3f)", x, y, z);
    }

}
