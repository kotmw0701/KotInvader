package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.PlayStatus;
import com.kotmw.kotinvader.entity.missiles.CannonMissile;
import com.kotmw.kotinvader.handlers.MainScheduleHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class GameMain extends Stage {

    public static final double WINDOW_X = 1200.0; //ウィンドウのサイズ
    public static final double WINDOW_Y = 700.0;
    public static final double STATUS_X = WINDOW_X; //ステータスの画面のサイズ
    public static final double STATUS_Y = 100.0;
    public static final double REMAIN_X = WINDOW_X; //残基表示の画面のサイズ
    public static final double REMAIN_Y = 50.0;
    public static final double MAIN_X = WINDOW_X; //メイン画面のサイズ
    public static final double MAIN_Y = 550.0;

    private final PlayStatus player;

    private GameContainer container;
    private GameStatus status;
    private GameRemain remain;


    public GameMain() {
        player = new PlayStatus();

        container = new GameContainer(player);
        status = new GameStatus();
        remain = new GameRemain();

        KeyHandler keyManager = new KeyHandler();

        BorderPane root = new BorderPane();
        root.setPrefSize(WINDOW_X, WINDOW_Y);
        root.setTop(status);
        root.setBottom(remain);
        root.setCenter(container);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.getRoot().requestFocus();
        scene.setOnKeyPressed(keyManager);
        scene.setOnKeyReleased(keyManager);

        this.setScene(scene);
        this.initStyle(StageStyle.TRANSPARENT);
        this.setTitle("Invader | Play!");
        this.setResizable(false);
        this.sizeToScene();
        this.show();

        this.setOnCloseRequest( event -> System.exit(0) );

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0.016),
                        new MainScheduleHandler(container)
                )
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public class KeyHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
                switch (event.getCode()) {
                    case LEFT:
                        player.getCannon().setSpeed(5.0);
                        player.getCannon().setDirection(180);
                        break;
                    case RIGHT:
                        player.getCannon().setSpeed(5.0);
                        player.getCannon().setDirection(0.0);
                        break;
//                    case UP:
//                        player.getCannon().setSpeed(5.0);
//                        player.getCannon().setDirection(270.0);
//                        break;
//                    case DOWN:
//                        player.getCannon().setSpeed(5.0);
//                        player.getCannon().setDirection(90.0);
//                        break;
                    case SPACE:
                        if (container.getChildren().stream().anyMatch(e -> e instanceof CannonMissile)) break;
                        container.getChildren().add(player.getCannon().shoot());
                        break;
                    default:
                        break;

                }
            } else if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
                if (event.getCode().isArrowKey()) {
                    player.getCannon().setSpeed(0.0);
                }
            }
        }
    }
}
