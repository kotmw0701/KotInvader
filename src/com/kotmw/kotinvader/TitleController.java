package com.kotmw.kotinvader;

import com.kotmw.kotinvader.gui.GameMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TitleController {

    @FXML
    private void onStart(ActionEvent actionEvent) {
        GameMain gameMain = new GameMain();
    }
}
