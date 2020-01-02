package com.kotmw.kotinvader;

import com.kotmw.kotinvader.gui.GameMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TitleController {

    @FXML
    private void onStart(ActionEvent actionEvent) {
        GameMain gameMain = new GameMain();
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onSettings(ActionEvent actionEvent) {
    }

    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
