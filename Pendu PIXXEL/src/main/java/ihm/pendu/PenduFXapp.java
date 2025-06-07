package ihm.pendu;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PenduFXapp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

        PenduFXapp.primaryStage = primaryStage;
        loadAccueil();
    }

    /* ------------------------------ */

    public static void loadAccueil() {

        try {
            Parent root = FXMLLoader.load(PenduFXapp.class.getResource("view/Accueil.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Accueil");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ------------------------------ */

    public static void loadJeuNormal() {

        try {
            Parent LJV = FXMLLoader.load(PenduFXapp.class.getResource("view/JeuPendu.fxml"));
            Scene scene = new Scene(LJV);
            primaryStage.setTitle("Jeu du Pendu");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ------------------------------ */


    public static void loadJeuTriche() {

        try {
            Parent LJV = FXMLLoader.load(PenduFXapp.class.getResource("view/JeuPenduTriche.fxml"));
            Scene scene = new Scene(LJV);
            primaryStage.setTitle("Jeu du Pendu - triche");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main2(String[] args) {
        launch(args);
    }
}
