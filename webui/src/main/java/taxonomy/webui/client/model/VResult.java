/*
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package taxonomy.webui.client.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Author : Nguyen Thanh Hai
 *          haithanh0809@gmail.com
 * Jun 18, 2012  
 */
public class VResult implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private LinkedList<LinkedList<Object>> holder;
	
	public VResult() {
		this.holder = new LinkedList<LinkedList<Object>>();
	}
	
	public Object get(int i) {
		return holder.get(i);
	}
	
	public void set(LinkedList<Object> value) {
		holder.addLast(value);
	}
}
