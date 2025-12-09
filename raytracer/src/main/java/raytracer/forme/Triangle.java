package raytracer.forme;

import geometrie.Point;
import geometrie.Vector;
import raytracer.Intersection;
import raytracer.Ray;

import java.util.Optional;

public class Triangle extends Shape {
    private final Point a, b, c;
    public final Vector normal;

    private final Vector edge1;
    private final Vector edge2;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;

        // Calcul de la normale dès la construction (Optimisation)
        // Normale = (B-A) vectoriel (C-A) normalisé
        this.edge1 =  b.subtract(a);
        this.edge2 =  c.subtract(a);
        // Attention au sens (main droite), parfois c'est edge1 x edge2 ou l'inverse selon l'ordre des points
        // Ici on garde l'ordre standard trigonométrique
        this.normal = (Vector) edge1.crossProduct(edge2).normalize();
    }

    public Point getA() { return a; }
    public Point getB() { return b; }
    public Point getC() { return c; }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        final double EPSILON = 1e-9;

        Vector rayDirection = ray.getDirection();

        // Calcul du vecteur p = d x edge2
        Vector pVec = rayDirection.crossProduct(this.edge2);

        // Calcul du déterminant
        double determinant = edge1.dotProduct(pVec);

        // Si déterminant proche de 0, rayon parallèle au triangle
        if (Math.abs(determinant) < EPSILON) {
            return Optional.empty();
        }

        double invDeterminant = 1.0 / determinant;

        // Calcul du vecteur t = origin - a
        Vector tVec = ray.getOrigin().subtract(a);

        double beta = tVec.dotProduct(pVec) * invDeterminant;
        if (beta < 0.0 || beta > 1.0) {
            return Optional.empty(); // Hors du triangle
        }

        // Calcul du vecteur q = tVec x edge1
        Vector qVec = tVec.crossProduct(edge1);

        double gamma = rayDirection.dotProduct(qVec) * invDeterminant;
        if (gamma < 0.0 || beta + gamma > 1.0) {
            return Optional.empty(); // Hors du triangle
        }

        // Calcul de t (distance)
        double t = edge2.dotProduct(qVec) * invDeterminant;

        if (t < EPSILON) {
            return Optional.empty(); // Derrière la caméra
        }

        Point hitPosition = (Point) ray.getOrigin().addVector(rayDirection.multiply(t));

        return Optional.of(new Intersection(t, hitPosition, this, normal));
   }
}