package com.demo.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.example.entities.Playlist;
import com.demo.example.entities.Song;
import com.demo.example.services.PlaylistService;
import com.demo.example.services.SongService;

@Controller
public class PlaylistController {
@Autowired
PlaylistService playlistService;
@Autowired
SongService songService;

@GetMapping("/createPlaylist")
public String createPlaylist(Model model)
{
	List<Song> songList=songService.fetchAllSongs();
	model.addAttribute("songs",songList);
	return "createPlaylist";
}
@PostMapping("/addPlaylist")
public String addPlaylist(@ModelAttribute Playlist playlist)
{
	//updating playlist table playlist_song table which have done through maopping
	playlistService.addPlaylist(playlist);
      System.out.println(playlist);
	//updating song table song_playlist
List<Song> songList=playlist.getSongs();
for(Song s:songList)
{
	s.getPlaylists().add(playlist);
	
	//update song object in db
	songService.updateSong(s);
}
	return "adminHome";
}
//display playlist to user
@GetMapping("/viewPlaylists")
public String viewPlaylists(Model model)
{
	List<Playlist>allPlaylists=playlistService.fetchAllPlaylists();
	model.addAttribute("allPlaylists", allPlaylists);
	return "displayPlaylists";
}

}
