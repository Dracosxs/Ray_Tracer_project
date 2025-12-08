package tool;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Outil de comparaison d'images basé sur le Jalon 0.
 */
public class ImageComparator {

    /**
     * Compte le nombre de pixels différents entre deux images.
     * @param img1 Première image
     * @param img2 Deuxième image (référence)
     * @return Le nombre de pixels différents (-1 si tailles différentes)
     */
    public static long countDifferentPixels(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            System.err.println("Erreur : Les dimensions des images sont différentes.");
            return -1;
        }

        long diffCount = 0;
        int width = img1.getWidth();
        int height = img1.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    diffCount++;
                }
            }
        }
        return diffCount;
    }

    /**
     * Génère une image différentielle (noire si identique, couleur si différent).
     */
    public static BufferedImage createDiffImage(BufferedImage img1, BufferedImage img2) {
        int width = Math.min(img1.getWidth(), img2.getWidth());
        int height = Math.min(img1.getHeight(), img2.getHeight());
        BufferedImage diffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                if (rgb1 == rgb2) {
                    diffImg.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    // Calcul de la différence de couleur
                    int r1 = (rgb1 >> 16) & 0xFF;
                    int g1 = (rgb1 >> 8) & 0xFF;
                    int b1 = rgb1 & 0xFF;

                    int r2 = (rgb2 >> 16) & 0xFF;
                    int g2 = (rgb2 >> 8) & 0xFF;
                    int b2 = rgb2 & 0xFF;

                    int rDiff = Math.abs(r1 - r2);
                    int gDiff = Math.abs(g1 - g2);
                    int bDiff = Math.abs(b1 - b2);

                    // On recompose la couleur de différence
                    int diffColor = (rDiff << 16) | (gDiff << 8) | bDiff;
                    diffImg.setRGB(x, y, diffColor);
                }
            }
        }
        return diffImg;
    }
}
