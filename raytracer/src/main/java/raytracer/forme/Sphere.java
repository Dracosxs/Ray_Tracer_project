package raytracer.forme;

import geometrie.AbstractVec3;
import geometrie.Point;
import geometrie.Vector;
import raytracer.Intersection;
import raytracer.Ray;

import java.util.Optional;

public class Sphere extends Shape {
    private final Point center;
    private final double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        //  Récupération des données
        Point rayOrigin = ray.getOrigin();
        Vector rayDirection = ray.getDirection();
        Point sphereCenter = this.center;

        //  Résolution de l'équation du second degré : a*t^2 + b*t + c = 0
        // L'inconnue 't' représente la distance le long du rayon.

        // Vecteur allant du centre de la sphère à l'origine du rayon
        Vector originToCenter = rayOrigin.sub(sphereCenter);

        // Coefficient 'a' : correspond au carré de la direction (d . d)
        // Comme la direction est normalisée, 'a' vaut souvent 1, mais on le calcule par sécurité.
        double quadraticA = rayDirection.dot(rayDirection);

        // Coefficient 'b' : 2 * ( (origine - centre) . direction )
        double quadraticB = 2.0 * originToCenter.dot(rayDirection);

        // Coefficient 'c' : (origine - centre)^2 - rayon^2
        double quadraticC = originToCenter.dot(originToCenter) - (radius * radius);

        // Calcul du discriminant (Delta)
        double discriminant = (quadraticB * quadraticB) - (4 * quadraticA * quadraticC);

        // Si discriminant < 0, la droite manque la sphère -> pas d'intersection
        if (discriminant < 0) {
            return Optional.empty();
        }


        //  Calcul des solutions (distances t1 et t2)
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double distance1 = (-quadraticB + sqrtDiscriminant) / (2 * quadraticA);
        double distance2 = (-quadraticB - sqrtDiscriminant) / (2 * quadraticA);

        // l'intersection la plus proche qui est DEVANT la caméra (distance > 0)
        double closestDistance = -1;

        if (distance2 > 0) {
            closestDistance = distance2; // distance2 est mathématiquement toujours la plus petite
        } else if (distance1 > 0) {
            closestDistance = distance1; // si distance2 < 0, on est peut-être à l'intérieur de la sphère
        } else {
            // La sphère est entièrement derrière la caméra
            return Optional.empty();
        }

        //  Construction du résultat
        // Point d'impact exact : P = Origine + (Direction * distance)
        Point hitPosition = (Point) rayOrigin.add(rayDirection.mul(closestDistance));

        return Optional.of(new Intersection(closestDistance, hitPosition,this, getNormal(hitPosition)));
    }

    @Override
    public Vector getNormal(Point p) {
        Vector diff = p.sub(center);
        AbstractVec3 n = diff.normalize();
        return new Vector(n.getX(), n.getY(), n.getZ()); // conversion
    }


    public Point getCenter() { return center; }
    public double getRadius() { return radius; }
}