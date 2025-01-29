package com.example.demo.service.impl;

import com.example.demo.model.Track;
import com.example.demo.repository.TrackRepository;
import com.example.demo.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackServiceImp implements TrackService {

    @Autowired
    private TrackRepository trackRepository;

    public TrackServiceImp(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public Track saveTrack(Track track) {
        return trackRepository.save(track);
    }

    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }

    public List<Track> getTracksByAlbumId(Long albumId) {
        return trackRepository.findByAlbumId(albumId);
    }

    public Optional<Track> getTrackById(Long id) {
        return trackRepository.findById(id);
    }

}
