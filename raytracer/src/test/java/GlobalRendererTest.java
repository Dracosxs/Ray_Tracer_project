import org.junit.jupiter.api.Test;
import parsing.SceneFileParser;
import raytracer.RayTracer;
import raytracer.Scene;
import tool.ImageComparator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalRendererTest {

    // Méthode utilitaire pour tester une scène spécifique
    private void testSceneAgainstReference(String testFileName, String referenceImageName) throws IOException {
        //Définir les chemins
        String scenePath = "src/main/resources/scenes/" + testFileName;
        String refPath = "src/main/resources/scenes/" + referenceImageName;

        //Générer l'image avec notre code
        SceneFileParser parser = new SceneFileParser();
        Scene scene = parser.parse(scenePath);
        RayTracer rayTracer = new RayTracer(scene);

        //Création image en mémoire
        BufferedImage generatedImage = new BufferedImage(scene.getWidth(), scene.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < scene.getHeight(); y++) {
            for (int x = 0; x < scene.getWidth(); x++) {
                // Inversion Y
                generatedImage.setRGB(x, scene.getHeight() - 1 - y, rayTracer.getPixelColor(x, y).toRGB());
            }
        }

        // harger l'image de référence fournie par les profs
        File refFile = new File(refPath);
        assertTrue(refFile.exists(), "L'image de référence n'existe pas : " + refPath);
        BufferedImage referenceImage = ImageIO.read(refFile);

        //Utiliser Comparateur
        long diffPixels = ImageComparator.countDifferentPixels(generatedImage, referenceImage);

        //résultat
        System.out.println("Test " + testFileName + " : " + diffPixels + " pixels différents.");


        //Assertion JUnit
        // Le PDF dit < 1000 pixels on met 10.
        assertTrue(diffPixels < 100, "L'image diffère de " + diffPixels + " pixels (tolérance < 10)");
    }

    //TESTS

    @Test
    public void testJalon4_Tp41_Directional() throws IOException {
        testSceneAgainstReference("jalon4/tp41-dir.test", "jalon4/tp41-dir.png");
    }

    @Test
    public void testJalon4_Tp41_Point() throws IOException {
        testSceneAgainstReference("jalon4/tp41-point.test", "jalon4/tp41-point.png");
    }

    @Test
    public void testJalon3_Tp32() throws IOException {
        testSceneAgainstReference("jalon3/tp32.test", "jalon3/tp32.png");
    }

//    @Test
//    public void testJalon4_Tp42_Directional() throws IOException {
//        testSceneAgainstReference("jalon4/tp42-dir.test", "jalon4/tp42-dir.png");
//    }
//
//    @Test
//    public void testJalon4_Tp42_Point() throws IOException {
//        testSceneAgainstReference("jalon4/tp42-point.test", "jalon4/tp42-point.png");
//    }
//
//    @Test
//    public void testJalon4_Tp43() throws IOException {
//        testSceneAgainstReference("jalon4/tp43.test", "jalon4/tp43.png");
//    }
//
//    @Test
//    public void testJalon4_Tp44() throws IOException {
//        testSceneAgainstReference("jalon4/tp44.test", "jalon4/tp44.png");
//    }
//
//    @Test
//    public void testJalon4_Tp45() throws IOException {
//        testSceneAgainstReference("jalon4/tp45.test", "jalon4/tp45.png");
//    }

    @Test
    public void testJalon5_Tp51_Diffuse() throws IOException {
        // Teste les ombres portées avec des sphères mates (diffuses uniquement)
        testSceneAgainstReference("jalon5/tp51-diffuse.test", "jalon5/tp51-diffuse.png");
    }

    @Test
    public void testJalon5_Tp51_Specular() throws IOException {
        // Teste l'illumination de Phong (reflets brillants) + ombres
        testSceneAgainstReference("jalon5/tp51-specular.test", "jalon5/tp51-specular.png");
    }


//    @Test
//    public void testJalon5_Tp52_Triangles() throws IOException {
//        // Contient un carré fait de triangles
//        testSceneAgainstReference("jalon5/tp52.test", "jalon5/tp52.png");
//    }
//
//    @Test
//    public void testJalon5_Tp53_Pyramid() throws IOException {
//        // Contient une pyramide (triangles)
//        testSceneAgainstReference("jalon5/tp53.test", "jalon5/tp53.png");
//    }
//
//    @Test
//    public void testJalon5_Tp54_Plane() throws IOException {
//        // Contient un plan infini -> La sphère s'affichera, mais pas le sol
//        testSceneAgainstReference("jalon5/tp54.test", "jalon5/tp54.png");
//    }
//
//    @Test
//    public void testJalon5_Tp55_Complex() throws IOException {
//        // Mélange Sphère, Triangle, Plan
//        testSceneAgainstReference("jalon5/tp55.test", "jalon5/tp55.png");
//    }


}