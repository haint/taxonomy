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
package taxonomy.test.annotation;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 * Apr 19, 2012
 */
public class Model {

	String foo;
	
	Set<Model2> bar;
	
	@Field("Foo_")
	public void setFoo(String foo) {
		this.foo = foo;
	}
	
	@Field("Foo_")
	public String getFoo() {
		return foo;
	}
	
	@Fields(mapTo = Model2.class, value = "Bar_IDs")
	public void setBar(Set<Model2> bar) {
		this.bar = bar;
	}
	
	@Fields(mapTo = Model2.class, value = "Bar_IDs")
	public Set<Model2> getBar() {
		return bar;
	}
}
