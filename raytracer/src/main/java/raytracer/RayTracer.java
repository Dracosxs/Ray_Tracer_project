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
     *
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

        //Étape 5 : Rendu
        // Calcul de la couleur
        if (closestIntersection.isPresent()) {
            Intersection intersection = closestIntersection.get();

            // La lumière ambiante s'applique partout de manière égale
            Color totalColor = scene.getAmbient(); // si l'ambiant est rouge et l'objet bleu, ça s'additionne.

            //ajout la contribution de chaque lumière active
            for (Light light : scene.getLights()) {
                // Intersection va calculer sa couleur pour cette lumière
                Color diffusePart = intersection.computeColor(light);

                // On accumule la lumière
                totalColor = totalColor.add(diffusePart);
            }

            return totalColor;
        } else {
            return new Color(0, 0, 0); // Noir si on touche rien
        }
    }
}