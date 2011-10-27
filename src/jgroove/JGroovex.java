/**
 *   This file is part of JGroove.
 *
 *   JGroove is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   JGroove is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with JGroove. If not, see <http://www.gnu.org/licenses/>.
 **/

package jgroove;



import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import java.net.URL;
import java.net.URLConnection;
import java.io.OutputStreamWriter;


import java.net.MalformedURLException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import jgroove.jsonx.JsonFav;
import jgroove.jsonx.JsonGetSong;
import jgroove.jsonx.JsonPlaylist;
import jgroove.jsonx.JsonPlaylistSongs;
import jgroove.jsonx.JsonSearchResults;
import jgroove.jsonx.JsonSongs;
import jgroove.jsonx.JsonUser;

/**
 * Abstract class with easy and quick methods to communicate and have real fun
 * with Grooveshark
 * @author Aitor Ruano Miralles <0x077d@gmail.com>
 */
public class JGroovex {

	/**
	 * Returns the results of a search from the result of calling
	 * Grooveshark's method getSearchResultsEx
	 * @param query Words to search
	 * @param type The type of result you want (Songs, Artits, Albums, Playlists...)
	 * @return Json object with al info of the response
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static JsonSearchResults getSearchResults(String query, String type) throws IOException {

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("country", jgroove.json.JsonPost.country);
		parameters.put("query", query);
		parameters.put("type", type);
		parameters.put("guts", 0);
		parameters.put("ppOverride", false);

		String response = JGroove.callMethod(parameters, "getSearchResultsEx");
		return (new Gson().fromJson(response, JsonSearchResults.class));
	}

	/**
	 * Returns the songs of the artist given its ID
	 * @param artistid ID of the artist
	 * @param offset Displacement of the songs (Starting song)
	 * @param isverified Songs verified or not
	 * @return Json object with all info of the response
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static JsonSongs getArtistSongs(String artistid, int offset, boolean isverified) throws IOException {

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("country", jgroove.json.JsonPost.country);
		parameters.put("offset", offset);
		parameters.put("artistID", artistid);
		parameters.put("isVerified", isverified);

		String response = JGroove.callMethod(parameters, "artistGetSongs");
		return (new Gson().fromJson(response, JsonSongs.class));
	}

	/**
	 * Returns the songs of the album given its ID
	 * @param albumid ID of the album
	 * @param offset Displacement of the songs (Starting song)
	 * @param isverified Songs verified or not
	 * @return Json object with all info of the response
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static JsonSongs getAlbumSongs(String albumid, int offset, boolean isverified) throws IOException {

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("offset", offset);
		parameters.put("albumID", albumid);
		parameters.put("isVerified", isverified);
		// parameters.put("country", jgroove.json.JsonPost.country);
		String response = JGroove.callMethod(parameters, "albumGetSongs");
		return (new Gson().fromJson(response, JsonSongs.class));
	}
	
	public static JsonPlaylistSongs userGetSongsInLibrary(String userID, int page) throws IOException {

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("userID", userID);
		parameters.put("page", page);

		// parameters.put("country", jgroove.json.JsonPost.country);
		String response = JGroove.callMethod(parameters, "userGetSongsInLibrary");
		System.out.println(response);
		return (new Gson().fromJson(response, JsonPlaylistSongs.class));
	}
	
	public static JsonFav getFavorites(String userID) throws IOException {

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("userID", userID);
		parameters.put("ofWhat", "Songs");

		// parameters.put("country", jgroove.json.JsonPost.country);
		String response = JGroove.callMethod(parameters, "getFavorites");
		System.out.println(response);
		return   (new Gson().fromJson(response, JsonFav.class));
		


	}

	/**
	 * Returns the songs of the playlist given its ID
	 * @param listid ID of the playlist
	 * @param offset Displacement of the songs (Starting song)
	 * @param isverified Songs verified or not
	 * @return Json object with all info of the response
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static JsonPlaylistSongs getPlaylistSongs(String listid, int offset, boolean isverified) throws IOException {

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("country", jgroove.json.JsonPost.country);
		parameters.put("offset", offset);
		parameters.put("playlistID", listid);
		parameters.put("isVerified", isverified);

		String response = JGroove.callMethod(parameters, "playlistGetSongs");
		
		return (new Gson().fromJson(response, JsonPlaylistSongs.class));
	}

	/**
	 * Returns a list of Popular Songs
	 * @param type "monthly" or null to "daily"
	 * @return Json Object that contains the list of popular songs
	 * @throws IOException
	 */
	public static JsonPlaylistSongs getPopularSongs(String type) throws IOException{
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("country", jgroove.json.JsonPost.country);
		parameters.put("type", type);
		String response = JGroove.callMethod(parameters, "popularGetSongs");
		return (new Gson().fromJson(response, JsonPlaylistSongs.class));
	}

	/**
	 * Must be called before retrieving a stream to avoid Grooveshark to blacklist
	 * your IP. All the needed params are given by getSongURL
	 * @param servid StreamServerID
	 * @param streamkey StreamKey
	 * @param songid Song ID
	 * @throws IOException
	 */

	public static void markSongAsDownloaded(int servid, String streamkey, String songid) throws IOException{
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("country", jgroove.json.JsonPost.country);
		parameters.put("streamServerID", servid);
		parameters.put("streamKey", streamkey);
		parameters.put("songID", songid);
		JGroove.callMethod(parameters, "markSongDownloadedEx");
	}

