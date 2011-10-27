package com.airjukebox.services;

import com.airjukebox.common.FileOperations;
import com.airjukebox.services.ChannelService;

import jaco.mp3.player.MP3Player;

import java.io.File;

import java.lang.InterruptedException;

public class PlayerService extends Thread {

	private MP3Player player = new MP3Player();
	private String currentSong;
	private ChannelService channel;
	private DownloadService download;

	public void setChannelService(ChannelService channel) {
		this.channel = channel;
	}

	public void setDownloadService(DownloadService download) {
		this.download = download;
	}

	public String getCurrentSong() {
		return currentSong;
	}

	public void skipSong() {
		player.stop();
	}

	public void run() {

		while (true) {

			// Wait until the player is free
			while (!player.isStopped()) {
				try { sleep(100); } catch (InterruptedException ex) {}
			}

			// Mark the previous song as complete
			if (currentSong != null) {
				channel.setSongComplete(currentSong);
				currentSong = null;
			}

			// Wait until the channel has some new content to play
			while (channel.isEmpty()) {
				System.out.println("PS: Waiting for channel content ...");

				try { sleep(1000); } catch (InterruptedException ex) {}
			}

			// Get the current song to play, and add it to the player
			String songID = currentSong = channel.getCurrentSong();

			// Wait until the download service indicates that the file is available
			while (!download.isAvailable(songID)) {
				System.out.println("PS: Waiting for download of song " + songID + " ...");

				try { sleep(1000); } catch (InterruptedException ex) {}

				songID = currentSong = channel.getCurrentSong();
			}

			// Add the song to the playlist and ensure the player is playing
			System.out.println("PS: Playing " + songID + " ...");

			File mp3 = FileOperations.getSongFile(songID);
			player = new MP3Player();
			player.addToPlayList(mp3);
			player.play();
		}
	}
}
