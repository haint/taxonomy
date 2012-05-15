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
package taxonomy.model;

import taxonomy.annotation.OneToOne;
import taxonomy.annotation.Table;

/**
 * Author : Nguyen Thanh Hai
 *          haithanh0809@gmail.com
 * Apr 19, 2012  
 */
@Table("Locales")
public class Locale extends Model<Locale> {

	private static final long	serialVersionUID	= 2378720725851305514L;
	
	private String name;
	
	private String value;
	
	@OneToOne("NAME")
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToOne("NAME")
	public String getName() {
		return name;
	}
	
	@OneToOne("VALUE")
	public void setValue(String value) {
		this.value = value;
	}
	
	@OneToOne("VALUE")
	public String getValue() {
		return value;
	}
}
