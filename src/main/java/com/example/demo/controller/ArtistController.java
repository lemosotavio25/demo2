package com.example.demo.controller;

import com.example.demo.dto.request.ArtistRequest;
import com.example.demo.model.Album;
import com.example.demo.model.Artist;
import com.example.demo.service.AlbumService;
import com.example.demo.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private ArtistService artistService;

    private AlbumService albumService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        Optional<Artist> artist = artistService.getArtistById(id);
        return artist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody ArtistRequest artistRequest) {
        Artist artist = new Artist();
        artist.setName(artistRequest.getName());

        Artist savedArtist = artistService.saveArtist(artist);
        return ResponseEntity.ok(savedArtist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id, @RequestBody Artist artistDetails) {
        Optional<Artist> existingArtist = artistService.getArtistById(id);

        if (existingArtist.isPresent()) {
            Artist artist = existingArtist.get();
            artist.setName(artistDetails.getName()); // Assuming Artist has a 'name' field
            Artist updatedArtist = artistService.saveArtist(artist);
            return ResponseEntity.ok(updatedArtist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        Optional<Artist> artist = artistService.getArtistById(id);
        if (artist.isPresent()) {
            artistService.deleteArtist(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{artistId}/albums")
    public ResponseEntity<List<Album>> getAlbumsByArtist(@PathVariable Long artistId) {
        List<Album> albums = artistService.getAlbumsByArtistId(artistId);
        if (!albums.isEmpty()) {
            return ResponseEntity.ok(albums);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}