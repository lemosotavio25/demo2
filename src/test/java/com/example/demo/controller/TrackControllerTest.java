package com.example.demo.controller;

import com.example.demo.model.Album;
import com.example.demo.model.Track;
import com.example.demo.dto.response.TrackResponse;
import com.example.demo.service.TrackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TrackControllerTest {

    @InjectMocks
    private TrackController trackController;

    @Mock
    private TrackService trackService;

    @Test
    void testGetTracksByAlbumId() {
        // Mock Track objects to simulate service response
        Album mockAlbum = new Album();
        List<Track> mockTracks = List.of(new Track("Track 1", mockAlbum));

        // Mock service behavior to return tracks
        when(trackService.getTracksByAlbumId(1L)).thenReturn(mockTracks);

        // Call the method being tested
        ResponseEntity<List<TrackResponse>> response = trackController.getTracksByAlbumId(1L);

        // Assertions
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Track 1", response.getBody().get(0).title());
    }
}
