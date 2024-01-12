package com.demo.example.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.example.entities.Playlist;
import com.demo.example.repositories.PlaylistRepository;

@Service
public class PlaylistServiceImplementation  implements PlaylistService{

	@Autowired
	PlaylistRepository repo;

	@Override
	public void addPlaylist(Playlist playlist) {
		repo.save(playlist);
		
	}
//fetching list of playlist which consists list of songs
	@Override
	public List<Playlist> fetchAllPlaylists() {
		
		return repo.findAll();
	}

	
	
}
