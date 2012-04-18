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

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import taxonomy.db.TaxonomyConnector;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 * Apr 18, 2012
 */
public class LocaleResolver {

	public static Map<Locale, String> resolve(String locale_ids) throws Exception {
		if(locale_ids == null || locale_ids.trim().isEmpty()) return null;
		Map<Locale, String> map = new HashMap<Locale, String>();
		String condition = locale_ids.replaceAll("::", " OR ID = ");
		TaxonomyConnector connector = new TaxonomyConnector();
		ResultSet rs = connector.select("Select * from LOCALES where ID = " + condition);
		while(rs.next()) {
			String locale = rs.getString("NAME");
			String value = rs.getString("VALUE");
			map.put(new Locale(locale), value);
		}
		return map;
	}
}
