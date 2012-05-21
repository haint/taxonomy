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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 * May 18, 2012
 */
public class HSqlDBTestCase extends TestCase {

	public void test() throws Exception {
		Class.forName("org.hsqldb.jdbcDriver");
		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:target/hsqldb/test", "sa", "");
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream("src/main/resources/hsql-createTable.sql"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int available = -1;
		while((available = bis.read(buff)) > -1) {
			baos.write(buff, 0, available);
		}
		con.createStatement().execute(new String(baos.toByteArray()));
		con.createStatement().executeUpdate("Insert into kingdom values(null, 'hà nội', 'Hải Phòng')");
		ResultSet rs = con.createStatement().executeQuery("Select * from kingdom");
		rs.next();
		assertEquals(1, rs.getInt(1));
		assertEquals("hà nội", rs.getString(2));
		assertEquals("Hải Phòng", rs.getString(3));
		
		con.createStatement().executeUpdate("Insert into TAG values(NULL, 'Tag', NULL)");
      ResultSet tmp = con.createStatement().executeQuery("Select MAX(ID) from Tag");
     tmp.next();
      assertEquals(1, tmp.getInt(1));
      con.createStatement().execute("SHUTDOWN");
      con.close();
	}
	
	public void test2() throws Exception {
		Class.forName("org.hsqldb.jdbcDriver");
		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:target/hsqldb/db", "sa", "");
		ResultSet rs = con.createStatement().executeQuery("SELECT MAX(ID) from Kingdom");
		rs.next();
		assertEquals(2, rs.getInt(1));
		rs.close();
		rs = con.createStatement().executeQuery("SELECT MAX(ID) from NaturalObject");
		rs.next();
		assertEquals(3711, rs.getInt(1));
		
		rs = con.createStatement().executeQuery(("Select * from Family where ID = 1"));
		rs.next();
		assertEquals(1, rs.getInt(1));
	}
}
