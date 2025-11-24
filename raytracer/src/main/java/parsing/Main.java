package parsing;

import geometrie.Color;
import raytracer.RayTracer;
import raytracer.Scene;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Charger la scène
            String filePath = "src/main/resources/scenes/jalon3/tp33.test"; // Exemple Jalon 3
            SceneFileParser parser = new SceneFileParser();
            Scene scene = parser.parse(filePath);

            System.out.println("Rendu de la scène : " + scene.getOutput());
            System.out.println("Taille : " + scene.getWidth() + "x" + scene.getHeight());

            // 2. Initialiser le RayTracer
            RayTracer rayTracer = new RayTracer(scene);

            // 3. Créer le buffer image
            BufferedImage image = new BufferedImage(scene.getWidth(), scene.getHeight(), BufferedImage.TYPE_INT_RGB);

            // 4. Boucle principale de rendu (parcours de chaque pixel)
            for (int y = 0; y < scene.getHeight(); y++) {
                for (int x = 0; x < scene.getWidth(); x++) {
                    // Attention : inversion de l'axe Y selon la consigne (consigne 93 du PDF)
                    // "inverser les pixels des colonnes (haut/bas) lors du rendu"
                    // On calcule la couleur pour (x, y)
                    Color color = rayTracer.getPixelColor(x, y);

                    // On écrit dans l'image (l'axe Y est souvent inversé en image informatique vs math)
                    // Le PDF dit "C'est une spécificité Java qui nécessitera d'inverser... sinon image à l'envers"
                    // Essayons d'écrire à (height - 1 - y) si nécessaire, ou on laisse (x, y) si la formule de b gère ça.
                    // La formule de b gère le repère centré, écrivons simplement à (x, height - 1 - y) pour remettre à l'endroit.
                    image.setRGB(x, scene.getHeight() - 1 - y, color.toRGB());
                }
            }

            // 5. Sauvegarder l'image
            File outputFile = new File(scene.getOutput());
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image générée avec succès : " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}