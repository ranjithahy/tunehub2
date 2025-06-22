package com.demo.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.example.entities.Song;

public interface SongRepository extends JpaRepository<Song,Integer>{

	public Song findByName(String name);
}