	/**
	 * Must be called once the stream has finished to avoid Grooveshark to blacklist
	 * your IP. All the needed params are given by getSongURL
	 * @param servid StreamServerID
	 * @param streamkey StreamKey
	 * @param songid Song ID
	 * @throws IOException
	 */
	public static void markSongComplete(int servid, String streamkey, String songid) throws IOException{
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("country", jgroove.json.JsonPost.country);
		parameters.put("streamServerID", servid);
		parameters.put("streamKey", streamkey);
		parameters.put("songID", songid);
		JGroove.callMethod(parameters, "markSongComplete");
	}

	public static JsonUser authenticateUser(String username, String password) throws IOException{
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);
		parameters.put("password", password);
		parameters.put("savePassword", 1);
		JGroove.methodurl = "https"+ "://"  + JGroove.domain+ "/"+ JGroove.methodphp;
		String response = JGroove.callMethod(parameters, "authenticateUser");
		JGroove.methodurl = "http"+ "://"  + JGroove.domain+ "/"+  JGroove.methodphp;
		
		return (new Gson().fromJson(response, JsonUser.class));
	}

	public static void initiateQueue () throws IOException{
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("country", jgroove.json.JsonPost.country);
		JGroove.callMethod(parameters, "initiateQueue");
		
	}
	

	public static void createPlaylist(String playlistName, String playlistAbout, String[] ids) throws IOException{
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("country", jgroove.json.JsonPost.country);
		parameters.put("playlistName", playlistName);
		parameters.put("songIDs", ids);
		parameters.put("playlistAbout", playlistAbout);

		JGroove.callMethod(parameters, "createPlaylist");
		
	}
	
	public static void playlistAddSongToExisting(String playlistID, String songID) throws IOException{
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("country", jgroove.json.JsonPost.country);
		parameters.put("playlistID", playlistID);
		parameters.put("songID", songID);
		

		JGroove.callMethod(parameters, "playlistAddSongToExisting");
		
	}
	
	public static void userAddSongsToLibrary(String songID, String songName, String albumID,  String albumName, String artistID, String artistName, String artFilename, String trackNum) throws IOException{
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("country", jgroove.json.JsonPost.country);
		
		HashMap<String,String> songs = new HashMap<String,String>();
		
		songs.put("songID", songID);
		songs.put("songName", songName);
		songs.put("albumID", albumID);
		songs.put("albumName", albumName);
		songs.put("artistID", artistID);
		songs.put("artistName", artistName);
		songs.put("artFilename", artFilename);
		songs.put("track", trackNum);
		
		List<HashMap<String,String>> ok = new ArrayList<HashMap<String,String>>();
		ok.add(songs);
		parameters.put("songs", ok);
	

		JGroove.callMethod(parameters, "userAddSongsToLibrary");
		
	}
	
	public static void favorite(String songID, String songName, String albumID,  String albumName, String artistID, String artistName, String artFilename, String trackNum) throws IOException{
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		//parameters.put("country", jgroove.json.JsonPost.country);
		
		HashMap<String,String> details = new HashMap<String,String>();
		
		details.put("songID", songID);
		details.put("songName", songName);
		details.put("albumID", albumID);
		details.put("albumName", albumName);
		details.put("artistID", artistID);
		details.put("artistName", artistName);
		details.put("artFilename", artFilename);
		details.put("track", trackNum);
		parameters.put("what", "Song");
		parameters.put("ID", songID);
		parameters.put("details", details);
	

		System.out.println(JGroove.callMethod(parameters, "favorite"));
		
	}
	
	public static JsonPlaylist userGetPlaylists(String userID) throws IOException{
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("country", jgroove.json.JsonPost.country);
		parameters.put("userID", userID);
		String response = (JGroove.callMethod(parameters, "userGetPlaylists"));
		
		return (new Gson().fromJson(response, JsonPlaylist.class));
		
	}
	

	public static void markStreamKeyOver30Seconds(int songQueueID, int servid, String streamkey, String songid) throws IOException{
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("songQueueID", songQueueID);
		parameters.put("songQueueSongID", 1);
		parameters.put("streamServerID", servid);
		parameters.put("streamKey", streamkey);
		parameters.put("songID", songid);
		JGroove.callMethod(parameters, "markStreamKeyOver30Seconds");
	}

	/**
	 * Returns info about where te get the song stream in a Json Object
	 * @param songid The song ID to obtain information about
	 * @return JsonGetSong object that contains the streamKey and the IP where
	 * to retrieve the song
	 * @throws IOException
	 */
	public static JsonGetSong getSongURL(String songid) throws IOException{
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("songID", songid);
		parameters.put("country", jgroove.json.JsonPost.country);
		parameters.put("mobile", false);
		parameters.put("prefetch", false);

		String response = JGroove.callMethod(parameters, "getStreamKeyFromSongIDEx");
		return (new Gson().fromJson(response, JsonGetSong.class));
	}

	/**
	 * Returns the song audio stream corresponded to the streamKey passed, it can be
	 * used to store it on disk or play it.
	 * IMPORTANT!: When using this method markSongAsDownloaded and
	 * markSongComplete should be used to avoid Grooveshark heuristics to
	 * detect this as an attack.
	 * @param ip IP of the host where the song is stored
	 * @param streamKey streamKey that identifies the song
	 * @return Audio stream of the song (InputStream)
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedAudioFileException
	 */
	public static Object[] getSongStream(String ip, String streamKey) throws IOException {
		URL url = new URL("http://"+ ip + "/" + JGroove.streamphp + "?");
		String data = "streamKey=" + streamKey;
		Object[] results = new Object[2];
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setDoOutput(true);

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

		wr.write(data);
		wr.flush();
		wr.close();
		
		results[0] = conn.getContentLength();
		results[1]=conn.getInputStream();
		return results;
	}
}
