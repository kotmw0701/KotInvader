package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.PlayStatus;
import com.kotmw.kotinvader.entity.Entity;
import com.kotmw.kotinvader.entity.missiles.CannonMissile;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

    /*
      VBox (box)
       ┗Pane (root)
         ┣BorderPane
         ┃ ┣[Top]    BorderPane (status)
         ┃ ┃ ┣[Left]   VBox
         ┃ ┃ ┣[Center] VBox
         ┃ ┃ ┗[Right]  VBox
         ┃ ┣[Center] Pane (container)
         ┃ ┗[Bottom] HBox (remain)
         ┗Pane
                                                  1200px
    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
    ┃       SCORE                                HI-SCORE                                           ┃
    ┃       00000                                 00000                                             ┃
    ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
    ┃                                                                                               ┃
    ┃                                                                                               ┃
    ┃                                 ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊                             　　┃
    ┃                                 ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊                             　　┃
    ┃                                 ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊                             　　┃
    ┃                                 ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊                             　　┃
    ┃                                 ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊ ＊                             　　┃
    ┃                                                                                               ┃ 600px
    ┃                                    ｜                                                    　　　　┃
    ┃                                                                                               ┃
    ┃　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　      ｜                            　　　　　┃
    ┃                                               ｜                                         　　　　┃
    ┃                                                                                               ┃
    ┃                                                                                               ┃
    ┃                                               凸                                   　　　　      ┃
    ┃                                                                                               ┃
    ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
    ┃　　凸 凸 凸　　　                                                                                 ┃
    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

    TODO インベーダーの種類を増やす
    TODO スコアの割り当てをちゃんと設定
    TODO Tochicaを設置する
    TODO 設定画面
        TODO フルスクリーン、仮想フルスクリーン、ウィンドウモードの実装
        TODO SE、BGMの有無
    TODO GUI系の調整、追加
        TODO レベル開始前のアニメーション
        TODO 背景画像
    TODO SE、BGMの実装
    */

    private final PlayStatus player;

    private GameContainer container;
    private GameStatus status;
    private GameRemain remain;

    private CoverPane cover;

    private double xOffset, yOffset;

    public GameMain() {
        player = new PlayStatus();

        cover = new CoverPane();

        container = new GameContainer(player, cover);
        container.setId("container");
        status = new GameStatus(player);
        status.setId("status");
        status.getStyleClass().add("info");
        remain = new GameRemain(player);
        remain.setId("remain");
        remain.getStyleClass().add("info");

        KeyHandler keyManager = new KeyHandler();

        VBox root = new VBox();
        root.setPrefSize(WINDOW_X, WINDOW_Y);
        root.setId("box");
        Pane pane = new Pane();
        pane.setId("root");
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            this.setX(event.getScreenX() - xOffset);
            this.setY(event.getScreenY() - yOffset);
        });
        root.getChildren().add(pane);

        BorderPane borderPane = new BorderPane();
//        borderPane.setId("root");
        borderPane.setPrefSize(WINDOW_X, WINDOW_Y);
        borderPane.setTop(status);
        borderPane.setBottom(remain);
        borderPane.setCenter(container);

        ScaleTransition animationX = new ScaleTransition(Duration.seconds(0.25), pane), animationY = new ScaleTransition(Duration.seconds(0.25), pane);
        animationX.setFromX(0.001);
        animationX.setToX(1.0);
        animationY.setFromY(0.005);
        animationY.setToY(1.0);
        animationY.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), borderPane);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);

        SequentialTransition animation = new SequentialTransition(animationX, animationY, fade);

        pane.getChildren().addAll(borderPane, cover);

        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
//        Scene scene = new Scene(borderPane, WINDOW_X, WINDOW_Y);
        scene.setFill(null);
        scene.getRoot().requestFocus();
        Application.setUserAgentStylesheet("MODENA");
        scene.getStylesheets().addAll(getClass().getResource("/resources/main.css").toExternalForm());
        scene.setOnKeyPressed(keyManager);
        scene.setOnKeyReleased(keyManager);

        this.setScene(scene);
        this.initStyle(StageStyle.TRANSPARENT);
        this.setTitle("Invader | Play!");
        this.setResizable(false);
        this.sizeToScene();
        this.show();

        animation.setOnFinished(event -> container.play());
        animation.play();

        this.setOnCloseRequest( event -> System.exit(0) );
    }

    public class KeyHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
                switch (event.getCode()) {
                    case LEFT:
                        player.getCannon().setSpeed(2.0);
                        player.getCannon().setDirection(Entity.Direction.LEFT);
                        break;
                    case RIGHT:
                        player.getCannon().setSpeed(2.0);
                        player.getCannon().setDirection(Entity.Direction.RIGHT);
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
