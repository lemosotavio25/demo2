package com.example.demo.inteface;

import com.example.demo.model.Album;
import com.example.demo.model.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistService {
    List<Artist> getAllArtists();
    Optional<Artist> getArtistById(Long id);
    Artist deleteArtist(Long id);
    List<Album> getAlbumsByArtistId(Long artistId);
    Artist saveArtist(Artist artist);
}
