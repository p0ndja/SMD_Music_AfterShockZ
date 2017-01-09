package me.catallena.mcaholic.api;

import java.io.File;
import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.catallena.mcaholic.pluginMain;
import me.catallena.mcaholic.NoteBlockAPI.NBSDecoder;
import me.catallena.mcaholic.NoteBlockAPI.RadioSongPlayer;
import me.catallena.mcaholic.NoteBlockAPI.Song;
import me.catallena.mcaholic.NoteBlockAPI.SongPlayer;
import me.catallena.mcaholic.api.tools.ActionBar;
import net.md_5.bungee.api.ChatColor;

public class MusicThread implements Runnable {

	private SongPlayer songPlayer;
	private Song[] loadedSongs;
	private int currentSong;

	public MusicThread(File songFolder) {
		currentSong = 0;
		loadedSongs = new Song[1];
		loadSongs(songFolder);
	}

	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			String title = pluginMain.getMusicThread().getCurrentSong().getTitle();
			String author = pluginMain.getMusicThread().getCurrentSong().getAuthor();
			if (title.isEmpty()) {
				title = ChatColor.GRAY+"Unknown Song"+ChatColor.RESET;
			}
			if (author.isEmpty()) {
				author = ChatColor.GRAY+"Unknown Author"+ChatColor.RESET;
			}
			ActionBar music = new ActionBar(ChatColor.AQUA + "Song: " + ChatColor.YELLOW + title + ChatColor.WHITE + " - " + ChatColor.GREEN + author);
			music.sendToAll();

			if (!songPlayer.isPlaying()) {
				randomSong();
				songPlayer.addPlayer(player);
			}
			songPlayer.setPlaying(true);
		}
	}

	public Song getCurrentSong() {
		return getSongPlayer().getSong();
	}

	public SongPlayer getSongPlayer() {
		return songPlayer;
	}

	private void loadSongs(File songFolder) {
		File[] files = songFolder.listFiles();
		List<Song> songs = new ArrayList<Song>();

		pluginMain.getInstance().getLogger().info("Loading songs from " + songFolder.getPath());

		for (File file : files) {
			Song song = null;
			try {
				song = NBSDecoder.parse(file);
			} catch (Exception e) {

				pluginMain.getInstance().getLogger()
						.severe("ERROR: Failed to load song " + file.getPath() + "... " + e.getMessage());

				continue;
			}

			songs.add(song);
		}

		pluginMain.getInstance().getLogger().info("Loaded " + songs.size() + " songs!");

		loadedSongs = songs.toArray(loadedSongs);

		songPlayer = new RadioSongPlayer(loadedSongs[0]);

		songPlayer.setPlaying(true);

	}

	public void nextSong(int times) {
		currentSong += times;

		if (currentSong >= loadedSongs.length) {
			currentSong = 0;
		}

		songPlayer = new RadioSongPlayer(loadedSongs[currentSong]);
	}

	public void randomSong() {
		Random rand = new Random();
		int r = rand.nextInt(30);
		for (Player p : Bukkit.getOnlinePlayers()) {
			songPlayer.addPlayer(p); 
		}
		songPlayer.setPlaying(false);
		nextSong(r);
	}

	public boolean trySetSong(String songName) {
		songName = songName.replaceAll("_", " ");
		for (Song song : loadedSongs) {
			if (song.getTitle().equalsIgnoreCase(songName)) {
				songPlayer.setPlaying(false);
				songPlayer = new RadioSongPlayer(song);
				songPlayer.setPlaying(true);
				return true;
			}
		}

		if (!songName.endsWith(".nbs")) {
			songName = songName + ".nbs";
		}

		for (Song song : loadedSongs) {
			if (song.getPath().getName().equalsIgnoreCase(songName)) {
				songPlayer.setPlaying(false);
				songPlayer = new RadioSongPlayer(song);
				songPlayer.setPlaying(true);
				return true;
			}
		}

		return false;
	}

	public Song[] getSongs() {
		return loadedSongs;
	}

}
