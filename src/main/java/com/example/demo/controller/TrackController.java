package com.example.demo.controller;

import com.example.demo.dto.TrackResponse;
import com.example.demo.interfaces.AlbumService;
import com.example.demo.interfaces.TrackService;
import com.example.demo.dto.TrackRequest;
import com.example.demo.model.Album;
import com.example.demo.model.Track;
import com.example.demo.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService trackService;
    private final AlbumService albumService;
    private final TrackRepository trackRepository;

    @Autowired
    public TrackController(TrackService trackService, AlbumService albumService, TrackRepository trackRepository) {
        this.trackService = trackService;
        this.albumService = albumService;
        this.trackRepository = trackRepository;
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

        trackService.saveTrack(track);
        TrackResponse trackResponse = new TrackResponse(track.getId(),
                track.getTitle()
        );

        return ResponseEntity.ok(trackResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrackResponse> updateTrack(@PathVariable Long id,@RequestBody TrackRequest trackrequest){
        Optional<Track> existingTrack = trackService.getTrackById(id);
        if (existingTrack.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            Track track = existingTrack.get();
            track.setTitle(trackrequest.getTitle());
            trackRepository.save(track);
            TrackResponse trackResponse = new TrackResponse(
                    track.getId(),
                    track.getTitle()
            );
            return ResponseEntity.status(HttpStatus.OK).body(trackResponse);


        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackResponse> getTrackById(@PathVariable Long id){
        Optional<Track> existingTrack = trackService.getTrackById(id);
        if (existingTrack.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        else{
            Track track = existingTrack.get();
            TrackResponse trackResponse = new TrackResponse(
                    track.getId(),
                    track.getTitle()
            );
            return ResponseEntity.status(HttpStatus.OK).body(trackResponse);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long id){
        Optional<Track> existingTrack = trackService.getTrackById(id);
        if (existingTrack.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Track track = existingTrack.get();
        trackService.deleteTrack(track.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{albumId}/tracks")
    public ResponseEntity<List<TrackResponse>> getTracksByAlbumId(@PathVariable Long albumId) {
        List<Track> tracks = trackService.getTracksByAlbumId(albumId);

        if (tracks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<TrackResponse> trackResponses = tracks.stream()
                .map(track -> new TrackResponse(
                        track.getId(),
                        track.getTitle()
                ))
                .toList();

        return ResponseEntity.ok(trackResponses);
    }


}