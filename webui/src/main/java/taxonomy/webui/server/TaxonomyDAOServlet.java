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
package taxonomy.webui.server;

import taxonomy.webui.client.TaxonomyDAOService;
import taxonomy.webui.client.model.VModel;
import taxonomy.webui.client.model.VResult;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public class TaxonomyDAOServlet extends RemoteServiceServlet implements TaxonomyDAOService {

	private static final long	serialVersionUID	= 1L;

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Integer getMaxId(String tableName) throws Exception {
		TaxonomyDAOService service =(TaxonomyDAOService)TaxonomyServices.getInstance().getService(TaxonomyDAOService.class.getName());
		return service.getMaxId(tableName);
	}

	@Override
	public VModel getGeneric(String clazz, Integer id) throws Exception {
		TaxonomyDAOService service =(TaxonomyDAOService)TaxonomyServices.getInstance().getService(TaxonomyDAOService.class.getName());
		return service.getGeneric(clazz, id);
	}

	@Override
	public VResult execute(String query) throws Exception {
		TaxonomyDAOService service =(TaxonomyDAOService)TaxonomyServices.getInstance().getService(TaxonomyDAOService.class.getName());
		return service.execute(query);
	}
}
