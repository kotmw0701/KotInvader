package com.kotmw.kotinvader.canvasprototype;

import com.kotmw.kotinvader.canvasprototype.gui.GameMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TitleController {

    @FXML
    private void onStart(ActionEvent actionEvent) {
        GameMain gameMain = new GameMain();
    }
}
