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
package taxonomy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 * Apr 18, 2012
 */
public final class TaxonomyConnector {
	
	private final Connection con;
	
	public TaxonomyConnector() throws Exception {
		this("org.sqlite.JDBC", "jdbc:sqlite:src/main/resources/taxonomy.db", "sa", "");
	}
	
	public TaxonomyConnector(String clazzDriver, String dataSource, String username, String password) throws Exception {
		Class.forName(clazzDriver);
      con = DriverManager.getConnection(dataSource, username, password);
	}
	
	public ResultSet select(String query) throws SQLException {
		return con.createStatement().executeQuery(query);
	}
	
	public ResultSet select(PreparedStatement ps) throws SQLException {
		return ps.executeQuery();
	}
	
	public int update(String query) throws SQLException {
		return con.createStatement().executeUpdate(query);
	}
	
	public int update(PreparedStatement ps) throws SQLException {
		return ps.executeUpdate();
	}
}
