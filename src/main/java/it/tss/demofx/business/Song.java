/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.demofx.business;

import java.util.Objects;

/**
 *
 * @author tss
 */
public class Song {

    private Long id;
    private String titolo;
    private String artista;
    private String album;
    private final String filename;

    public Song(String titolo, String artista, String album, String filename) {
        this(null, titolo, artista, album, filename);
    }

    public Song(Long id, String titolo, String artista, String album, String filename) {
        this.id = id;
        this.titolo = titolo;
        this.artista = artista;
        this.album = album;
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getFilename() {
        return filename;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Song other = (Song) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Song{" + "id=" + id + ", titolo=" + titolo + ", artista=" + artista + ", album=" + album + '}';
    }

}
