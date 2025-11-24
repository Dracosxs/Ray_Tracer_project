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
        Vector wVec = (Vector) lookFrom.sub(lookAt).normalize();

        // u = (up × w) normalisé
        Vector uVec = (Vector) up.cross(wVec).normalize();

        // v = (w × u) normalisé
        Vector vVec = (Vector) wVec.cross(uVec).normalize();

        this.Vhorizon = wVec;
        this.Vvertial = uVec;
        this.VdirectionCam = vVec;
    }

    public Vector getU() { return Vhorizon; }
    public Vector getV() { return Vvertial; }
    public Vector getW() { return VdirectionCam; }

    @Override
    public String toString() {
        return "Orthonormal(u=" + Vhorizon + ", v=" + Vvertial + ", w=" + VdirectionCam + ")";
    }
}
