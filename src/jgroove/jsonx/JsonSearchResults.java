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

import java.util.HashMap;
import jgroove.json.JsonHeader;

/**
 * Json class representing the object returned by the Grooveshark's method
 * getSearchResultsEx. HashMap[] result contains the list of songs found with
 * they respective information when the string returned by callMethod is
 * deserialized.
 * @author Aitor Ruano Miralles <0x077d@gmail.com>
 */
public class JsonSearchResults {
    /**
     * Response of the method. HashMap[] result contains the list of songs
     */
    public static class Result {
        public HashMap<String, String>[] result;
        public String version;
        public boolean askForSuggestion;
    }
    
    public JsonHeader header;
    public Result result;
}
