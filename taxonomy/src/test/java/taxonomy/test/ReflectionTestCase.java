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
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import taxonomy.test.annotation.Field;
import taxonomy.test.annotation.Fields;
import taxonomy.test.annotation.Model;
import taxonomy.test.annotation.Model2;

/**
 * Author : Nguyen Thanh Hai
 *          haithanh0809@gmail.com
 * Apr 19, 2012  
 */
public class ReflectionTestCase extends TestCase {

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
