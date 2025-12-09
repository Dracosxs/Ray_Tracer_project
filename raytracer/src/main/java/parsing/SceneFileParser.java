package parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import geometrie.Color;
import geometrie.Point;
import geometrie.Vector;
import raytracer.Camera;
import raytracer.DirectionalLight;
import raytracer.PointLight;
import raytracer.Scene;
import raytracer.forme.Plane;
import raytracer.forme.Sphere;
import raytracer.forme.Triangle;

/**
 * Analyseur de fichiers de scène pour le moteur de ray tracing.
 *
 * Lit un fichier texte décrivant une scène (caméra, lumières, matériaux,
 * géométrie) et construit l’objet Scene correspondant. Gère les commandes
 * standard du format : taille, couleurs, caméra, lumières directionnelles
 * et ponctuelles, sommets, triangles, sphères, plans, etc.
 */


public class SceneFileParser {

    private Color currentDiffuse = new Color();
    private Color currentSpecular = new Color();
    private int maxVerts = 0;
    private final List<Point> vertices = new ArrayList<>();
    private double currentShininess = 0.0;

    public Scene parse(String fileName) throws IOException {
        Scene scene = new Scene();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\s+");
                String cmd = parts[0];

                switch (cmd) {
                    case "size" -> scene.setSize(
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2])
                    );
                    case "output" -> scene.setOutput(parts[1]);
                    case "camera" -> parseCamera(scene, parts);
                    case "ambient" -> scene.setAmbient(parseColor(parts));
                    case "diffuse" -> currentDiffuse = parseColor(parts);
                    case "specular" -> currentSpecular = parseColor(parts);
                    case "directional" -> scene.addLight(new DirectionalLight(
                            new Vector(Double.parseDouble(parts[1]),
                                    Double.parseDouble(parts[2]),
                                    Double.parseDouble(parts[3])),
                            parseColor(parts, 4)
                    ));
                    case "point" -> scene.addLight(new PointLight(
                            new Point(Double.parseDouble(parts[1]),
                                    Double.parseDouble(parts[2]),
                                    Double.parseDouble(parts[3])),
                            parseColor(parts, 4)
                    ));
                    case "maxverts" -> maxVerts = Integer.parseInt(parts[1]);
                    case "vertex" -> vertices.add(new Point(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                    ));
                    case "tri" -> {
                        int a = Integer.parseInt(parts[1]);
                        int b = Integer.parseInt(parts[2]);
                        int c = Integer.parseInt(parts[3]);
                        if (a >= maxVerts || b >= maxVerts || c >= maxVerts)
                            throw new IllegalArgumentException("Indice de vertex invalide dans tri");
                        Triangle tri = new Triangle(vertices.get(a), vertices.get(b), vertices.get(c));
                        tri.setDiffuse(currentDiffuse);
                        tri.setSpecular(currentSpecular);
                        tri.setShininess(currentShininess);
                        scene.addShape(tri);
                    }
                    case "sphere" -> {
                        Point center = new Point(
                                Double.parseDouble(parts[1]),
                                Double.parseDouble(parts[2]),
                                Double.parseDouble(parts[3])
                        );
                        double radius = Double.parseDouble(parts[4]);
                        Sphere s = new Sphere(center, radius);
                        s.setDiffuse(currentDiffuse);
                        s.setSpecular(currentSpecular);
                        s.setShininess(currentShininess);
                        scene.addShape(s);
                    }
                    case "plane" -> {
                        Point p = new Point(
                                Double.parseDouble(parts[1]),
                                Double.parseDouble(parts[2]),
                                Double.parseDouble(parts[3])
                        );
                        Vector n = new Vector(
                                Double.parseDouble(parts[4]),
                                Double.parseDouble(parts[5]),
                                Double.parseDouble(parts[6])
                        );
                        Plane plane = new Plane(p, n);
                        plane.setDiffuse(currentDiffuse);
                        plane.setSpecular(currentSpecular);
                        plane.setShininess(currentShininess);
                        scene.addShape(plane);
                    }
                    case "shininess" -> currentShininess = Double.parseDouble(parts[1]);
                    default -> throw new IllegalArgumentException("Commande inconnue : " + cmd);
                }
            }
        }
        return scene;
    }

    private void parseCamera(Scene scene, String[] p) {
        Point from = new Point(Double.parseDouble(p[1]), Double.parseDouble(p[2]), Double.parseDouble(p[3]));
        Point at   = new Point(Double.parseDouble(p[4]), Double.parseDouble(p[5]), Double.parseDouble(p[6]));
        Vector up  = new Vector(Double.parseDouble(p[7]), Double.parseDouble(p[8]), Double.parseDouble(p[9]));
        double fov = Double.parseDouble(p[10]);
        scene.setCamera(new Camera(from, at, up, fov));
    }

    private Color parseColor(String[] parts) {
        return new Color(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
    }

    private Color parseColor(String[] parts, int offset) {
        return new Color(Double.parseDouble(parts[offset]),
                Double.parseDouble(parts[offset + 1]),
                Double.parseDouble(parts[offset + 2]));
    }
}
