/**
 * Représente une couleur avec composantes rouge, verte et bleue,
 * chacune comprise entre 0 et 1.
 */
public class Color extends AbstractVec3 {

    /** Constructeur par défaut : noir (0,0,0). */
    public Color() {
        super(0, 0, 0);
    }

    /** Constructeur avec clamp automatique entre 0 et 1. */
    public Color(double r, double g, double b) {
        super(clamp(r), clamp(g), clamp(b));
    }

    /** Redéfinition de la méthode de création. */
    @Override
    protected AbstractVec3 create(double r, double g, double b) {
        return new Color(r, g, b);
    }

    /** Retourne la composante rouge. */
    public double getR() { return x; }

    /** Retourne la composante verte. */
    public double getG() { return y; }

    /** Retourne la composante bleue. */
    public double getB() { return z; }

    /** Multiplie cette couleur par un scalaire, avec clamp. */
    @Override
    public Color mul(double scalar) {
        return new Color(x * scalar, y * scalar, z * scalar);
    }

    /** Produit de Schur (couleur * couleur). */
    public Color mul(Color c) {
        return new Color(x * c.x, y * c.y, z * c.z);
    }

    /** Additionne deux couleurs, avec clamp. */
    @Override
    public Color add(AbstractVec3 other) {
        return new Color(x + other.x, y + other.y, z + other.z);
    }

    /** Convertit la couleur [0,1] en valeur RGB 24 bits. */
    public int toRGB() {
        int red = (int) Math.round(x * 255);
        int green = (int) Math.round(y * 255);
        int blue = (int) Math.round(z * 255);
        return ((red & 0xff) << 16)
                + ((green & 0xff) << 8)
                + (blue & 0xff);
    }

    /** Applique une limite entre 0 et 1 à une composante. */
    private static double clamp(double value) {
        if (value < 0) return 0;
        if (value > 1) return 1;
        return value;
    }

    @Override
    public String toString() {
        return String.format("Color(%.3f, %.3f, %.3f)", x, y, z);
    }
}