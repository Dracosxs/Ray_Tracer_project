package raytracer;

import geometrie.Point;
import geometrie.Vector;

public class Orthonormal {

    private final Vector Vhorizon;
    private final Vector Vvertial;
    private final Vector VdirectionCam;

    public Orthonormal(Camera camera) {
        Point lookFrom = camera.getLookFrom();
        Point lookAt   = camera.getLookAt();
        Vector up      = camera.getUp();

        // w = (lookFrom - lookAt) normalisé
        Vector wVec = (Vector) lookFrom.subtract(lookAt).normalize();

        // u = (up × w) normalisé
        Vector uVec = (Vector) up.crossProduct(wVec).normalize();

        // v = (w × u) normalisé
        Vector vVec = (Vector) wVec.crossProduct(uVec).normalize();

        this.Vhorizon = uVec;       // u est l'horizon
        this.Vvertial = vVec;       // v est la verticale
        this.VdirectionCam = wVec;  // w est la direction (arrière)
    }

    public Vector getU() { return Vhorizon; }
    public Vector getV() { return Vvertial; }
    public Vector getW() { return VdirectionCam; }

    @Override
    public String toString() {
        return "Orthonormal(u=" + Vhorizon + ", v=" + Vvertial + ", w=" + VdirectionCam + ")";
    }
}
