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

import taxonomy.resources.client.model.VModel;
import taxonomy.resources.client.model.VResult;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
@RemoteServiceRelativePath("service")
public interface TxDAOService extends RemoteService, TxService {

	public Integer getMaxId(String tableName) throws Exception;
	
	public VModel getGeneric(String clazz, Integer id) throws Exception;
	
	public List<VModel> select(String tableName, Integer from, Integer to) throws Exception;
	
	public <M extends VModel> PagingLoadResult<M> select(String tableName, PagingLoadConfig config) throws Exception;
	
	public VResult query(String query) throws Exception;
	
	public void update(String query) throws Exception;
}
