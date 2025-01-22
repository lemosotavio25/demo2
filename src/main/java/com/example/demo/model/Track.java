package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false, foreignKey = @ForeignKey(name = "fk_track_album"))
    private Album album;

    // Default constructor (required by JPA)
    public Track() {
    }

    // Parameterized constructor
    public Track(String title, Album album) {
        this.title = title;
        this.album = album;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Album getAlbum() {
        return album;
    }
}