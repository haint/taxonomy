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

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import junit.framework.TestCase;
import taxonomy.model.Family;
import taxonomy.model.Kingdom;
import taxonomy.model.Locale;
import taxonomy.model.Model;
import taxonomy.util.LocaleResolver;
import taxonomy.util.ORMTools;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 * Apr 18, 2012
 */
public class FamilyORMTestCase extends TestCase {

	public void testMappingFamily() throws Exception {
		Connection con = ORMTools.getConnection();
		ResultSet rs = con.createStatement().executeQuery(("Select * from [Family] where ID = 1"));
//		rs.next();
		Family f = (Family)ORMTools.map(Family.class, rs);
		assertNotNull(f);
		assertEquals(f.getId().intValue(), rs.getInt("ID"));
		assertEquals(f.getName(), rs.getString("NAME"));
		assertEquals(f.getDescription(), rs.getString("DESCRIPTION"));
		assertEquals(f.getAvatar(), rs.getString("AVATAR"));
		
		Kingdom kingdom = f.getKingdom();
		assertNotNull(kingdom);
		rs = con.createStatement().executeQuery("Select * from [Kingdom] where ID = " + rs.getInt("KINGDOM_ID"));
		assertTrue(rs.next());
		assertEquals(kingdom.getId().intValue(), rs.getInt("ID"));
		assertEquals(kingdom.getName(), rs.getString("Name"));
		assertEquals(kingdom.getCode(), rs.getString("CODE"));
		
		Iterator<Locale> iterator = f.getLocales();
		assertNotNull(iterator);
		while(iterator.hasNext()) {
			Locale lc = iterator.next();
			rs = con.createStatement().executeQuery(("Select * from [Locales] where ID = " + lc.getId()));
			assertTrue(rs.next());
			assertEquals(lc.getId().intValue(), rs.getInt("ID"));
			assertEquals(lc.getName(), rs.getString("NAME"));
			assertEquals(lc.getValue(), rs.getString("VALUE"));
		}
		con.close();
		
		JAXBContext jc = JAXBContext.newInstance(Family.class);
		Marshaller m = jc.createMarshaller();
		m.marshal(f, System.out);
	}
	
	public void testSelectLastInsertRowId() throws Exception {
		Connection con = ORMTools.getConnection();
		con.createStatement().executeUpdate("INSERT INTO [Locales] VALUES (NULL, 'vn', 'test')");
		ResultSet rs = con.createStatement().executeQuery("Select last_insert_rowid() from [Locales]");
		assertEquals(true, rs.next());
		con.close();
	}
	
	public void testInsertFamily() throws Exception {
		Family f = new Family();
		f.setName("Test insert family");
		f.setAvatar("avatar");
		f.setDescription("description");
		f.setKingdom(new Kingdom().setId(111));
		Set<Locale> locales = new HashSet<Locale>();
		Collections.addAll(locales, new Locale().setId(12345), new Locale().setId(6789));
		f.setLocales(locales);
		ORMTools.insert(f);
		
		Connection con = ORMTools.getConnection();
		ResultSet rs = con.createStatement().executeQuery("Select max(ID) from [Family]");
		int maxId = rs.getInt(1);
		rs = con.createStatement().executeQuery("Select * from [Family] where ID = " + maxId);
		assertEquals(maxId, rs.getInt("ID"));
		assertEquals(f.getAvatar(), rs.getString("AVATAR"));
		assertEquals(f.getDescription(), rs.getString("DESCRIPTION"));
		assertEquals(f.getKingdom().getId().intValue(), rs.getInt("KINGDOM_ID"));
		assertEquals(LocaleResolver.resolve(f.getLocales()), rs.getString("LOCALE_IDS"));
		con.close();
	}
	
	public void testUpdateFamily() throws Exception {
		Connection con = ORMTools.getConnection();
		ResultSet rs = con.createStatement().executeQuery(("Select count(ID) from [Family] where ID = 1"));
		Family f = (Family)ORMTools.map(Family.class, 1);
		assertNotNull(f);
		assertEquals(f.getId().intValue(), 1);
		assertEquals(rs.getInt(1), 1);
		con.close();

		f.setAvatar("avatar");
		f.setDescription("description");
		
		Iterator<Locale> i = f.getLocales();
		Set<Locale> holder = new HashSet<Locale>();
		int foo = 0;
		while(i.hasNext()) {
			Locale locale = i.next();
			foo += locale.getId();
			locale.setId(locale.getId() + 1);
			holder.add(locale);
		}
		f.setLocales(holder);
		ORMTools.update(f, "kingdom", "avatar", "description", "locales");
		f = (Family)ORMTools.map(Family.class, 1);
		assertNotNull(f);
		assertEquals(f.getId().intValue(), 1);
		assertEquals(f.getAvatar(), "avatar");
		assertEquals(f.getDescription(), "description");
		
		i = f.getLocales();
		int bar = 0;
		while(i.hasNext()) {
			bar += i.next().getId();
		}
		assertEquals(foo + 2, bar);
	}
}