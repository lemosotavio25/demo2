package com.example.demo.controller;

import com.example.demo.model.Album;
import com.example.demo.model.Artist;
import com.example.demo.dto.request.ArtistRequest;
import com.example.demo.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ArtistControllerTest {

    @InjectMocks
    private ArtistController artistController;

    @Mock
    private ArtistService artistService;

    // Test for getAllArtists()
    @Test
    void testGetAllArtists() {
        List<Artist> mockArtists = List.of(new Artist(1L, "Artist 1"), new Artist(2L, "Artist 2"));

        // Mock service behavior
        when(artistService.getAllArtists()).thenReturn(mockArtists);

        // Call the method
        List<Artist> response = artistController.getAllArtists();

        // Assertions
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Artist 1", response.get(0).getName());
    }

    // Test for getArtistById()
    @Test
    void testGetArtistById_Found() {
        Artist mockArtist = new Artist(1L, "Artist 1");

        when(artistService.getArtistById(1L)).thenReturn(Optional.of(mockArtist));

        ResponseEntity<Artist> response = artistController.getArtistById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getName().contains("Artist 1"));
    }

    @Test
    void testGetArtistById_NotFound() {
        when(artistService.getArtistById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Artist> response = artistController.getArtistById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    // Test for createArtist()
    @Test
    void testCreateArtist() {
        ArtistRequest artistRequest = new ArtistRequest("New Artist");
        Artist savedArtist = new Artist(1L, "New Artist");

        when(artistService.saveArtist(any(Artist.class))).thenReturn(savedArtist);

        ResponseEntity<Artist> response = artistController.createArtist(artistRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("New Artist", response.getBody().getName());
    }

    // Test for updateArtist()
    @Test
    void testUpdateArtist_Success() {
        Artist existingArtist = new Artist(1L, "Old Artist");
        Artist updatedArtist = new Artist(1L, "Updated Artist");

        when(artistService.getArtistById(1L)).thenReturn(Optional.of(existingArtist));
        when(artistService.saveArtist(any(Artist.class))).thenReturn(updatedArtist);

        Artist newDetails = new Artist();
        newDetails.setName("Updated Artist");

        ResponseEntity<Artist> response = artistController.updateArtist(1L, newDetails);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Updated Artist", response.getBody().getName());
    }

    @Test
    void testUpdateArtist_NotFound() {
        when(artistService.getArtistById(1L)).thenReturn(Optional.empty());

        Artist newDetails = new Artist();
        newDetails.setName("Updated Artist");

        ResponseEntity<Artist> response = artistController.updateArtist(1L, newDetails);

        assertEquals(404, response.getStatusCodeValue());
    }

    // Test for deleteArtist()
    @Test
    void testDeleteArtist_Success() {
        Artist existingArtist = new Artist(1L, "Artist to Delete");

        when(artistService.getArtistById(1L)).thenReturn(Optional.of(existingArtist));
        doNothing().when(artistService).deleteArtist(1L);

        ResponseEntity<Void> response = artistController.deleteArtist(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteArtist_NotFound() {
        when(artistService.getArtistById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = artistController.deleteArtist(1L);

        assertEquals(404, response.getStatusCodeValue());
    }
}