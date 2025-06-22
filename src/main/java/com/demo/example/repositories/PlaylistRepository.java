package com.demo.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.example.entities.Playlist;

public interface PlaylistRepository  extends JpaRepository<Playlist,Integer>{

}
