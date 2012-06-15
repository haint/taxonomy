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

import java.sql.Connection;
import java.sql.ResultSet;

import taxonomy.model.Family;
import taxonomy.util.ORMTools;
import taxonomy.webui.client.TaxonomyDAOService;
import taxonomy.webui.client.model.VFamily;
import taxonomy.webui.client.model.VModel;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public class TaxonomyDAOServiceImpl implements TaxonomyDAOService {

	@Override
	public String getName() {
		return TaxonomyDAOService.class.getName();
	}

	@Override
	public Integer getMaxId(String tableName) throws Exception {
		Connection con = ORMTools.getConnection();
		ResultSet rs = con.createStatement().executeQuery("Select max(ID) from " + tableName);
		rs.next();
		int maxId = rs.getInt(1);
		con.close();
		return maxId;
	}

	@Override
	public VFamily getFamily(Integer id) throws Exception {
		VFamily f = new VFamily();
		Family model = (Family)ORMTools.map(Family.class, id);
		f.setId(model.getId());
		f.setAvatar(model.getAvatar());
		f.setDescription(model.getDescription());
		f.setName(model.getName());
		return f;
	}

	@Override
	public VModel getGeneric(String clazz, Integer id) throws Exception {
		VFamily f = (VFamily)Tools.serialize(VFamily.class.getName(), ORMTools.map(Family.class, id));
		System.out.println(f);
		return f;
	}
}
