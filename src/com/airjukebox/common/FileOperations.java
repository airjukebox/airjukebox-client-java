package com.airjukebox.common;

import java.io.File;

import java.util.List;
import java.util.LinkedList;

public class FileOperations {
	public static File getSongFile(String songID) {
		String libraryPath = System.getProperty("airjukebox.library.path", "data");
		File libraryDir = new File(libraryPath);

		if (!libraryDir.exists()) {
			libraryDir.mkdir();
		}

		return new File(libraryPath + System.getProperty("file.separator") + songID + ".mp3");
	}

	public static List<String> getSongsAvailable() {
		List<String> results = new LinkedList<String>();

		String libraryPath = System.getProperty("airjukebox.library.path", "data");
		File libraryDir = new File(libraryPath);

		if (libraryDir.exists()) {
			for (String filename : libraryDir.list()) {
				results.add(filename.replace(".mp3", ""));
			}
		}
		return results;
	}
}
