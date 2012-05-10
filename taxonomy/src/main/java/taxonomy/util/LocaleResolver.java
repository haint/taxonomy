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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import taxonomy.model.Locale;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 * Apr 18, 2012
 */
public class LocaleResolver {
	
	public static Set<Locale> resolve(String locale_ids) throws Exception {
		if(locale_ids == null || locale_ids.trim().isEmpty()) return null;
		Set<Locale> holder = new HashSet<Locale>();
		String condition = locale_ids.replaceAll("::", " OR ID = ");
		
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("datasource.properties"));
		Class.forName(properties.getProperty("driver"));
		Connection con = DriverManager.getConnection(properties.getProperty("datasource"), properties.getProperty("username"), properties.getProperty("password"));
		
		ResultSet rs = con.createStatement().executeQuery(("Select * from LOCALES where ID = " + condition));
		while(rs.next()) {
			Locale l = new Locale();
			l.setId(rs.getInt("ID"));
			l.setName(rs.getString("NAME"));
			l.setValue(rs.getString("VALUE"));
			holder.add(l);
		}
		rs.close();
		con.close();
		return holder;
	}
}
