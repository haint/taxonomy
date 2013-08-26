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
package taxonomy.util;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import taxonomy.annotation.ManyToOne;
import taxonomy.annotation.OneToMany;
import taxonomy.annotation.OneToOne;
import taxonomy.annotation.Table;
import taxonomy.model.Model;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 * 
 *          Apr 19, 2012
 */
public class ORMTools {
	
	private final static Properties properties = new Properties();;
	
	static {
		try {
		String context = System.getProperty("taxonomy.context");
		InputStream is = null;
		if(context != null)
		{
			Class contextClass = Class.forName(context);
			is = contextClass.getResourceAsStream("/tx.properties");
			URL url = contextClass.getResource("/taxonomy.db");
			System.out.println(url.getFile());
			String datasource = "jdbc:sqlite:" + url.getFile();
			properties.load(is);
			properties.setProperty("datasource", datasource);
		}
		else
		{
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream("datasource.properties");
			properties.load(is);
		}
		is.close();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static Connection getConnection() {
	   try {
	      Class.forName(properties.getProperty("driver"));
	      Connection con =
	         DriverManager.getConnection(properties.getProperty("datasource"), properties.getProperty("username"),
	            properties.getProperty("password"));
	      return con;
	   } 
	   catch (Exception e)
	   {
	      throw new RuntimeException(e);
	   }
	}

	public static void insert(Model model) throws Exception {
		Connection con = getConnection();
		Table table = model.getClass().getAnnotation(Table.class);

		Map<String, Object> holder = new HashMap<String, Object>();
		Method[] methods = model.getClass().getMethods();
		for (Method m : methods) {
			if (!m.getName().startsWith("get"))
				continue;

			OneToOne foo = m.getAnnotation(OneToOne.class);
			ManyToOne bar = m.getAnnotation(ManyToOne.class);

			if (foo != null) {
				Object value = m.invoke(model, new Object[]{});
				if(value == null) continue;
				if(value instanceof String) {
					value = ((String) value).replaceAll("\'", "\'\'");
				}
				holder.put(foo.value(), value instanceof Model ? ((Model) value).getId() : value);
			}
			else if (bar != null) {
				Iterator<Model> i = (Iterator<Model>)m.invoke(model, new Object[]{});
				if (i == null)
					continue;

				StringBuilder b = new StringBuilder();
				while (i.hasNext()) {
					b.append(i.next().getId()).append("::");
				}
				if(b.length() == 0) continue;
				holder.put(bar.field(), b.toString().substring(0, b.toString().length() - 2));
			}
			else
				continue;
		}
		if(holder.size() == 0) return;
		
		StringBuilder b = new StringBuilder();
		b.append("Insert into ").append(buildValues(table.value(), holder));
		System.out.println(b.toString());
		con.createStatement().executeUpdate(b.toString());
		con.close();
	}

	private static String buildValues(String table, Map<String, Object> values) {
		StringBuilder b = new StringBuilder();
		b.append(table).append("(");

		LinkedList<String> keys = new LinkedList<String>(values.keySet());
		for (int i = 0; i < keys.size(); i++) {
			b.append(keys.get(i));
			if (i < keys.size() - 1)
				b.append(",");
		}
		b.append(") values (");

		LinkedList<Object> v = new LinkedList<Object>(values.values());
		for (int i = 0; i < v.size(); i++) {
			b.append("'").append(v.get(i)).append("'");
			if (i < v.size() - 1)
				b.append(",");
		}
		b.append(")");
		return b.toString();
	}

	public static void update(Model model, String... args) throws Exception {
		Connection con = getConnection();
		Method[] methods = model.getClass().getMethods();
		Set<String> fields = new HashSet<String>();
		Map<String, Object> holder = new HashMap<String, Object>();

		Collections.addAll(fields, args);
		Table table = model.getClass().getAnnotation(Table.class);
		int id = model.getId();

		for (Method m : methods) {
			if (!m.getName().startsWith("get"))
				continue;
			if (fields.contains(m.getName().toLowerCase().substring(3))) {
				OneToOne foo = m.getAnnotation(OneToOne.class);
				ManyToOne bar = m.getAnnotation(ManyToOne.class);
				if (foo != null) {
					Object obj = m.invoke(model, new Object[]{});
					holder.put(foo.value(), obj instanceof Model ? ((Model)obj).getId() : obj);
				}
				else if (bar != null) {
					Iterator<Model> iterator = (Iterator<Model>)m.invoke(model, new Object[]{});
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
		Iterator<Map.Entry<String, Object>> iterator = holder.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			sb.append(entry.getKey()).append(" = '").append(entry.getValue()).append("'").append(",");
		}
		b.append(sb.toString().substring(0, sb.toString().length() - 1));
		b.append(" WHERE ID = ").append(id);
		// System.out.println(b.toString());
		con.createStatement().executeUpdate(b.toString());
		con.close();
	}

	public static Model map(Class<?> clazz, ResultSet rs) throws Exception {
		Model obj = (Model)clazz.newInstance();
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
					Model model = map(param, (Integer)value);
					if(model == null) continue;
					m.invoke(obj, model);
				} else {
					m.invoke(obj, value);
				}
			}
			else if ((bar = m.getAnnotation(OneToMany.class)) != null) {
				String value = rs.getString(bar.field());
				Set<Model> holder = new HashSet<Model>();
				if(value == null) continue;
				String[] ids = value.split("::");
				for (String id : ids) {
					if(id.trim().isEmpty() || id == null || "null".equals(id)) continue;
					Model model = map(bar.model(), Integer.parseInt(id.trim()));
					if(model != null) holder.add(model);
				}
				m.invoke(obj, holder);
			}
		}
		return obj;
	}

	public static Model map(Class<?> clazz, Integer id) throws Exception {
		if(id < 1) return null;
		
		Connection con = getConnection();
		String table = clazz.getAnnotation(Table.class).value();
		ResultSet rs = con.createStatement().executeQuery(("Select * from " + table + " where ID = " + id));
		if(!rs.next()) return null;
		Model model = map(clazz, rs);
		con.close();
		return model;
	}
	
	public static Model map(Class<?> clazz, String query) throws Exception {
		Connection con = getConnection();
		ResultSet rs = con.createStatement().executeQuery(query);
		Model model = map(clazz, rs);
		con.close();
		return model;
	}
}