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
package taxonomy.webui.client.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author <a href="mailto:haint@exoplatform.com">Nguyen Thanh Hai</a>
 *
 */
public class VFamily extends VModel
{
	private static final long serialVersionUID = 1L;

	private VKingdom king;
   
   private String name;
   
   private Set<VLocale> locales;
   
   private String desc;
   
   private String avartar;
   

   public VKingdom getKingdom()
   {
      return king;
   }
   
   public void setKingdom(VKingdom king)
   {
      this.king = king;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String scienseName)
   {
      this.name = scienseName;
   }
   
   public String getDescription()
   {
      return desc;
   }
   
   public void setDescription(String desc)
   {
      this.desc = desc;
   }
 
   public Iterator<VLocale> getLocales() {
   	return locales != null ? locales.iterator() : null;
   }
   
   public void setLocales(Set<VLocale> locales) {
   	this.locales = locales;
   }
   
   public void addLocale(VLocale locale) {
   	if(locales == null) locales = new HashSet<VLocale>();
   	locales.add(locale);
   }
   
   public void setAvatar(String path)
   {
      this.avartar = path;
   }
   
   public String getAvatar()
   {
      return avartar;
   }
}