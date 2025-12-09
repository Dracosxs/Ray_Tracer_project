package raytracer;

import geometrie.Color;
import raytracer.forme.Shape;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private int width;
    private int height;
    private String output = "output.png";
    private Camera camera;
    private Color ambient = new Color();
    private final List<Light> lights = new ArrayList<>();
    private final List<Shape> shapes = new ArrayList<>();

    private int maxDepth = 1;

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setOutput(String output) { this.output = output; }
    public String getOutput() { return output; }

    public void setCamera(Camera camera) { this.camera = camera; }
    public Camera getCamera() { return camera; }

    public void setAmbient(Color c) { this.ambient = c; }
    public Color getAmbient() { return ambient; }

    public List<Light> getLights() { return lights; }
    public List<Shape> getShapes() { return shapes; }

    public void addLight(Light l) { lights.add(l); }

    public void addShape(Shape s) {
        shapes.add(s);
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
}
