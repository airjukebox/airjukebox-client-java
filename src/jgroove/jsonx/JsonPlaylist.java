package jgroove.jsonx;

import java.util.HashMap;

import jgroove.json.JsonHeader;


public class JsonPlaylist {
    public static class Result {
        public HashMap<String, String>[] Playlists;
    }

    public JsonHeader header;
    public Result result;
}
