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
package taxonomy.webui.client;

import taxonomy.webui.client.model.VFamily;
import taxonomy.webui.client.model.VModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public interface TaxonomyDAOServiceAsync {
	
	void getMaxId(String tableName, AsyncCallback<Integer> callback);

	void getFamily(Integer id, AsyncCallback<VFamily> callback);
	
	void getGeneric(String clazz, Integer id, AsyncCallback<VModel> callback);
	
	void getName(AsyncCallback<String> callback);
	
	public static class Util {
		private static TaxonomyDAOServiceAsync instance;
		
		public static TaxonomyDAOServiceAsync getInstance() {
			if(instance == null) {
				instance = GWT.create(TaxonomyDAOService.class);
				ServiceDefTarget target = (ServiceDefTarget) instance;
				target.setServiceEntryPoint(GWT.getModuleBaseURL() + "DAOService");
			}
			return instance;
		}
	}
}
