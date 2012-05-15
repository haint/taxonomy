/**
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
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

import java.io.Serializable;

import taxonomy.annotation.OneToOne;

/**
 * @author <a href="mailto:haint@exoplatform.com">Nguyen Thanh Hai</a>
 * 
 * @datOct 3, 2011
 */
public abstract class Model<T extends Model> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@OneToOne("ID")
	public T setId(Integer id) {
		this.id = id;
		return (T) this;
	}
	
	@OneToOne("ID")
	public Integer getId() {
		return id;
	}
	
	 public boolean equals(Object obj)
	   {
	      Model<?> other = (Model<?>) obj;
	      if(this.getId() == other.getId()) return true;
	      return false;
	   }
}
