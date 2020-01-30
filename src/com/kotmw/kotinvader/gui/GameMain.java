package com.kotmw.kotinvader.gui;

import com.kotmw.kotinvader.PlayStatus;
import com.kotmw.kotinvader.gameobjects.entity.Entity;
import com.kotmw.kotinvader.gameobjects.entity.missiles.Missile;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
      VBox (root)
       ┗StackPane (cover)
       　┣BorderPane
       　┃ ┣[Top]    BorderPane (status)
       　┃ ┃ ┣[Left]   VBox
       　┃ ┃ ┣[Center] VBox
       　┃ ┃ ┗[Right]  VBox
       　┃ ┣[Center] StackPane
       　┃ ┃ ┣Pane (container)
       　┃ ┃ ┗Pane (float)※移動先
       　┃ ┗[Bottom] HBox (remain)
       　┗Pane ※移動


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
    ┃  4  凸 凸 凸　　　                                                                        　　    ┃
    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

    (バグ)
    TODO インベーダーの弾が2発同時に当たった時に無敵時間が発生しない

    TODO 設定画面
        TODO 仮想フルスクリーン、ウィンドウモードの実装
        TODO SE、BGMの有無
    TODO GUI系の調整、追加
        TODO 背景画像
    TODO SE、BGMの実装
    */

    private Stage title;

    private final PlayStatus player;

    private GameContainer container;
    private GameStatus status;
    private GameRemain remain;

    private CoverPane cover;

    private double xOffset, yOffset;

    public GameMain(Stage title) {
        this.title = title;
        player = new PlayStatus();

        KeyHandler keyManager = new KeyHandler();

        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(WINDOW_X, WINDOW_Y);


        FloatsContainer floats = new FloatsContainer();
        cover = new CoverPane(this, borderPane);
        cover.setId("root");
        container = new GameContainer(player, cover, floats);
        container.setId("container");
        status = new GameStatus(player);
        status.setId("status");
        status.getStyleClass().add("info");
        remain = new GameRemain(player);
        remain.setId("remain");
        remain.getStyleClass().add("info");

        StackPane stackPane = new StackPane(container, floats);

        borderPane.setTop(status);
        borderPane.setBottom(remain);
        borderPane.setCenter(stackPane);

        VBox root = new VBox(cover);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(WINDOW_X, WINDOW_Y);
        root.setId("box");

        ScaleTransition animationX = new ScaleTransition(Duration.seconds(0.25), cover), animationY = new ScaleTransition(Duration.seconds(0.25), cover);
        animationX.setFromX(0.001);
        animationX.setToX(1.0);
        animationY.setFromY(0.005);
        animationY.setToY(1.0);
        animationY.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), borderPane);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);

        SequentialTransition animation = new SequentialTransition(animationX, animationY, fade);

        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(null);
        scene.getRoot().requestFocus();
        Application.setUserAgentStylesheet("MODENA");
        scene.getStylesheets().addAll(getClass().getResource("/resources/main.css").toExternalForm());
        scene.setOnKeyPressed(keyManager);
        scene.setOnKeyReleased(keyManager);

        root.setOnMousePressed(event -> {
            root.setCursor(Cursor.MOVE);
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseReleased(event -> root.setCursor(Cursor.NONE));
        root.setOnMouseDragged(event -> {
            this.setX(event.getScreenX() - xOffset);
            this.setY(event.getScreenY() - yOffset);
        });
        root.setCursor(Cursor.NONE);
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

    @Override
    public void close() {
        title.show();
        super.close();
    }

    public class KeyHandler implements EventHandler<KeyEvent> {

//        private int count = 0;

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
                        Missile missile = player.getCannon().shoot();
                        if (missile != null) container.getChildren().add(missile);
                        break;
//                    case F1:
//                        cover.nextLevel(count++, event1 -> {});
//                    case ESCAPE:
//                        container.pause();
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
