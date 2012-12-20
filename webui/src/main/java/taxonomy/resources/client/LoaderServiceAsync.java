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

import java.util.List;

import taxonomy.resources.client.model.FilterModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 * 
 */
public interface LoaderServiceAsync {
  void load(String tableName, AsyncCallback<List<FilterModel>> callback);

  /**
   * Specify to load the locale name from VNaturalObject
   * 
   * @return
   */
  void localeName(String locale, AsyncCallback<List<String>> callback);

  public static class Util {
    private static LoaderServiceAsync instance;

    public static LoaderServiceAsync getInstance() {
      if (instance == null) {
        instance = GWT.create(LoaderService.class);
        ServiceDefTarget service = (ServiceDefTarget)instance;
        service.setServiceEntryPoint(GWT.getModuleBaseURL() + "loader");
      }
      return instance;
    }
  }
}
