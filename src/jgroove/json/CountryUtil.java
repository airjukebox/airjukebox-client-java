/*******************************************************************************
 * Copyright (c) 2011 Ale46.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/
package jgroove.json;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;

import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import jgroove.jsonx.JsonCountry;




public class CountryUtil {
	private static final String url = "http://grooveshark.com";
	public static JsonCountry country;
	
	private static StringBuilder getHTML(){

		StringBuilder data = new StringBuilder("");
		String line=null;
		URL address = null;
		try {
			address = new URL(url);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		HttpURLConnection connection = null;


		try {

			connection = (HttpURLConnection) address.openConnection();
			connection.setRequestProperty("accept", "text/html");
			connection.setRequestProperty("user-agent", "Mozilla/5.0 (compatible; MSIE 7.0b; Windows NT 6.0)");
		} catch (IOException e) {

			e.printStackTrace();
		}



		BufferedReader buffreader = null;
		try {
			buffreader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (IOException e2) {

			e2.printStackTrace();
		}

		try {
			line = buffreader.readLine();
		} catch (IOException e) {

			e.printStackTrace();
		}
		while (line!=null) {

			data.append(line);

			try {
				line = buffreader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		try {
			buffreader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		connection.disconnect();
		return data;

	}
	
	public static void  initCountryCode(){
		String json = (getHTML()).toString();
		int start = json.lastIndexOf("\"country\":{")+10;
		int end = json.indexOf("},",start)+1;
		
		json = json.substring(start,end);
		
		country = new Gson().fromJson(json, JsonCountry.class);

	}
	
	public JsonCountry getCountryCode(){
		return country;
	}
}
