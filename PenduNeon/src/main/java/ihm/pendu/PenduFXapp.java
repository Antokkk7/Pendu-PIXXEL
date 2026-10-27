package ihm.pendu;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PenduFXapp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

        PenduFXapp.primaryStage = primaryStage;
        chargerPolices();
        loadAccueil();
    }

    private static void chargerPolices() {
        for (String f : new String[] { "Audiowide-Regular.ttf", "Rajdhani-Bold.ttf" }) {
            try (var in = PenduFXapp.class.getResourceAsStream("fonts/" + f)) {
                Font.loadFont(in, 12);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** Chemin (URL) de la feuille de style néon, utilisable par les dialogues. */
    public static String getStylesheet() {
        return PenduFXapp.class.getResource("css/neon.css").toExternalForm();
    }

    /** Crée la scène : thème néon + caméra perspective (pour les éléments 3D). */
    private static Scene creerScene(Parent root) {
        boolean dejaAffiche = primaryStage.getScene() != null;
        Scene scene = dejaAffiche
                ? new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight())
                : new Scene(root);
        scene.setFill(Color.web("#07030f"));
        scene.setCamera(new PerspectiveCamera());
        scene.getStylesheets().add(getStylesheet());
        return scene;
    }

    private static void afficher(String titre, String fxml) {
        try {
            Parent root = FXMLLoader.load(PenduFXapp.class.getResource(fxml));
            Scene scene = creerScene(root);
            primaryStage.setTitle(titre);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadAccueil() {
        afficher("Accueil", "view/Accueil.fxml");
    }

    public static void loadJeuNormal() {
        afficher("Jeu du Pendu", "view/JeuPendu.fxml");
    }

    public static void loadJeuTriche() {
        afficher("Jeu du Pendu - triche", "view/JeuPenduTriche.fxml");
    }

    public static void main2(String[] args) {
        launch(args);
    }
}
