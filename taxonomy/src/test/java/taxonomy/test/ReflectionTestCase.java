/*
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package taxonomy.test;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;
import taxonomy.db.TaxonomyConnector;
import taxonomy.model.Family;
import taxonomy.model.Kingdom;
import taxonomy.model.Locale;
import taxonomy.test.annotation.Field;
import taxonomy.test.annotation.Fields;
import taxonomy.test.annotation.Model;
import taxonomy.test.annotation.Model2;
import taxonomy.test.annotation.ORMByAnnotaionFactory;

/**
 * Author : Nguyen Thanh Hai
 *          haithanh0809@gmail.com
 * Apr 19, 2012  
 */
public class ReflectionTestCase extends TestCase {

	public void testMappingFamily() throws Exception {
		TaxonomyConnector connector = new TaxonomyConnector();
		ResultSet rs = connector.select("Select * from [Family] where ID = 1");
//		rs.next();
		Family f = (Family)ORMByAnnotaionFactory.mapToModel(Family.class, rs);
		assertNotNull(f);
		assertEquals(f.getId(), rs.getInt("ID"));
		assertEquals(f.getName(), rs.getString("NAME"));
		assertEquals(f.getDescription(), rs.getString("DESCRIPTION"));
		assertEquals(f.getAvatar(), rs.getString("AVATAR"));
		
		Kingdom kingdom = f.getKingdom();
		assertNotNull(kingdom);
		rs = connector.select("Select * from [Kingdom] where ID = " + rs.getInt("KINGDOM_ID"));
		assertTrue(rs.next());
		assertEquals(kingdom.getId(), rs.getInt("ID"));
		assertEquals(kingdom.getName(), rs.getString("Name"));
		assertEquals(kingdom.getCode(), rs.getString("CODE"));
		
		Iterator<Locale> iterator = f.getLocales();
		assertNotNull(iterator);
		while(iterator.hasNext()) {
			Locale lc = iterator.next();
			rs = connector.select("Select * from [Locales] where ID = " + lc.getId());
			assertTrue(rs.next());
			assertEquals(lc.getId(), rs.getInt("ID"));
			assertEquals(lc.getName(), rs.getString("NAME"));
			assertEquals(lc.getValue(), rs.getString("VALUE"));
		}
	}
	
	public void testPersistFamily() throws Exception {
		TaxonomyConnector connector = new TaxonomyConnector();
		ResultSet rs = connector.select("Select * from [Family] where ID = 1");
		Family f = (Family)ORMByAnnotaionFactory.mapToModel(Family.class, rs);
	}
	
	public void testAnnotaion() throws Exception {
		Set<Model2> set = new HashSet<Model2>();
		set.add(new Model2(1));
		set.add(new Model2(2));
		
		Class<?> clazz = Class.forName(Model.class.getName());
		Object obj = clazz.newInstance();
		Method[] methods = clazz.getMethods();
		for(Method m : methods) {
			if(!m.getName().startsWith("set")) continue;
			Field f;
			Fields fs;
			if((f = m.getAnnotation(Field.class)) != null) {
				assertEquals("Foo_", f.value());
				assertSame(String.class.getName(), m.getParameterTypes()[0].getName());
				 m.invoke(obj, "fooInject");
			}
			
			if((fs = m.getAnnotation(Fields.class)) != null) {
				assertEquals(Model2.class, fs.mapTo());
				assertEquals("Bar_IDs", fs.value());
				assertSame(Set.class.getName(), m.getParameterTypes()[0].getName());
				m.invoke(obj, set);
			}
		}
		Method m = clazz.getMethod("getFoo");
		assertEquals("fooInject", m.invoke(obj, new Object[] {}));
		
		m = clazz.getMethod("getBar");
		Set<Model2> result = (Set<Model2>) m.invoke(obj, new Object[] {});
		assertEquals(result.size(), 2);
	}
}
