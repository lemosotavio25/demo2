package com.example.demo.repository;

import com.example.demo.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long>{
    List<Track> findByAlbumId(Long albumId);
}
