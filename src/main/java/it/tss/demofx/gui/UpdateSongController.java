/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.demofx.gui;

import it.tss.demofx.App;
import it.tss.demofx.business.DbStore;
import it.tss.demofx.business.Song;
import it.tss.demofx.business.SongsStoreException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tss
 */
public class UpdateSongController implements Initializable {

    @FXML
    private TextField txtTitolo;
    @FXML
    private TextField txtArtista;
    @FXML
    private TextField txtAlbum;
    @FXML
    private Label txtFilename;
    private Song selected;
    private Stage stage;
    private File file;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initialize updateSong controller...");
    }

    public void onSave() {
        try {
            DbStore.updateSong(selected.getId(), txtTitolo.getText(), txtArtista.getText(), txtAlbum.getText());
            Messages.showMessage("Song salvata correttamente", "", Alert.AlertType.INFORMATION);
            stage.close();
        } catch (SongsStoreException ex) {
            Messages.showMessage(ex.getMessage(), "", Alert.AlertType.ERROR);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void changeSelected(Song song) {
        this.selected = song;
        txtTitolo.setText(song.getTitolo());
        txtArtista.setText(song.getArtista());
        txtAlbum.setText(song.getAlbum());
    }
    
        public void onChooseFile() {
        final FileChooser fileChooser = new FileChooser();
        this.file = fileChooser.showOpenDialog(App.getRootStage());
        txtFilename.setText(this.file.getName());
        
    }
}
