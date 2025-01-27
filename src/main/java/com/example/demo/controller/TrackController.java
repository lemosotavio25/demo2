package com.example.demo.controller;

import com.example.demo.dto.TrackResponse;
import com.example.demo.interfaces.AlbumService;
import com.example.demo.interfaces.TrackService;
import com.example.demo.dto.TrackRequest;
import com.example.demo.model.Album;
import com.example.demo.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService trackService;
    private final AlbumService albumService;

    @Autowired
    public TrackController(TrackService trackService, AlbumService albumService) {
        this.trackService = trackService;
        this.albumService = albumService;
    }


    @PostMapping
    public ResponseEntity<TrackResponse> saveTrack(@RequestBody TrackRequest trackrequest){
        Optional<Album> albumOptional = albumService.getAlbumById(trackrequest.getAlbumId());
        if (albumOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Track track = new Track();
        track.setTitle(trackrequest.getTitle());
        track.setAlbum(albumOptional.get());

        Track savedTrack = trackService.saveTrack(track);
        TrackResponse trackResponse = new TrackResponse(track.getTitle(), track.getAlbum().getTitle());

        return ResponseEntity.ok(trackResponse);
    }
}