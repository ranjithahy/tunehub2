package com.demo.example.services;



import java.util.List;

import com.demo.example.entities.Playlist;

public interface PlaylistService {

	public  void addPlaylist(Playlist playlist);

	public List<Playlist> fetchAllPlaylists();

	
}
