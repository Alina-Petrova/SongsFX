/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.demofx.gui;

import it.tss.demofx.App;
import static it.tss.demofx.App.loadFXML;
import it.tss.demofx.business.DbStore;
import it.tss.demofx.business.Song;
import it.tss.demofx.business.SongsStoreException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author tss
 */
public class ListSongController implements Initializable {

    @FXML
    private TableView tblSongs;

    @FXML
    private Button cmdElimina;
    @FXML
    private Button cmdAggiorna;
    @FXML
    private Button cmdPlay;

    private Song selected;

    @FXML
    private TableColumn<String,Song> col1;
    @FXML
    private TableColumn<String,Song> col2;
    @FXML
    private TableColumn<String,Song> col3;
    @FXML
    private TableColumn<String,Song> col4;
    @FXML
    private TableColumn<String,Song> col5;
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initialize ListSongController");

        cmdElimina.setDisable(true);
        cmdAggiorna.setDisable(true);
        cmdPlay.setDisable(true);
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        col3.setCellValueFactory(new PropertyValueFactory<>("artista"));
        col4.setCellValueFactory(new PropertyValueFactory<>("album"));
        col5.setCellValueFactory(new PropertyValueFactory<>("filename"));
        
        tblSongs.setPlaceholder(new Label("Nessuna song presente"));

        tblSongs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tblSongs.getSelectionModel().selectedItemProperty().addListener(this::onSelectionChange);

        DbStore.all().forEach(v -> tblSongs.getItems().add(v));
    }

    public void onSelectionChange(ObservableValue<Song> sel, Object oldSel, Object newSel) {
        this.selected = sel.getValue();
        cmdElimina.setDisable(false);
        cmdAggiorna.setDisable(false);
        cmdPlay.setDisable(false);
    }

    public void onDelete() {
        try {
            DbStore.deleteSong(this.selected.getId());
            tblSongs.getItems().remove(this.selected);
            Messages.showMessage("Song eliminata con successo", "", Alert.AlertType.INFORMATION);
        } catch (SongsStoreException ex) {
            Messages.showMessage(ex.getMessage(), "", Alert.AlertType.ERROR);
        }
    }

    public void onUpdate() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("updateSong.fxml"));
        Stage dialog = new Stage();
        dialog.setTitle("Aggiorna SONG");
        dialog.setScene(new Scene(fxmlLoader.load(), 640, 480));
        UpdateSongController controller = fxmlLoader.getController();
        controller.changeSelected(this.selected);
        controller.setStage(dialog);
        dialog.initOwner(App.getRootStage());
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
        this.selected = DbStore.findById(this.selected.getId());
        int idx = tblSongs.getItems().indexOf(this.selected);
        tblSongs.getItems().set(idx, this.selected);
    }
    
    public void onPlay(){
        System.out.println(DbStore.getFile(this.selected.getFilename()));
        Media m = new Media(DbStore.getFile(this.selected.getFilename()));
        MediaPlayer mp = new MediaPlayer(m);
        mp.play();
    }
}
