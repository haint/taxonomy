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

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import taxonomy.annotation.OneToMany;
import taxonomy.annotation.OneToOne;
import taxonomy.annotation.Table;
import taxonomy.db.TaxonomyConnector;
import taxonomy.model.IModel;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 * Apr 19, 2012
 */
public class ORMByAnnotaionFactory {
	
	public static void modelPersistToDB(IModel model) throws Exception {
		Method[] methods = model.getClass().getMethods();
		for(Method m : methods) {
			
		}
	}
	
	public static IModel mapToModel(Class<?> clazz, ResultSet rs) throws Exception {
		IModel obj = (IModel)clazz.newInstance();
		Method[] methods = clazz.getMethods();
		for(Method m : methods) {
			if(!m.getName().startsWith("set")) continue;
			OneToOne foo;
			OneToMany bar;
			if((foo = m.getAnnotation(OneToOne.class)) != null) {
				Object value = rs.getObject(foo.value());
				Class<?> param = m.getParameterTypes()[0];
				if(param.getAnnotation(Table.class) != null) {
					IModel model = mapToModel(param, (Integer) value);
					m.invoke(obj, model);
				} else {
					m.invoke(obj, value);
				}
			} else if((bar = m.getAnnotation(OneToMany.class)) != null) {
				String value = rs.getString(bar.field());
				Set<IModel> holder = new HashSet<IModel>();
				String[] ids = value.split("::");
				for(String id : ids) {
					IModel model = mapToModel(bar.model(), Integer.parseInt(id.trim()));
					holder.add(model);
				}
				m.invoke(obj, holder);
			}
		}
		return obj;
	}
	
	public static IModel mapToModel(Class<?> clazz, int id) throws Exception {
		String table = clazz.getAnnotation(Table.class).value();
		TaxonomyConnector connector = new TaxonomyConnector();
		ResultSet rs =connector.select("Select * from " + table + " where ID = " + id);
		return mapToModel(clazz, rs);
	}
}
