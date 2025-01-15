package com.example.demo.service;

import com.example.demo.model.Artist;
import com.example.demo.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

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
}