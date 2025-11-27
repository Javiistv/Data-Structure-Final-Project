package Interfaces;

import Runner.MainScreen;
import Characters.Hero;
import Logic.Game;
import com.almasb.fxgl.dsl.FXGL;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Scale;
import java.net.URL;
import java.util.Optional;

public class GameMapScreen {

    private final StackPane root;
    private final Pane container;
    private final ImageView mapView;
    private final ImageView heroView;
    private final Scale containerScale;

    private double lastMouseX, lastMouseY;
    private boolean draggingMap = false;
    private boolean up, down, left, right;
    private final AnimationTimer mover;
    private double vx = 0, vy = 0;
    private final double SPEED = 180.0;

    private final double mapW;
    private final double mapH;

    private MediaPlayer mapMusic;

    public GameMapScreen(Game game) {
        Hero hero = game != null ? game.getHero() : null;

        Image mapImg = null;
        try {
            mapImg = new Image(getClass().getResourceAsStream("/Resources/textures/map.png"));
        } catch (Throwable ignored) {
            mapImg = null;
        }

        if (mapImg == null) {
            mapW = 800;
            mapH = 600;
        } else {
            mapW = mapImg.getWidth() > 0 ? mapImg.getWidth() : 800;
            mapH = mapImg.getHeight() > 0 ? mapImg.getHeight() : 600;
        }

        mapView = new ImageView(mapImg);
        mapView.setPreserveRatio(false);
        mapView.setSmooth(true);
        mapView.setFitWidth(mapW);
        mapView.setFitHeight(mapH);

        container = new Pane();
        container.setPrefSize(mapW, mapH);
        container.getChildren().add(mapView);

        heroView = createHeroView(hero);
        container.getChildren().add(heroView);

        containerScale = new Scale(1.0, 1.0, 0, 0);
        container.getTransforms().add(containerScale);

        root = new StackPane();
        root.setPrefSize(800, 600);
        root.getChildren().add(container);

        root.addEventFilter(MouseEvent.ANY, MouseEvent::consume);

        installControls();
        installEscHandler();

        mover = new AnimationTimer() {
            private long last = -1;

            @Override
            public void handle(long now) {
                if (last < 0) {
                    last = now;
                }
                double dt = (now - last) / 1e9;
                last = now;
                updateVelocity();
                if (vx == 0 && vy == 0) {
                    return;
                }
                double dx = vx * dt;
                double dy = vy * dt;
                moveHero(dx, dy);
            }
        };

        root.sceneProperty().addListener((obs, old, nw) -> {
            if (nw != null) {
                Platform.runLater(() -> {
                    positionHeroCenter();
                    root.requestFocus();
                    mover.start();
                });
            } else {
                mover.stop();
            }
        });
    }

    private ImageView createHeroView(Hero hero) {
        Image img = null;
        if (hero != null) {
            try {
                img = hero.getImage();
            } catch (Throwable ignored) {
                img = null;
            }
        }
        if (img == null) {
            try {
                img = new Image(getClass().getResourceAsStream("/Resources/sprites/hero.png"));
            } catch (Throwable ignored) {
                img = null;
            }
        }
        ImageView iv = new ImageView(img);
        iv.setPreserveRatio(true);
        iv.setFitWidth(48);
        iv.setFitHeight(48);
        iv.setMouseTransparent(true);
        return iv;
    }

    private void positionHeroCenter() {
        double hw = heroView.getBoundsInLocal().getWidth();
        double hh = heroView.getBoundsInLocal().getHeight();
        heroView.setLayoutX((mapW - hw) / 2.0);
        heroView.setLayoutY((mapH - hh) / 2.0);
    }

