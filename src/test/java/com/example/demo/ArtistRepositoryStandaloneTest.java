package com.example.demo;

import com.example.demo.model.Artist;
import com.example.demo.repository.ArtistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ArtistRepositoryStandaloneTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    public void saveAndFindArtist() {
        // Arrange
        Artist artist = new Artist("John Doe");

        // Act
        Artist savedArtist = artistRepository.save(artist);
        Optional<Artist> foundArtist = artistRepository.findById(savedArtist.getId());

        // Assert
        assertThat(foundArtist).isPresent();
        assertThat(foundArtist.get().getName()).isEqualTo("John Doe");
    }
}