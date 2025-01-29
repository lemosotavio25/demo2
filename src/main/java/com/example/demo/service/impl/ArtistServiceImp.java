package com.example.demo.service.impl;

import com.example.demo.model.Artist;
import com.example.demo.model.Album;
import com.example.demo.repository.AlbumRepository;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImp implements ArtistService {


    private final ArtistRepository artistRepository;

    private AlbumRepository albumRepository;

    @Autowired
    public ArtistServiceImp(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Optional<Artist> getArtistById(Long id) {
        return artistRepository.findById(id);
    }

    @Override
    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }

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