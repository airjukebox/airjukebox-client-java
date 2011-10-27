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

package jgroove.jsonx;

import jgroove.json.JsonHeader;

/**
 * Json class representing the object returned by the Grooveshark's method
 * getSongFromIDEx. streamKey and ip are the fields needed to retrieve a song.
 * @author Aitor Ruano Miralles <0x077d@gmail.com>
 */
public class JsonGetSong {
    /**
     * Response of the method. Ip contains the url to the host that has the song,
     * streamKey is the key needed to retrieve it
     */
    public static class Result{
        public int uSecs;
        public String FileToken;
        public String streamKey;
        public int streamServerID;
        public String ip;
    }

    public JsonHeader header;
    public Result result;
}
