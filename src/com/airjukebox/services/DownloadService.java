package com.airjukebox.services;

import com.airjukebox.common.FileOperations;

import jgroove.JGroovex;
import jgroove.json.CountryUtil;
import jgroove.jsonx.JsonGetSong.Result;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import java.lang.InterruptedException;

public class DownloadService extends Thread {

	{
		try {
			CountryUtil.initCountryCode();
			JGroovex.initiateQueue();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private List<String> downloadQueue = new LinkedList<String>();
	private Set<String> downloadContent = new TreeSet<String>();

	public void enqueue(String songID) {
		downloadQueue.add(songID);
	}

	public boolean isAvailable(String songID) {
		return downloadContent.contains(songID);
	}

	public void run() {

		// Find already downloaded content
		downloadContent.addAll(FileOperations.getSongsAvailable());

		while (true) {

			// Wait for the download queue to fill
			while (downloadQueue.size() == 0) {
				System.out.println("DS: Waiting for download requests ...");

				try { sleep(5000); } catch (InterruptedException ex) {}
			}

			try {
				// Take the next item from the download queue
				String songID = downloadQueue.remove(0);
				if (downloadContent.contains(songID)) continue;

				// Determine the file source
				Result songURL = JGroovex.getSongURL(songID).result;
				Object[] streamOptions = JGroovex.getSongStream(songURL.ip, songURL.streamKey);

				// Open a local file for writing
				File mp3 = FileOperations.getSongFile(songID);
				InputStream inStream = (InputStream)streamOptions[1];
				OutputStream outStream = new FileOutputStream(mp3);

				// Stream the file from source
				int len;
				byte[] buf = new byte[1024];

				while ((len = inStream.read(buf)) > 0) {
					outStream.write(buf, 0, len);
				}
				downloadContent.add(songID);
				JGroovex.markSongComplete(songURL.streamServerID, songURL.streamKey, songID);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
