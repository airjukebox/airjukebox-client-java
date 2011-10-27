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

package jgroove.json;

import java.util.HashMap;

import java.io.IOException;

import jgroove.JGroove;
import jgroove.jsonx.JsonCountry;

/**
 * Class that represents the basic Json post object to make the petition to a
 * Grooveshark's service, it is mainly used by callMethod and should not be
 * used out of there but if you want to complicate your life.
 * @author Aitor Ruano Miralles <0x077d@gmail.com>
 */
public class JsonPost {
	public final HashMap<String, Object> header = new HashMap<String, Object>();
	{
		header.put("country", country);
		header.put("uuid", JGroove.uuid);
		header.put("privacy", 0);
		header.put("session", JGroove.getCurrentSessionID());
	}
	public static final HashMap<String, String> country = new HashMap<String, String>();
	static {
		JsonCountry jcountry = CountryUtil.country;
		country.put("ID", jcountry.ID);
		country.put("CC1", jcountry.CC1);
		country.put("CC2", jcountry.CC2);
		country.put("CC3", jcountry.CC3);
		country.put("CC4", jcountry.CC4);
		country.put("IPR", jcountry.IPR);
	}
	public HashMap<String, Object> parameters;
	public String method;
	

	/**
	 * Attach the parameters and the method to the JsonPost abstract class to
	 * send it via http, it will also automatically get the session id if it
	 * hasn't been done already
	 * @param parameters Paramaters to post
	 * @param method Method to call
	 */
	public JsonPost(HashMap<String, Object> parameters, String method) throws IOException{
		this.parameters = parameters;
		this.method = method;

		//System.out.println("DEBUGGGGG:"+method);

		if (method.equalsIgnoreCase("getStreamKeyFromSongIDEx")  || 
			method.equalsIgnoreCase("markSongComplete") || 
			method.equalsIgnoreCase("markSongDownloadedEx")||
			method.equalsIgnoreCase("markStreamKeyOver30Seconds"))
		{
			
			this.header.put("client", JGroove.nameJS);
			this.header.put("clientRevision", JGroove.versionJS);
			JGroove.password = "theTicketsAreNowDiamonds";
		}
		else if (method.equalsIgnoreCase("getSearchResultsEx")||
				method.equalsIgnoreCase("authenticateUser")|| 
				method.equalsIgnoreCase("playlistAddSongToExisting") || 
				method.equalsIgnoreCase("createPlaylist") ||
				method.equalsIgnoreCase("popularGetSongs") || 
				method.equalsIgnoreCase("playlistGetSongs") || 
				method.equalsIgnoreCase("initiateQueue") || 
				method.equalsIgnoreCase("userAddSongsToLibrary") || 
				method.equalsIgnoreCase("userGetPlaylists")||
				method.equalsIgnoreCase("userGetSongsInLibrary")||
				method.equalsIgnoreCase("getCommunicationToken")||
				method.equalsIgnoreCase("authenticateUser")||
				method.equalsIgnoreCase("getFavorites")||
				method.equalsIgnoreCase("favorite")||
				method.equalsIgnoreCase("albumGetSongs")){
			
			this.header.put("client", JGroove.nameHTML);
			this.header.put("clientRevision", JGroove.versionHTML);
			JGroove.password = "imOnAHorse";
		}
		
		if (JGroove.getCurrentSessionID().isEmpty()){
			this.header.put("session", JGroove.getSessionID());
		} else {
			this.header.put("session", JGroove.getCurrentSessionID());
		}
		
		
	}
}
