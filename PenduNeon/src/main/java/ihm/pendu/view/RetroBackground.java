package ihm.pendu.view;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

/**
 * Fond rétro-futuriste (synthwave) : ciel nocturne étoilé, soleil néon rayé
 * et grille en perspective qui défile vers l'horizon.
 * Utilisable directement dans un FXML : &lt;RetroBackground/&gt;.
 */
public class RetroBackground extends Pane {

    private static final Color CYAN = Color.web("#00f0ff");
    private static final Color MAGENTA = Color.web("#ff2bd6");

    private final Canvas canvas = new Canvas();
    private final double[][] stars = new double[70][3]; // x, y (0..1), taille
    private double phase = 0;
    private long last = 0;

    private final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (last != 0) {
                phase = (phase + (now - last) / 1_000_000_000.0 * 0.35) % 1.0;
            }
            last = now;
            draw();
        }
    };

    public RetroBackground() {
        setMouseTransparent(true);
        setMinSize(0, 0);
        getChildren().add(canvas);

        Random rnd = new Random(42);
        for (double[] s : stars) {
            s[0] = rnd.nextDouble();
            s[1] = rnd.nextDouble() * 0.55;
            s[2] = 0.6 + rnd.nextDouble() * 1.4;
        }

        // L'animation ne tourne que lorsque le fond est affiché dans une scène.
        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) {
                timer.stop();
                last = 0;
            } else {
                timer.start();
            }
        });
    }

    @Override
    protected void layoutChildren() {
        canvas.setWidth(Math.max(1, getWidth()));
        canvas.setHeight(Math.max(1, getHeight()));
        draw();
    }

    private void draw() {
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        GraphicsContext g = canvas.getGraphicsContext2D();
        double horizon = h * 0.60;

        // Ciel
        g.setFill(new LinearGradient(0, 0, 0, horizon, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#07030f")),
                new Stop(0.65, Color.web("#1a0735")),
                new Stop(1, Color.web("#4a0f6b"))));
        g.fillRect(0, 0, w, horizon);

        // Étoiles
        g.setFill(Color.web("#ffffff", 0.75));
        for (double[] s : stars) {
            g.fillOval(s[0] * w, s[1] * h, s[2], s[2]);
        }

        // Soleil rayé
        double r = Math.min(w, h) * 0.26;
        double cx = w / 2;
        double cy = horizon - r * 0.15;
        g.save();
        g.beginPath();
        g.rect(0, 0, w, horizon);
        g.clip();
        g.setFill(new LinearGradient(0, cy - r, 0, cy + r, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#ffe600")),
                new Stop(0.55, Color.web("#ff7a1a")),
                new Stop(1, Color.web("#ff2bd6"))));
        g.fillOval(cx - r, cy - r, 2 * r, 2 * r);
        // bandes sombres qui s'épaississent vers le bas
        g.setFill(Color.web("#1a0735"));
        for (int i = 0; i < 7; i++) {
            double y = cy + r * (0.05 + i * 0.14);
            double thick = 2 + i * 1.6;
            g.fillRect(cx - r, y, 2 * r, thick);
        }
        g.restore();

        // Sol
        g.setFill(new LinearGradient(0, horizon, 0, h, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#1a0735")),
                new Stop(1, Color.web("#07030f"))));
        g.fillRect(0, horizon, w, h - horizon);

        // Lignes de fuite
        double groundH = h - horizon;
        g.setLineWidth(1.2);
        int cols = 24;
        for (int i = -cols; i <= cols; i++) {
            double xBottom = cx + i * (w / cols) * 1.1;
            g.setStroke(Color.web("#ff2bd6", 0.55));
            g.strokeLine(cx, horizon, xBottom, h);
        }
        // Lignes horizontales (défilent vers le spectateur)
        for (int i = 0; i < 14; i++) {
            double t = (i + phase) / 14.0;     // 0 = horizon, 1 = bas
            double y = horizon + groundH * t * t;
            g.setStroke(Color.web("#00f0ff", 0.15 + 0.55 * t));
            g.setLineWidth(0.8 + 1.6 * t);
            g.strokeLine(0, y, w, y);
        }

        // Halo à l'horizon
        g.setStroke(MAGENTA);
        g.setLineWidth(2);
        g.strokeLine(0, horizon, w, horizon);
        g.setStroke(Color.web("#ff2bd6", 0.25));
        g.setLineWidth(8);
        g.strokeLine(0, horizon, w, horizon);

        // Voile sombre léger pour garder l'UI lisible
        g.setFill(Color.web("#07030f", 0.35));
        g.fillRect(0, 0, w, h);
    }
}
