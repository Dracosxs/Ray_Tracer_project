package jalon4;

import geometrie.Color;
import geometrie.Point;
import geometrie.Vector;
import raytracer.*;
import raytracer.forme.Sphere;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LightingTest {

    @Test
    public void testLambertAtCenter() {
        //scène minimale
        Scene scene = new Scene();
        scene.setSize(640, 480);
        scene.setCamera(new Camera(new Point(0, 0, 4), new Point(0, 0, 0), new Vector(0, 1, 0), 45));

        // Pas de lumière ambiante pour isoler Lambert
        scene.setAmbient(new Color(0, 0, 0));

        // Ajout lumière directionnelle (1, 1, 1)
        scene.addLight(new DirectionalLight(new Vector(1, 1, 1), new Color(1, 1, 1)));

        // Ajout sphère rouge au centre
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1.0);
        sphere.setDiffuse(new Color(1, 0, 0));
        scene.addShape(sphere);

        //Lancer le RayTracer sur le pixel central
        RayTracer rayTracer = new RayTracer(scene);
        Color resultColor = rayTracer.getPixelColor(320, 240); // Pixel au centre

        //Vérification Mathématique
        //Intensité = N.L = (0,0,1).(0.577, 0.577, 0.577) = 0.57735 merci chatgpt
        double expectedIntensity = 1.0 / Math.sqrt(3.0);

        assertEquals(expectedIntensity, resultColor.getRed(), 0.01, "Le rouge doit être ~0.577");
        assertEquals(0.0, resultColor.getGreen(), 0.001, "Le vert doit être 0");
        assertEquals(0.0, resultColor.getBlue(), 0.001, "Le bleu doit être 0");
    }
}
