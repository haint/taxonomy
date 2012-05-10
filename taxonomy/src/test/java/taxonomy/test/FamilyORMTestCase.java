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
package taxonomy.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Properties;

import org.hsqldb.lib.InOutUtil;

import junit.framework.TestCase;
import sun.misc.IOUtils;
import taxonomy.model.Family;
import taxonomy.model.Kingdom;
import taxonomy.model.Locale;
import taxonomy.test.annotation.ORMTools;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 * Apr 18, 2012
 */
public class FamilyORMTestCase extends TestCase {

	public void testMappingFamily() throws Exception {
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("datasource.properties"));
		Class.forName(properties.getProperty("driver"));
		Connection con = DriverManager.getConnection(properties.getProperty("datasource"), properties.getProperty("username"), properties.getProperty("password"));
		
		ResultSet rs = con.createStatement().executeQuery(("Select * from [Family] where ID = 1"));
//		rs.next();
		Family f = (Family)ORMTools.map(Family.class, rs);
		assertNotNull(f);
		assertEquals(f.getId(), rs.getInt("ID"));
		assertEquals(f.getName(), rs.getString("NAME"));
		assertEquals(f.getDescription(), rs.getString("DESCRIPTION"));
		assertEquals(f.getAvatar(), rs.getString("AVATAR"));
		
		Kingdom kingdom = f.getKingdom();
		assertNotNull(kingdom);
		rs = con.createStatement().executeQuery("Select * from [Kingdom] where ID = " + rs.getInt("KINGDOM_ID"));
		assertTrue(rs.next());
		assertEquals(kingdom.getId(), rs.getInt("ID"));
		assertEquals(kingdom.getName(), rs.getString("Name"));
		assertEquals(kingdom.getCode(), rs.getString("CODE"));
		
		Iterator<Locale> iterator = f.getLocales();
		assertNotNull(iterator);
		while(iterator.hasNext()) {
			Locale lc = iterator.next();
			rs = con.createStatement().executeQuery(("Select * from [Locales] where ID = " + lc.getId()));
			assertTrue(rs.next());
			assertEquals(lc.getId(), rs.getInt("ID"));
			assertEquals(lc.getName(), rs.getString("NAME"));
			assertEquals(lc.getValue(), rs.getString("VALUE"));
		}
		con.close();
	}
	
	public void testUpdateFamily() throws Exception {
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("datasource.properties"));
		Class.forName(properties.getProperty("driver"));
		Connection con = DriverManager.getConnection(properties.getProperty("datasource"), properties.getProperty("username"), properties.getProperty("password"));
		
		ResultSet rs = con.createStatement().executeQuery(("Select count(ID) from [Family] where ID = 1"));
		Family f = (Family)ORMTools.map(Family.class, 1);
		assertNotNull(f);
		assertEquals(f.getId(), 1);
		assertEquals(rs.getInt(1), 1);
		con.close();

		f.setAvatar("avatar");
		f.setDescription("description");
		ORMTools.update(f, "kingdom", "avatar", "description", "locales");
		f = (Family)ORMTools.map(Family.class, 1);
		assertNotNull(f);
		assertEquals(f.getId(), 1);
		assertEquals(f.getAvatar(), "avatar");
		assertEquals(f.getDescription(), "description");
	}
}
