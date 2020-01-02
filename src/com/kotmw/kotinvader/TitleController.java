package com.kotmw.kotinvader;

import com.kotmw.kotinvader.gui.GameMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TitleController {

    @FXML
    private void onStart(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        GameMain gameMain = new GameMain(stage);
        stage.hide();
    }

    public void onSettings(ActionEvent actionEvent) {
    }

    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
