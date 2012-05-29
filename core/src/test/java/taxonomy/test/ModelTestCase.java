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

import java.lang.reflect.Method;
import java.util.Set;

import junit.framework.TestCase;

import taxonomy.annotation.OneToOne;
import taxonomy.test.model.MockModel;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 * May 15, 2012
 */
public class ModelTestCase extends TestCase {

	public void testMapStringToSet() {
		MockModel mock = new MockModel("foo", "bar", "juu");
		mockMapTools(mock);
	}
	
	private void mockMapTools(MockModel mock) {
		Method[] methods = mock.getClass().getMethods();
		for(Method m : methods) {
			OneToOne foo = m.getAnnotation(OneToOne.class);
			if(foo != null) {
				Class<?> returnType = m.getReturnType();
				Class<?> param = m.getParameterTypes()[0];
				System.out.println(returnType.getName());
				System.out.println(param.getName());
				System.out.println(Set.class.getName());
			}
		}
	}
}
