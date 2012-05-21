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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import junit.framework.TestCase;

import org.reflections.Reflections;

import taxonomy.model.Model;
import taxonomy.model.NaturalObject;
import taxonomy.util.ORMTools;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 * 
 *          May 17, 2012
 */
public class GenericORMTestCase extends TestCase {

	public void testDateFormater() throws ParseException {
		Date date = NaturalObject.DATE_FORMATER.parse("11/11/1998 09:29:29");
		assertEquals(NaturalObject.DATE_FORMATER.format(date), "11/11/1998 09:29:29");
	}

	public void _testMap() throws Exception {
		Reflections reflections = new Reflections(Model.class.getPackage().getName());
		Set<Class<? extends Model>> subTypes = reflections.getSubTypesOf(Model.class);

		Iterator<Class<? extends Model>> i = subTypes.iterator();
		while (i.hasNext()) {
			Class clazz = i.next();
			Model model = ORMTools.map(clazz, 1);
			assertNotNull(model);
			assertEquals(clazz, model.getClass());
		}
	}
	
	public void testNaturalObject() throws Exception {
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("datasource.properties"));
		Class.forName(properties.getProperty("driver"));
		Connection con =
			DriverManager.getConnection(properties.getProperty("datasource"), properties.getProperty("username"),
				properties.getProperty("password"));
		
		ResultSet rs = con.createStatement().executeQuery("Select count(id) from NaturalObject");
		rs.next();
		int count = rs.getInt(1);
		System.out.println(count);
		for(int i = 1; i <= count; i++) {
			NaturalObject obj = (NaturalObject)ORMTools.map(NaturalObject.class, i);
			assertNotNull(obj);
		}
	}
}
