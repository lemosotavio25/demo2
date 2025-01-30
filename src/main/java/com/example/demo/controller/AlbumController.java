package com.example.demo.controller;

import com.example.demo.dto.response.AlbumResponse;
import com.example.demo.service.AlbumService;
import com.example.demo.service.ArtistService;
import com.example.demo.model.Album;
import com.example.demo.dto.request.AlbumRequest;
import com.example.demo.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    private final ArtistService artistService;

    @Autowired
    public AlbumController(AlbumService albumService, ArtistService artistService) {
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponse>> getAllAlbums() {
        List<Album> albums = albumService.getAllAlbums();
        List<AlbumResponse> responseDTOList = new ArrayList<>();

        for (Album album : albums) {
            responseDTOList.add(
                    new AlbumResponse(
                            album.getId(),
                            album.getTitle(),
                            album.getArtist().getId(),
                            album.getArtist().getName()
                    )
            );
        }

        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        Optional<Album> album = albumService.getAlbumById(id);
        return album.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Album> createAlbum(@RequestBody AlbumRequest albumRequest) {
        Optional<Artist> artistOptional = artistService.getArtistById(albumRequest.getArtistId());
        if (artistOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Album album = new Album();
        album.setTitle(albumRequest.getTitle());
        album.setArtist(artistOptional.get());

        Album savedAlbum = albumService.saveAlbum(album);
        return ResponseEntity.ok(savedAlbum);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestBody Album albumDetails) {
        Optional<Album> existingAlbum = albumService.getAlbumById(id);
        if (existingAlbum.isPresent()) {
            Album album = existingAlbum.get();
            album.setTitle(albumDetails.getTitle()); // Assuming Album has a 'title' field
            Album updatedAlbum = albumService.saveAlbum(album);
            return ResponseEntity.ok(updatedAlbum);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        if (albumService.getAlbumById(id).isPresent()) {
            albumService.deleteAlbum(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}