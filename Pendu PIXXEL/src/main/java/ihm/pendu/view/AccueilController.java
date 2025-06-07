package ihm.pendu.view;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import ihm.pendu.PenduFXapp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import java.awt.Desktop;


public class AccueilController {

    @FXML
    private ImageView Cwinge;
    @FXML
    private ImageView Tomate;

    @FXML 
    private Button PartieNormale;
    @FXML 
    private Button PartieTriche;


    @FXML
    private void initializeNormal() {
        PartieNormale.setOnAction(e -> PenduFXapp.loadJeuNormal());
    }

    @FXML
    private void initializeTriche() {
        PartieTriche.setOnAction(e -> PenduFXapp.loadJeuTriche());
    }

    @FXML
    private void quitter() {
        System.exit(0);
    }

    @FXML
    private void Credit() {
        Alert confirm = new Alert(Alert.AlertType.INFORMATION);
        confirm.setTitle("Credits");
        confirm.setHeaderText("BUT1 - G2B - Semaine IHM");
        String Credits = new String("Fait par : PIQUEPE Alexis \nAidé par : du coca, une crêpe et des rêves");
        confirm.setContentText(Credits);
        confirm.getButtonTypes().setAll(ButtonType.OK);
    
        confirm.showAndWait();
    }

    @FXML
    private void Version() {
        Alert confirm = new Alert(Alert.AlertType.INFORMATION);
        confirm.setTitle("Version");
        confirm.setHeaderText("Version du jeu : ");
        String Version = new String("v1.0.1 *sans musique");
        confirm.setContentText(Version);
        confirm.getButtonTypes().setAll(ButtonType.OK);
    
        confirm.showAndWait();
    }


    @FXML
    private void Scratch() {
        String url = "https://scratch.mit.edu/projects/807348626/fullscreen/";

        try {
            Desktop Scratch = Desktop.getDesktop();
            Scratch.browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Regles() {
        Alert confirm = new Alert(Alert.AlertType.INFORMATION);
        confirm.setTitle("Comment jouer");
        confirm.setHeaderText("Commer jouer à Pendu Pique Sel ");
        String Version = new String("Nous pouvons actuellement observer une titanesque SKILL ISSUE de la part de notre très cher utilisateur... \n\nPas de chance !");
        confirm.setContentText(Version);
        confirm.getButtonTypes().setAll(ButtonType.OK);
    
        confirm.showAndWait();
    }
    
}
