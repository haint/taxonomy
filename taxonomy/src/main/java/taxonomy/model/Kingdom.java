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

import taxonomy.annotation.OneToOne;
import taxonomy.annotation.Table;

/**
 * @author <a href="mailto:haint@exoplatform.com">Nguyen Thanh Hai</a>
 *
 * @datOct 3, 2011
 */
@Table("[Kingdom]")
public class Kingdom implements IModel
{

   private static final long serialVersionUID = -3380120455488706079L;

   private Integer id;
   
   private String name;
   
   private String code;

   @OneToOne("ID")
   public Integer getId()
   {
      return id;
   }

   @OneToOne("ID")
   public Kingdom setId(Integer id)
   {
      this.id = id;
      return this;
   }
   
   @OneToOne("NAME")
   public String getName()
   {
      return name;
   }

   @OneToOne("NAME")
   public void setName(String name)
   {
      this.name = name;
   }

   @OneToOne("CODE")
   public String getCode()
   {
      return code;
   }

   @OneToOne("CODE")
   public void setCode(String code)
   {
      this.code = code;
   }
   
   public boolean equals(Object obj)
   {
      Kingdom other = (Kingdom) obj;
      if(this.getId() == other.getId()) return true;
      return false;
   }
}
