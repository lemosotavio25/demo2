package com.example.demo.controller;

import com.example.demo.dto.ArtistRequest;
import com.example.demo.model.Album;
import com.example.demo.model.Artist;
import com.example.demo.inteface.AlbumService;
import com.example.demo.inteface.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }


    // Get all artists
    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    // Get artist by ID
    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        Optional<Artist> artist = artistService.getArtistById(id);
        return artist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new artist
    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody ArtistRequest artistRequest) {
        Artist artist = new Artist();
        artist.setName(artistRequest.getName());

        Artist savedArtist = artistService.saveArtist(artist);
        return ResponseEntity.ok(savedArtist);
    }

    // Update an existing artist
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

    // Delete an artist
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

    // Get albums by artist ID
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