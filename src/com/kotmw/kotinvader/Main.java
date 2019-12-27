package com.kotmw.kotinvader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        File files = new File(getClass().getResource("/resources").toURI());
        for (File file : files.listFiles()) {
            System.out.println(file.getName());
        }
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Title.fxml")));//スクリーンのデータを読み込む
        Application.setUserAgentStylesheet("MODENA");
        scene.getStylesheets().addAll(getClass().getResource("/resources/main.css").toExternalForm());
        scene.getRoot().requestFocus();//キー入力を受け付けるようにする
        stage.setTitle("Invader | Title");//タイトルを設定する
        stage.setResizable(false);//サイズ変更を不可能にする
        stage.setScene(scene);//読み込んだデータを設定する
        stage.sizeToScene();//サイズ変更を不可能にしたときに左右に生ずる10pxのバグの余白を削除する
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
