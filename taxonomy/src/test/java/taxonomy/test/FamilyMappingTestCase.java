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

import java.util.List;

import taxonomy.factory.FamilyModelFactory;
import taxonomy.factory.TaxonomyFactory;
import taxonomy.model.Family;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 * Apr 18, 2012
 */
public class FamilyMappingTestCase extends TestCase {

	public void testSelect() throws Exception {
		FamilyModelFactory factory = (FamilyModelFactory) TaxonomyFactory.getInstance().getFactory(Family.class);
		assertNotNull(factory);
		Family family = factory.getModelById("1");
		assertNotNull(family);
		assertEquals(family.getId(), 1);
		List<Family> list = factory.getAll();
		assertNotNull(list);
		assertEquals(list.size(), 20);
	}
}
