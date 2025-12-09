package parsing;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import geometrie.Color;
import raytracer.RayTracer;
import raytracer.Scene;

/**
 * Point d’entrée du programme de ray tracing.
 *
 * Charge une scène depuis un fichier, initialise le moteur de rendu,
 * calcule la couleur de chaque pixel et génère l’image finale au format PNG.
 * Gère l’inversion verticale des coordonnées et l’export sur disque.
 */

public class Main {
    public static void main(String[] args) {
        try {
            //Charger la scène
            //String filePath = "src/main/resources/scenes/final.scene";
            String filePath = "raytracer\\src\\main\\resources\\scenes\\final.scene";
            SceneFileParser parser = new SceneFileParser();
            Scene scene = parser.parse(filePath);

            System.out.println("Rendu de la scène : " + scene.getOutput());
            System.out.println("Taille : " + scene.getWidth() + "x" + scene.getHeight());

            //Initialiser le RayTracer
            RayTracer rayTracer = new RayTracer(scene);

            //Créer le buffer image
            BufferedImage image = new BufferedImage(scene.getWidth(), scene.getHeight(), BufferedImage.TYPE_INT_RGB);

            //Boucle principale de rendu (parcours de chaque pixel)
            for (int y = 0; y < scene.getHeight(); y++) {
                for (int x = 0; x < scene.getWidth(); x++) {
                    // inversion de l'axe Y selon la consigne
                    // inverser les pixels des colonnes (haut/bas) lors du rendu
                    //calcule la couleur pour (x, y)
                    Color color = rayTracer.getPixelColor(x, y);
                    image.setRGB(x, scene.getHeight() - 1 - y, color.toRGB());
                }
            }

            // sauvegarde l'image
            File outputFile = new File(scene.getOutput());
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image générée avec succès : " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}