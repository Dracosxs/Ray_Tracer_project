package imgcompare;

import java.awt.image.BufferedImage;

public class imgcompare {

    /**
     * Compte le nombre de pixels différents entre deux images de même taille.
     * Deux pixels sont différents si leur valeur ARGB diffère.
     */
    public int countDifferentPixels(BufferedImage img1, BufferedImage img2) {
        ensureSameSize(img1, img2);

        int w = img1.getWidth();
        int h = img1.getHeight();
        int diff = 0;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    diff++;
                }
            }
        }
        return diff;
    }

    /**
     * Génère l'image différentielle :
     * - Noir (0,0,0) si les pixels sont identiques
     * - |ΔR|, |ΔG|, |ΔB| sinon (alpha forcé à 255)
     */
    public BufferedImage createDiffImage(BufferedImage img1, BufferedImage img2) {
        ensureSameSize(img1, img2);

        int w = img1.getWidth();
        int h = img1.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                int r1 = (rgb1 >>> 16) & 0xFF;
                int g1 = (rgb1 >>> 8) & 0xFF;
                int b1 = rgb1 & 0xFF;

                int r2 = (rgb2 >>> 16) & 0xFF;
                int g2 = (rgb2 >>> 8) & 0xFF;
                int b2 = rgb2 & 0xFF;

                int rd = Math.abs(r1 - r2);
                int gd = Math.abs(g1 - g2);
                int bd = Math.abs(b1 - b2);

                int outRgb;
                if (rd == 0 && gd == 0 && bd == 0) {
                    // Pixels identiques -> noir opaque
                    outRgb = (255 << 24);
                } else {
                    // Différences par canal -> couleur de la différence
                    outRgb = (255 << 24) | (rd << 16) | (gd << 8) | bd;
                }

                out.setRGB(x, y, outRgb);
            }
        }
        return out;
    }

    private void ensureSameSize(BufferedImage a, BufferedImage b) {
        if (a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) {
            throw new IllegalArgumentException(
                    "Les images doivent avoir la même taille (" +
                            a.getWidth() + "x" + a.getHeight() + " vs " +
                            b.getWidth() + "x" + b.getHeight() + ")."
            );
        }
    }
}
