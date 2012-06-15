/*
 * Copyright (C) 2011 eXo Platform SAS.
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
package taxonomy.webui.client.model;

public class VVariant extends VModel
{
	private static final long serialVersionUID = 1L;

	private String value;
   
   private Integer type;
   
   public static Integer SYNONYM = 1;
   
   public static Integer OLD = 2;
   
   public void setValue(String value)
   {
      this.value = value;
   }
   
   public String getValue()
   {
      return value;
   }
   
   public void setType(Integer type)
   {
      this.type = type;
   }
   
   public Integer getType()
   {
      return type;
   }
}