    private void installControls() {
        root.addEventFilter(ScrollEvent.SCROLL, ev -> {
            if (ev.isControlDown()) {
                double delta = ev.getDeltaY() > 0 ? 1.1 : 0.9;
                double newScale = clamp(containerScale.getX() * delta, 0.4, 3.5);
                containerScale.setX(newScale);
                containerScale.setY(newScale);
                ev.consume();
            }
        });

        mapView.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                lastMouseX = e.getSceneX();
                lastMouseY = e.getSceneY();
                draggingMap = true;
                root.setCursor(Cursor.CLOSED_HAND);
                e.consume();
            }
        });

        mapView.setOnMouseDragged(e -> {
            if (draggingMap) {
                double dx = e.getSceneX() - lastMouseX;
                double dy = e.getSceneY() - lastMouseY;
                container.setTranslateX(container.getTranslateX() + dx);
                container.setTranslateY(container.getTranslateY() + dy);
                lastMouseX = e.getSceneX();
                lastMouseY = e.getSceneY();
                e.consume();
            }
        });

        mapView.setOnMouseReleased(e -> {
            draggingMap = false;
            root.setCursor(Cursor.DEFAULT);
            e.consume();
        });

        root.addEventFilter(KeyEvent.KEY_PRESSED, ev -> {
            KeyCode k = ev.getCode();
            if (k == KeyCode.ENTER) {
                ev.consume();
                return;
            }
            if (k == KeyCode.W || k == KeyCode.UP) {
                up = true;
            }
            if (k == KeyCode.S || k == KeyCode.DOWN) {
                down = true;
            }
            if (k == KeyCode.A || k == KeyCode.LEFT) {
                left = true;
            }
            if (k == KeyCode.D || k == KeyCode.RIGHT) {
                right = true;
            }
        });

        root.addEventFilter(KeyEvent.KEY_RELEASED, ev -> {
            KeyCode k = ev.getCode();
            if (k == KeyCode.ENTER) {
                ev.consume();
                return;
            }
            if (k == KeyCode.W || k == KeyCode.UP) {
                up = false;
            }
            if (k == KeyCode.S || k == KeyCode.DOWN) {
                down = false;
            }
            if (k == KeyCode.A || k == KeyCode.LEFT) {
                left = false;
            }
            if (k == KeyCode.D || k == KeyCode.RIGHT) {
                right = false;
            }
        });

        root.setFocusTraversable(true);
    }

    private void installEscHandler() {
        root.addEventFilter(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ESCAPE) {
                ev.consume();
                confirmReturnToMenu();
            }
        });
    }

    private void confirmReturnToMenu() {
        Platform.runLater(() -> {
            Alert dlg = new Alert(Alert.AlertType.CONFIRMATION);
            dlg.setTitle("Volver al menú");
            dlg.setHeaderText("¿Quieres volver al menú principal?");
            dlg.setContentText("Si vuelves al menú, la partida seguirá guardada en disco.");
            Optional<ButtonType> opt = dlg.showAndWait();
            if (opt.isPresent() && opt.get() == ButtonType.OK) {
                stopMapMusic();
                try {
                    FXGL.getGameScene().removeUINode(root);
                } catch (Throwable ignored) {
                }
                MainScreen.restoreMenuAndMusic();
            }
        });
    }

    private void updateVelocity() {
        vx = 0;
        vy = 0;
        if (left) {
            vx -= SPEED;
        }
        if (right) {
            vx += SPEED;
        }
        if (up) {
            vy -= SPEED;
        }
        if (down) {
            vy += SPEED;
        }
    }

    private void moveHero(double dx, double dy) {
        double hw = heroView.getBoundsInLocal().getWidth();
        double hh = heroView.getBoundsInLocal().getHeight();

        double curX = heroView.getLayoutX();
        double curY = heroView.getLayoutY();

        double localX = curX + dx;
        double localY = curY + dy;

        if (localX < 0) {
            localX = 0;
        }
        if (localY < 0) {
            localY = 0;
        }
        if (localX + hw > mapW) {
            localX = mapW - hw;
        }
        if (localY + hh > mapH) {
            localY = mapH - hh;
        }

        heroView.setLayoutX(localX);
        heroView.setLayoutY(localY);
    }

    public void show() {
        Platform.runLater(() -> {
            MainScreen.hideMenu();
            startMapMusic();
            FXGL.getGameScene().addUINode(root);
            root.requestFocus();
        });
    }

    private void startMapMusic() {
        try {
            stopMapMusic();
            URL res = getClass().getResource("/Resources/music/gameMapScreen.mp3");
            if (res == null) {
                return;
            }
            Media media = new Media(res.toExternalForm());
            mapMusic = new MediaPlayer(media);
            mapMusic.setCycleCount(MediaPlayer.INDEFINITE);
            double vol = MainScreen.getVolumeSetting();
            mapMusic.setVolume(vol);
            mapMusic.play();
        } catch (Throwable ignored) {
        }
    }

    private void stopMapMusic() {
        try {
            if (mapMusic != null) {
                mapMusic.stop();
                mapMusic.dispose();
                mapMusic = null;
            }
        } catch (Throwable ignored) {
        }
    }

    private static double clamp(double v, double lo, double hi) {
        double out = v;
        if (out < lo) {
            out = lo;
        } else if (out > hi) {
            out = hi;
        }
        return out;
    }
}
