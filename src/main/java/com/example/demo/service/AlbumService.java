package com.example.demo.service;

import com.example.demo.model.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumService {
    List<Album> getAllAlbums(); // Renamed for clarity
    Optional<Album> getAlbumById(Long id);
    Album saveAlbum(Album album);
    void deleteAlbum(Long id); // Changed return type to void
}