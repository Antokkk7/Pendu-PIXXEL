package ihm.pendu.view;

import ihm.pendu.PenduFXapp;
import ihm.pendu.model.ResultatProposition;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Petites fonctions utilitaires d'affichage (aucune logique de jeu).
 */
public final class NeonUI {

    private NeonUI() {}

    /** "A--B" devient "A - - B" pour que les tirets restent lisibles. */
    public static String spaced(String mot) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mot.length(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(mot.charAt(i));
        }
        return sb.toString();
    }

    /** Colore une touche du clavier selon le résultat de la proposition. */
    public static void marquerTouche(Button touche, ResultatProposition res) {
        touche.getStyleClass().removeAll("key-ok", "key-ko");
        if (res == ResultatProposition.BIEN_JOUEE) {
            touche.getStyleClass().add("key-ok");
        } else if (res == ResultatProposition.MAUVAIS_CHOIX) {
            touche.getStyleClass().add("key-ko");
        }
    }

    /** Remet une touche à son style initial (reset de la partie). */
    public static void reinitialiserTouche(Button touche) {
        touche.getStyleClass().removeAll("key-ok", "key-ko");
    }

    /** Applique l'état visuel d'un message : "ok", "ko", "win", "lose" ou "" (neutre). */
    public static void etat(Label label, String etat) {
        label.getStyleClass().removeAll("msg-ok", "msg-ko", "msg-win", "msg-lose");
        if (etat != null && !etat.isEmpty()) {
            label.getStyleClass().add("msg-" + etat);
        }
    }

    /** Applique le thème néon à une boîte de dialogue. */
    public static void styler(Alert alert) {
        alert.getDialogPane().getStylesheets().add(PenduFXapp.getStylesheet());
        alert.getDialogPane().getStyleClass().add("neon-dialog");
    }
}
