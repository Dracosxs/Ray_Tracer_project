package raytracer;

import geometrie.Color;
import geometrie.Vector;
import raytracer.forme.Shape;

import java.util.Optional;

public class RayTracer {
    private final Scene scene;
    private final Orthonormal cameraBasis; // Le repère (u, v, w) de la caméra

    public RayTracer(Scene scene) {
        this.scene = scene;
        this.cameraBasis = new Orthonormal(scene.getCamera());
    }

    /**
     * Calcule la couleur pour un pixel spécifique de l'image.
     * @param pixelX La coordonnée horizontale du pixel (colonne).
     * @param pixelY La coordonnée verticale du pixel (ligne).
     * @return La couleur calculée pour ce pixel.
     */
    public Color getPixelColor(int pixelX, int pixelY) {
        int imageWidth = scene.getWidth();
        int imageHeight = scene.getHeight();

        //Étape 1 : Calcul de la taille virtuelle du pixel dans la scène
        // On convertit le FOV (degrés) en radians
        double fovRadians = Math.toRadians(scene.getCamera().getFov());

        // La hauteur totale de la vue à 1 unité de distance est liée à la tangente du FOV
        double viewPlaneHalfHeight = Math.tan(fovRadians / 2.0);

        // Mise à l'échelle pour obtenir la taille d'un seul pixel
        double pixelSizeY = viewPlaneHalfHeight; // Simplification issue cours pour hauteur unitaire
        // On respecte strictement la formule du PDF :
        double pixelFactorY = Math.tan(fovRadians / 2.0);
        double pixelFactorX = pixelFactorY * ((double) imageWidth / imageHeight);

        //Étape 2 : Positionnement normalisé sur le plan image
        // On centre les coordonnées : (0,0) devient le centre de l'image, pas le coin.
        // Formule PDF : a = (pixelwidth * (i - w/2 + 0.5)) / (w/2)

        double normalizedX = (pixelFactorX * (pixelX - imageWidth / 2.0 + 0.5)) / (imageWidth / 2.0);
        double normalizedY = (pixelFactorY * (pixelY - imageHeight / 2.0 + 0.5)) / (imageHeight / 2.0);

        //Étape 3 : Construction du vecteur direction du rayon
        // d = u*a + v*b - w
        // u = vecteur vers la droite, v = vecteur vers le haut, w = vecteur vers l'arrière
        Vector cameraRight = cameraBasis.getU();
        Vector cameraUp = cameraBasis.getV();
        Vector cameraBackward = cameraBasis.getW(); // w pointe vers l'arrière dans ce repère donc faudra le soustraire et pas l'additionner

        // On combine les vecteurs pour trouver la direction qui passe par le pixel
        Vector rayDirectionUnnormalized = (Vector) cameraRight.mul(normalizedX)
                .add(cameraUp.mul(normalizedY))
                .sub(cameraBackward);

        Vector rayDirection = (Vector) rayDirectionUnnormalized.normalize();

        //Étape 4 : Lancer du rayon
        Ray ray = new Ray(scene.getCamera().getLookFrom(), rayDirection);

        // TEST A SUPPRIMER : Debug pour le pixel central (320, 240)
        if (pixelX == 320 && pixelY == 240) {
            System.out.println("\n--- DEBUG PIXEL (320, 240) ---");
            System.out.println("Rayon Origine : " + ray.getOrigin());
            System.out.println("Rayon Direction : " + ray.getDirection()); // Devrait être approx (0, 0, -1)

            for (Shape shape : scene.getShapes()) {
                if (shape instanceof raytracer.forme.Sphere) {
                    raytracer.forme.Sphere s = (raytracer.forme.Sphere) shape;
                    System.out.println("Test Sphère (Centre " + s.getCenter() + ", Rayon " + s.getRadius() + ")");

                    // Test manuel de la formule
                    Vector oc = ray.getOrigin().sub(s.getCenter());
                    double a = ray.getDirection().dot(ray.getDirection());
                    double b = 2.0 * oc.dot(ray.getDirection());
                    double c = oc.dot(oc) - (s.getRadius() * s.getRadius());
                    double delta = b * b - 4 * a * c;

                    System.out.println("   -> Delta calculé : " + delta);
                    if (delta >= 0) {
                        System.out.println("   -> TOUCHÉ ! (Intersection détectée)");
                    } else {
                        System.out.println("   -> RATÉ (Delta négatif)");
                    }
                }
            }
        }

        return computeColorForRay(ray);
    }

    /**
     * Cherche l'intersection la plus proche et détermine la couleur.
     */
    private Color computeColorForRay(Ray ray) {
        Optional<Intersection> closestIntersection = Optional.empty();

        // On teste le rayon contre tous les objets de la scène
        for (Shape shape : scene.getShapes()) {
            Optional<Intersection> currentIntersection = shape.intersect(ray);

            if (currentIntersection.isPresent()) {
                // Si c'est la première intersection trouvée OU si elle est plus proche que la précédente
                if (closestIntersection.isEmpty() ||
                        currentIntersection.get().getTime() < closestIntersection.get().getTime()) {

                    closestIntersection = currentIntersection;
                }
            }
        }

        //Étape 5 : Rendu final
        if (closestIntersection.isPresent()) {
            // Pour le Jalon 3, on retourne simplement la lumière ambiante globale
            return scene.getAmbient();
        } else {
            // Si le rayon ne touche rien, on retourne du noir
            return new Color(0, 0, 0);
        }
    }
}