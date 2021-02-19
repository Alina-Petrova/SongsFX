/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.demofx.business;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tss
 */
public class DbStore {

    private static final String URL = "jdbc:mariadb://localhost:3306/songs";
    private static final String USR = "tss";
    private static final String PWD = "ghiglieno";
    private static final String MEDIA_FOLDER = "/home/tss/Documenti/";
    
    public static void saveNewSong(String titolo, String artista, String album, String filename, File file) {
        try ( Connection cn = DriverManager.getConnection(URL, USR, PWD);  
              PreparedStatement pcmd = cn.prepareStatement("INSERT INTO song (title, artist, album, filename) "
                + "VALUES (?, ?, ?, ?)");)
        {

            pcmd.setString(1, titolo);
            pcmd.setString(2, artista);
            pcmd.setString(3, album);
            pcmd.setString(4, filename);
            Files.createFile(Paths.get(MEDIA_FOLDER + filename));
            pcmd.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new SongsStoreException("Errore nel salvataggio della nuova song");
        }
    }

    public static void updateSong(Long id, String titolo, String artista, String album) {
        try ( Connection cn = DriverManager.getConnection(URL, USR, PWD);  
            PreparedStatement pcmd = cn.prepareStatement("update song set title = ?, artist = ?, album = ?, filename = ? where id = ?");) {
            pcmd.setString(1, titolo);
            pcmd.setString(2, artista);
            pcmd.setString(3, album);
            pcmd.setLong(4, id);
            pcmd.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new SongsStoreException("Errore nell'aggiornamento della song");
        }
    }
    
    public static void deleteSong(Long id) {
        try ( Connection cn = DriverManager.getConnection(URL, USR, PWD);  
            PreparedStatement pcmd = cn.prepareStatement("delete from song where id = ?"))
        {
            pcmd.setLong(1, id);
            pcmd.execute();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new SongsStoreException("Errore nel salvataggio della nuova song");
        }
    }
    
    public static Song findById(Long id) {
        try ( Connection cn = DriverManager.getConnection(URL, USR, PWD);  
                PreparedStatement pcmd = cn.prepareStatement("select * from song where id = ?");) {
            pcmd.setLong(1, id);
            ResultSet rs = pcmd.executeQuery();
            if(rs.next()){
                return new Song(rs.getLong("id"),rs.getString("title"), rs.getString("artist"), rs.getString("album"), rs.getString("filename"));
            }else{
                throw new SongsStoreException("Errore nel recupero della song da db");
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new SongsStoreException("Errore nell'aggiornamento della song");
        }
    }
    
    public static List<Song> all(){
        try ( Connection cn = DriverManager.getConnection(URL, USR, PWD);  
                Statement cmd = cn.createStatement()) {
            List<Song> result = new ArrayList<>();
            ResultSet rs = cmd.executeQuery("select * from song order by title");
            while(rs.next()){
                result.add(new Song(rs.getLong("id"),rs.getString("title"), rs.getString("artist"), rs.getString("album"), rs.getString("filename")));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new SongsStoreException("Errore nel salvataggio della nuova song");
        }
    }
    
    public static String getFile(String filename){
        
        return Paths.get(MEDIA_FOLDER + filename ).toUri().toString();
    }

}
