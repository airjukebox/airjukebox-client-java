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

/**
 * Basic Json header used commonly by all methods.
 * @author Aitor Ruano Miralles <0x077d@gmail.com>
 */
public class JsonHeader {
    public String session;
    public String serviceVersion;
    public boolean prefetchEnabled;
}
