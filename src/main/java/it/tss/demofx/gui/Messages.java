/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.demofx.gui;

import javafx.scene.control.Alert;

/**
 *
 * @author tss
 */
public class Messages {

    private static final String TITOLO = "SONGS APP MESSAGE";

    private Messages() {
    }

    public static void showMessage(String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(TITOLO);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
