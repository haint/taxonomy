/*
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package taxonomy.resources.server;

import java.util.HashMap;
import java.util.Map;

import taxonomy.resources.client.TxService;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public final class TxServices {
	private final Map<String, TxService> holder = new HashMap<String, TxService>();
	
	private static TxServices singleton;
	
	TxServices(TxService ... services)  {
		for(TxService service : services) {
			holder.put(service.getName(), service);
		}
	}
	
	public TxService getService(String key) {
		return holder.get(key);
	}
	
	public final static TxServices getInstance() {
		return singleton;
	}
	
	final static void setInstance(TxServices instance) {
		singleton = instance;
	}
}
