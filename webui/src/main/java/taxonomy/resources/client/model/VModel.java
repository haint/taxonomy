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
package taxonomy.resources.client.model;

import java.io.Serializable;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 * 
 */
public abstract class VModel implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }
  
  public abstract String getName();

  @Override
  public boolean equals(Object obj) {
    VModel other = (VModel)obj;
    if (this.getId() == other.getId())
      return true;
    return false;
  }
}
