package com.airjukebox.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.IOException;

import java.util.List;
import java.util.LinkedList;

import java.lang.InterruptedException;

public class ChannelService extends Thread {

	private String previousSong;
	private List<String> playQueue = new LinkedList<String>();
	private PlayerService player;
	private DownloadService download;

	public void setPlayerService(PlayerService player) {
		this.player = player;
	}

	public void setDownloadService(DownloadService download) {
		this.download = download;
	}

	public boolean isEmpty() {
		return playQueue.isEmpty();
	}

	public String getCurrentSong() {
		if (playQueue.isEmpty()) return null;
		return playQueue.get(0);
	}

	public void setSongComplete(String songID) {

		try {
			System.out.println("CS: Sending song complete notification");
			String playbackURL = System.getProperty("airjukebox.endpoint", "http://localhost:8000") + "/channels/" + System.getProperty("airjukebox.channel") + "/playback";

			HttpClient client = new DefaultHttpClient();
			client.execute(new HttpGet(playbackURL + "/finished?source_id=grooveshark&track_id=" + songID));
		} catch (IOException ex) {}

		previousSong = songID;
		playQueue.remove(0);
	}

	private class PlaybackRequest {
		String request_source_id;
		String request_track_id;
	}
	private class PlaybackRequests {
		List<PlaybackRequest> playlist;
	}

	private void refreshPlaylist() {

		System.out.println("CS: Polling playback API");

		HttpClient client = new DefaultHttpClient();
		Gson gson = new Gson();
		String playbackURL = System.getProperty("airjukebox.endpoint", "http://localhost:8000") + "/channels/" + System.getProperty("airjukebox.channel") + "/playback";

		try {
			// Make an HTTP request to the central API
			HttpResponse response = client.execute(new HttpGet(playbackURL));
			InputStreamReader responseReader = new InputStreamReader(response.getEntity().getContent());
			PlaybackRequests playback = gson.fromJson(responseReader, PlaybackRequests.class);

			playQueue.clear();
			for (PlaybackRequest request : playback.playlist) {

				String songID = request.request_track_id;
				if (songID == null || songID.equals(previousSong)) continue;

				playQueue.add(songID);
				download.enqueue(songID);
			}

		} catch (IOException ex) {}
	}

	public void run() {

		while (true) {

			// Refresh the upcoming playlist
			refreshPlaylist();

			// If we have content, and the player is active, but the next tracks don't match, then skip
			if (!playQueue.isEmpty()
			&& player.getCurrentSong() != null
			&& !getCurrentSong().equals(player.getCurrentSong())) {
				System.out.println("CS: Issuing skip request");
				player.skipSong();
			}

			// Wait another 5 seconds before re-polling the API
			try { sleep(5000); } catch (InterruptedException ex) {}
		}
	}
}
