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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import taxonomy.model.Family;
import taxonomy.resources.client.TxDAOService;
import taxonomy.util.ORMTools;
import taxonomy.webui.client.model.VFamily;
import taxonomy.webui.client.model.VModel;
import taxonomy.webui.client.model.VResult;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public class TxDAOServiceImpl implements TxDAOService {

	@Override
	public String getName() {
		return TxDAOService.class.getName();
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
	public VModel getGeneric(String clazz, Integer id) throws Exception {
		VFamily f = (VFamily)Tools.serialize(VFamily.class.getName(), ORMTools.map(Family.class, id));
		return f;
	}

	@Override
	public VResult execute(String query) throws Exception {
		Connection con = ORMTools.getConnection();
		ResultSet rs = con.createStatement().executeQuery(query);
		VResult vresult = new VResult();
		while(rs.next()) {
			LinkedList<Object> holder = new LinkedList<Object>();
			int i = 1;
			while(true) {
				try {
					holder.addLast(rs.getObject(i));
					i++;
				} catch(SQLException e) {
					vresult.set(holder);
					break;
				}
			}
		}
		return vresult;
	}
}
