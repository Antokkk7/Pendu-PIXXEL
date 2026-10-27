package ihm.pendu.view;


import ihm.pendu.model.JeuPendu;
import ihm.pendu.model.JeuPenduBuilder;
import ihm.pendu.model.ResultatProposition;

import java.net.URL;
import java.util.ResourceBundle;

import ihm.pendu.PenduFXapp;
import ihm.pendu.model.EtatPartie;
import ihm.pendu.model.JeuPenduException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class JeuPenduTricheController implements Initializable {
    private JeuPendu jeu;

    @FXML
    private Label LeMot;
    @FXML
    private Label ResultatChoix;
    @FXML
    private Label ErreurChoix;
    @FXML
    private GridPane clavier;
    @FXML 
    private Button Retour;
    @FXML 
    private Label TricheSurprise;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        jeu = JeuPenduBuilder.creer().construire();
        LeMot.setText(NeonUI.spaced(new String(jeu.getMotCourant())));
        ErreurChoix.setText(getErreursAffichables());
        TricheSurprise.setText("");
        setupClavier();
    }
 

    /* ------------------------------ */

    private void setupClavier() {
        clavier.getChildren().clear();
        int col = 0, row = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            final char lettre = c;
            Button ButLettre = new Button(String.valueOf(lettre));
            ButLettre.setPrefSize(68, 68);
            ButLettre.getStyleClass().add("key");
            ButLettre.setOnAction(e -> {
                ButLettre.setDisable(true);
                proposerLettre(lettre, ButLettre);
            });
            clavier.add(ButLettre, col++, row);
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }

    private void proposerLettre(char lettre, Button touche) {
        try {
            ResultatProposition res = jeu.proposerLettre(lettre);
            LeMot.setText(NeonUI.spaced(new String(jeu.getMotCourant())));
            ResultatChoix.setText(res.getMessage());
            NeonUI.marquerTouche(touche, res);
            NeonUI.etat(ResultatChoix, res == ResultatProposition.BIEN_JOUEE ? "ok" : "ko");
            ErreurChoix.setText(getErreursAffichables());

            if (jeu.isPartieTerminee()) {
                DesacClavierVisuel();
                if (jeu.getEtatPartie() == EtatPartie.GAGNEE) {
                    ResultatChoix.setText("Félicitations, vous avez gagné !");
                    NeonUI.etat(ResultatChoix, "win");
                } else if (jeu.getEtatPartie() == EtatPartie.PERDUE) {
                    ResultatChoix.setText("Dommage, vous avez perdu.");
                    NeonUI.etat(ResultatChoix, "lose");
                    LeMot.setText(NeonUI.spaced(jeu.getMotATrouver()));
                }
            }
        } catch (JeuPenduException ex) {
            ResultatChoix.setText(ex.getMessage());
        }
    }

    private String getErreursAffichables() {
        int nbErreurs = jeu.getNbErreurs();
        int maxErreurs = jeu.getNbErreursMax();
        return "Erreurs : " + nbErreurs + " / " + (maxErreurs == 0 ? "∞" : maxErreurs);
    }

    @FXML
    private void resetGame() {
        jeu.reset();
        LeMot.setText(NeonUI.spaced(new String(jeu.getMotCourant())));
        ResultatChoix.setText("");
        NeonUI.etat(ResultatChoix, "");
        ErreurChoix.setText(getErreursAffichables());
        TricheSurprise.setText("");


        clavier.getChildren().forEach(node -> {
            if (node instanceof Button) {
                ((Button) node).setDisable(false);
                NeonUI.reinitialiserTouche((Button) node);
            }
        });
    }

    private void DesacClavierVisuel() {
        clavier.getChildren().forEach(node -> {
            if (node instanceof Button)
                ((Button) node).setDisable(true);
        });
    }

    @FXML
    private void quiter() {
        System.exit(0);
    }

    @FXML
    private void loadAccueil() {
        PenduFXapp.loadAccueil();
    }

    @FXML
    private void Triche() {
        TricheSurprise.setText(jeu.getMotATrouver());
    }

}