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

import junit.framework.TestCase;
import taxonomy.factory.FamilyModelFactory;
import taxonomy.factory.TaxonomyFactory;
import taxonomy.model.Family;

/**
 * Author : Nguyen Thanh Hai
 *          haithanh0809@gmail.com
 * Apr 19, 2012  
 */
public class ReflectionTestCase extends TestCase {

	public void testFamily() throws Exception {
		FamilyModelFactory factory = (FamilyModelFactory) TaxonomyFactory.getInstance().getFactory(Family.class);
		assertNotNull(factory);
		Family family = factory.getModelById("1");
		Object obj = Family.class.newInstance();
		Method[] methods = obj.getClass().getMethods();
		for(Method m : methods) {
			if(m.getName().equals("getClass")) continue;
			if(m.getName().startsWith("get")) {
				System.out.println(m.getName() + " :: " + m.invoke(family, new Object[] {}));
			}
		}
	}
}
