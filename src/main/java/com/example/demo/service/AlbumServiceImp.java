package com.example.demo.service;

import com.example.demo.interfaces.AlbumService;
import com.example.demo.model.Album;
import com.example.demo.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImp implements AlbumService {

    @Autowired
    private final AlbumRepository albumRepository;

    public AlbumServiceImp(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public Optional<Album> getAlbumById(Long id) {
        return albumRepository.findById(id);
    }

    @Override
    public Album saveAlbum(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }
}