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
package taxonomy.resources.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public interface MockServiceAsync {

	void sayHello(AsyncCallback<String> callback);
	
	void getName(AsyncCallback<String> callback);
	
	public static class Util {
		private static MockServiceAsync instance;
		
		public static MockServiceAsync getInstance() {
			if(instance == null) {
				instance = GWT.create(MockService.class);
				ServiceDefTarget target = (ServiceDefTarget)instance;
				target.setServiceEntryPoint(GWT.getModuleBaseURL() + "MockService");
			}
			return instance;
		}
	}
}
