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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import taxonomy.annotation.ManyToOne;
import taxonomy.annotation.OneToMany;
import taxonomy.annotation.OneToOne;
import taxonomy.annotation.Table;
import taxonomy.model.IModel;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 * 
 *          Apr 19, 2012
 */
public class ORMTools {

	public static void insert(IModel model) throws Exception {

	}

	public static void update(IModel model, String... args) throws Exception {
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("datasource.properties"));
		Class.forName(properties.getProperty("driver"));
		Connection con = DriverManager.getConnection(properties.getProperty("datasource"), properties.getProperty("username"), properties.getProperty("password"));
		
		Method[] methods = model.getClass().getMethods();
		Set<String> fields = new HashSet<String>();
		Map<String, Object> holder = new HashMap<String, Object>();

		Collections.addAll(fields, args);
		Table table = model.getClass().getAnnotation(Table.class);
		int id = model.getId();

		for (Method m : methods) {
			if (!m.getName().startsWith("get"))
				continue;
			else if (fields.contains(m.getName().toLowerCase().substring(3))) {
				OneToOne foo = m.getAnnotation(OneToOne.class);
				ManyToOne bar = m.getAnnotation(ManyToOne.class);
				if (foo != null) {
					Object obj = m.invoke(model, new Object[]{});
					holder.put(foo.value(), obj instanceof IModel ? ((IModel)obj).getId() : obj);
				}
				else if (bar != null) {
					Iterator<IModel> iterator = (Iterator<IModel>)m.invoke(model, new Object[]{});
					StringBuilder b = new StringBuilder();
					while (iterator.hasNext()) {
						b.append(iterator.next().getId()).append("::");
					}
					holder.put(bar.field(), b.toString().substring(0, b.toString().length() - 2));
				}
				else
					throw new IllegalStateException();
			}
		}
		
		StringBuilder b = new StringBuilder();
		b.append("UPDATE ").append(table.value()).append(" SET ");
		Iterator<Map.Entry<String, Object>> iterator =holder.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		while(iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			sb.append(entry.getKey()).append(" = '").append(entry.getValue()).append("'").append(",");
		}
		b.append(sb.toString().substring(0, sb.toString().length() -1));
		b.append(" WHERE ID = ").append(id);
//		System.out.println(b.toString());
		con.createStatement().executeUpdate(b.toString());
		con.close();
	}

	public static IModel map(Class<?> clazz, ResultSet rs) throws Exception {
		IModel obj = (IModel)clazz.newInstance();
		Method[] methods = clazz.getMethods();
		for (Method m : methods) {
			if (!m.getName().startsWith("set"))
				continue;
			OneToOne foo;
			OneToMany bar;
			if ((foo = m.getAnnotation(OneToOne.class)) != null) {
				Object value = rs.getObject(foo.value());
				Class<?> param = m.getParameterTypes()[0];
				if (param.getAnnotation(Table.class) != null) {
					IModel model = map(param, (Integer)value);
					m.invoke(obj, model);
				}
				else {
					m.invoke(obj, value);
				}
			}
			else if ((bar = m.getAnnotation(OneToMany.class)) != null) {
				String value = rs.getString(bar.field());
				Set<IModel> holder = new HashSet<IModel>();
				String[] ids = value.split("::");
				for (String id : ids) {
					IModel model = map(bar.model(), Integer.parseInt(id.trim()));
					holder.add(model);
				}
				m.invoke(obj, holder);
			}
		}
		return obj;
	}

	public static IModel map(Class<?> clazz, int id) throws Exception {
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("datasource.properties"));
		Class.forName(properties.getProperty("driver"));
		Connection con = DriverManager.getConnection(properties.getProperty("datasource"), properties.getProperty("username"), properties.getProperty("password"));
		
		String table = clazz.getAnnotation(Table.class).value();
		ResultSet rs = con.createStatement().executeQuery(("Select * from " + table + " where ID = " + id));
		IModel model =map(clazz, rs);
		con.close();
		return model;
	}
}
