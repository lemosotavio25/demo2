package com.example.demo.service;

import com.example.demo.model.Track;

import java.util.List;
import java.util.Optional;

public interface TrackService{
    Track saveTrack(Track track);
    void deleteTrack(Long id);
    List<Track> getTracksByAlbumId(Long albumId);
    Optional<Track> getTrackById(Long id);
}
