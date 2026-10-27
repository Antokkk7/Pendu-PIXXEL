package ihm.pendu.view;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * Titre néon avec extrusion 3D réelle : plusieurs copies du texte empilées
 * en profondeur (translateZ) et un léger balancement autour de l'axe Y.
 * Nécessite une scène avec PerspectiveCamera (voir PenduFXapp).
 * Utilisable dans un FXML : &lt;NeonTitle text="..." fontSize="64"/&gt;.
 */
public class NeonTitle extends StackPane {

    private static final int LAYERS = 10;

    private String text = "";
    private double fontSize = 60;
    private final Timeline sway;

    public NeonTitle() {
        setPickOnBounds(false);
        setRotationAxis(Rotate.Y_AXIS);

        sway = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotateProperty(), -14, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.seconds(3.5), new KeyValue(rotateProperty(), 14, Interpolator.EASE_BOTH)));
        sway.setAutoReverse(true);
        sway.setCycleCount(Timeline.INDEFINITE);

        sceneProperty().addListener((obs, o, n) -> {
            if (n == null) {
                sway.stop();
            } else {
                sway.play();
            }
        });
        rebuild();
    }

    public String getText() { return text; }

    public void setText(String text) {
        this.text = text;
        rebuild();
    }

    public double getFontSize() { return fontSize; }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
        rebuild();
    }

    private void rebuild() {
        getChildren().clear();
        Font font = Font.font("Audiowide", fontSize);

        // Couches arrière (de la plus profonde à la plus proche)
        for (int i = LAYERS; i >= 1; i--) {
            double t = i / (double) LAYERS;
            Label l = new Label(text);
            l.setFont(font);
            l.setTextFill(Color.web("#ff2bd6").interpolate(Color.web("#3a0a5c"), t));
            l.setTranslateZ(i * 2.2);
            getChildren().add(l);
        }

        // Face avant
        Label front = new Label(text);
        front.setFont(font);
        front.setTextFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#ffffff")),
                new Stop(1, Color.web("#00f0ff"))));
        front.setEffect(new DropShadow(14, Color.web("#00f0ff", 0.9)));
        getChildren().add(front);
    }
}
