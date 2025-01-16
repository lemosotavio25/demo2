package com.example.demo.service;

import com.example.demo.model.Artist;
import com.example.demo.model.Album;
import com.example.demo.repository.AlbumRepository;
import com.example.demo.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    // Get all artists
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    // Get artist by ID
    public Optional<Artist> getArtistById(Long id) {
        return artistRepository.findById(id);
    }

    // Save or update an artist
    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    // Delete an artist by ID
    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    public List<Album> getAlbumsByArtistId(Long artistId) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        return artist.map(a -> albumRepository.findByArtistId(a.getId())).orElseGet(Collections::emptyList);
    }
}