package com.example.demo.controller;

import com.example.demo.dto.AlbumResponse;
import com.example.demo.inteface.AlbumService;
import com.example.demo.inteface.ArtistService;
import com.example.demo.model.Album;
import com.example.demo.dto.AlbumRequest;
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

    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArtistService artistService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // Get all albums
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

    // Get album by ID
    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        Optional<Album> album = albumService.getAlbumById(id);
        return album.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new album
    @PostMapping
    public ResponseEntity<Album> createAlbum(@RequestBody AlbumRequest albumRequest) {
        // Fetch the artist entity by ID
        Optional<Artist> artistOptional = artistService.getArtistById(albumRequest.getArtistId());
        if (artistOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Map the DTO to an entity
        Album album = new Album();
        album.setTitle(albumRequest.getTitle());
        album.setArtist(artistOptional.get());

        // Delegate to the service
        Album savedAlbum = albumService.saveAlbum(album);
        return ResponseEntity.ok(savedAlbum);
    }

    // Update an existing album
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

    // Delete an album
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