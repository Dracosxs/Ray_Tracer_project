package parsing;

import raytracer.Scene;

public class Main {
    public static void main(String[] args) {
        try {
            String filePath = "src/main/resources/scenes/jalon2/test5.scene";
            SceneFileParser parser = new SceneFileParser();
            Scene scene = parser.parse(filePath);

            System.out.println("Scène chargée");
            System.out.println("Taille : " + scene.getWidth() + "x" + scene.getHeight());
            System.out.println("Fichier de sortie : " + scene.getOutput());
            System.out.println("Caméra : " + scene.getCamera().getLookFrom());
            System.out.println("Lumières : " + scene.getLights().size());
            System.out.println("Objets : " + scene.getShapes().size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
