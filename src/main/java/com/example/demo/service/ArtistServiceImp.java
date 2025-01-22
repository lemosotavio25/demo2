package com.example.demo.service;

import com.example.demo.model.Artist;
import com.example.demo.model.Album;
import com.example.demo.repository.AlbumRepository;
import com.example.demo.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.interfaces.ArtistService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImp implements ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    public ArtistServiceImp(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }


    // Get all artists
    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    // Get artist by ID
    @Override
    public Optional<Artist> getArtistById(Long id) {
        return artistRepository.findById(id);
    }

    // Save or update an artist
    @Override
    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    // Delete an artist by ID
    @Override
    public Artist deleteArtist(Long id) {
        artistRepository.deleteById(id);
        return null;
    }

    @Override
    public List<Album> getAlbumsByArtistId(Long artistId) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        return artist.map(a -> albumRepository.findByArtistId(a.getId())).orElseGet(Collections::emptyList);
    }
}