package com.airjukebox;

import com.airjukebox.services.*;

import java.io.IOException;
import java.util.Properties;
import java.lang.ClassLoader;

public class Main {

	public static void main(String[] args) {

		// Load airjukebox application settings
		Properties properties = new Properties(System.getProperties());
		try { properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("airjukebox.properties")); }
		catch (IOException ex) {}
		System.setProperties(properties);

		// Instantiate each of the application service threads
		ChannelService channel = new ChannelService();
		DownloadService download = new DownloadService();
		PlayerService player = new PlayerService();

		// Thread communication setup
		channel.setPlayerService(player);
		channel.setDownloadService(download);
		player.setDownloadService(download);
		player.setChannelService(channel);

		// Start application threads
		player.start();
		download.start();
		channel.start();
	}

}
