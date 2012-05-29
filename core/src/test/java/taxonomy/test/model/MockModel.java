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
package taxonomy.test.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import taxonomy.annotation.OneToOne;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 * May 15, 2012
 */
public class MockModel {
	
	private Set<String> values;
	
	public MockModel(String ... values) {
		this.values = new HashSet<String>();
		Collections.addAll(this.values, values);
	}
	
	@OneToOne("Mock")
	public Set<String> setValues(String v) {
		String[] arr = v.split("::");
		Collections.addAll(values, arr);
		return values;
	}
	
	public Set<String> getValues() {
		return values;
	}
}
