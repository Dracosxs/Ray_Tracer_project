package raytracer;

import java.util.Optional;

import geometrie.Color;
import geometrie.Point;
import geometrie.Vector;
import raytracer.forme.Shape;

/**
 * Moteur de ray tracing principal.
 *
 * Calcule la couleur de chaque pixel en lançant des rayons depuis la caméra,
 * détecte les intersections avec les objets de la scène, gère l’éclairage
 * (diffuse, spéculaire, ambiant) et les ombres.
 */


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
        Vector rayDirectionUnnormalized = (Vector) cameraRight.multiply(normalizedX).addVector(cameraUp.multiply(normalizedY)).subtract(cameraBackward);

        Vector rayDirection = (Vector) rayDirectionUnnormalized.normalize();

        //Étape 4 : Lancer du rayon
        Ray ray = new Ray(scene.getCamera().getLookFrom(), rayDirection);


        return computeColorForRay(ray, 0);
    }

    /**
     * Cherche l'intersection la plus proche et détermine la couleur.
     */
    private Color computeColorForRay(Ray ray, int depth) {

        if (depth >= scene.getMaxDepth()) {
            return new Color(0, 0, 0);
        }

        Optional<Intersection> closestIntersection = Optional.empty();

        // On teste le rayon contre tous les objets de la scène
        for (Shape shape : scene.getShapes()) {
            Optional<Intersection> currentIntersection = shape.intersect(ray);

            if (currentIntersection.isPresent()) {
                // Si c'est la première intersection trouvée OU si elle est plus proche que la précédente
                if (closestIntersection.isEmpty() || currentIntersection.get().getDistance() < closestIntersection.get().getDistance()) {

                    closestIntersection = currentIntersection;
                }
            }
        }

        //Étape 5 : Rendu
        // Calcul de la couleur
        if (closestIntersection.isPresent()) {
            Intersection intersection = closestIntersection.get();
            Shape shape = intersection.getShape();
            Point hitPoint = intersection.getPosition();
            // La lumière ambiante s'applique partout de manière égale
            Color totalColor = scene.getAmbient(); // si l'ambiant est rouge et l'objet bleu, ça s'additionne.
            Vector viewDirection = (Vector) ray.getDirection().negate().normalize();

            for (Light light : scene.getLights()) {

                // Gestion Ombres
                // Vecteur vers la lumière
                Vector lightDirection = light.getL(hitPoint);

                // Point de départ décalé
                Point shadowOrigin = (Point) hitPoint.addVector(lightDirection.multiply(1e-9));

                // Le rayon d'ombre
                Ray shadowRay = new Ray(shadowOrigin, lightDirection);

                // Distance maximale à vérifier pour les intersections
                double distanceToLight = Double.MAX_VALUE;
                if (light instanceof PointLight) {
                    distanceToLight = ((PointLight) light).getPosition().distance(hitPoint);
                }

                boolean isShadowed = false;
                for (Shape s : scene.getShapes()) {
                    Optional<Intersection> shadowHit = s.intersect(shadowRay);
                    if (shadowHit.isPresent() && shadowHit.get().getDistance() < distanceToLight) {
                        isShadowed = true;
                        break;
                    }
                }

                // Calcul couleur

                // Si le point n'est pas à l'ombre pour cette lumière, on ajoute sa contribution
                if (!isShadowed) {
                    totalColor = totalColor.addVector(intersection.computeColor(light, viewDirection));
                }
            }
            Color specularColor = shape.getSpecular();
            // On ne calcule le reflet que si l'objet a une composante spéculaire (n'est pas noir)
            if (specularColor.getRed() > 0 || specularColor.getGreen() > 0 || specularColor.getBlue() > 0) {

                Vector d = ray.getDirection();
                Vector n = intersection.getNormal();

                // Formule de réflexion : R = D - 2(D.N)N
                double dotDN = d.dotProduct(n);
                Vector reflectionDir = (Vector) d.subtract(n.multiply(2 * dotDN)).normalize();

                Point reflectionOrigin = (Point) hitPoint.addVector(reflectionDir.multiply(1e-9));
                Ray reflectionRay = new Ray(reflectionOrigin, reflectionDir);

                // Appel Récursif
                Color reflectedColor = computeColorForRay(reflectionRay, depth + 1);

                // Ajout à la couleur totale : CouleurTotale += Specular * CouleurReflet
                totalColor = totalColor.addVector(specularColor.schurProduct(reflectedColor));
            }

            return totalColor;
        } else {
            return new Color(0, 0, 0); // Fond noir
        }
    }
}