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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import taxonomy.annotation.ManyToOne;
import taxonomy.annotation.OneToMany;
import taxonomy.annotation.OneToOne;
import taxonomy.annotation.Table;

/**
 * @author <a href="mailto:haint@exoplatform.com">Nguyen Thanh Hai</a>
 *
 * @datOct 3, 2011
 */
@Table("[Family]")
public class Family implements IModel
{

   private static final long serialVersionUID = -7257088502916236613L;
   
   private int id;
   
   private Kingdom king;
   
   private String name;
   
   private Set<Locale> locales;
   
   private String desc;
   
   private String avartar;
   

   @OneToOne("ID")
   public int getId() 
   {
      return id;
   }
   
   @OneToOne("ID")
   public void setId(int id)
   {
      this.id = id;
   }
   
   @OneToOne("KINGDOM_ID")
   public Kingdom getKingdom()
   {
      return king;
   }
   
   @OneToOne("KINGDOM_ID")
   public void setKingdom(Kingdom king)
   {
      this.king = king;
   }

   @OneToOne("NAME")
   public String getName()
   {
      return name;
   }

   @OneToOne("NAME")
   public void setName(String scienseName)
   {
      this.name = scienseName;
   }
   
   @OneToOne("DESCRIPTION")
   public String getDescription()
   {
      return desc;
   }
   
   @OneToOne("DESCRIPTION")
   public void setDescription(String desc)
   {
      this.desc = desc;
   }
 
   @ManyToOne(field = "LOCALE_IDS", model = Locale.class)
   public Iterator<Locale> getLocales() {
   	return locales.iterator();
   }
   
   @OneToMany(field = "LOCALE_IDS", model = Locale.class)
   public void setLocaleName(Set<Locale> locales) {
   	this.locales = locales;
   }
   
   public void addLocale(Locale locale) {
   	if(locales == null) locales = new HashSet<Locale>();
   	locales.add(locale);
   }
   
   @OneToOne("AVATAR")
   public void setAvatar(String path)
   {
      this.avartar = path;
   }
   
   @OneToOne("AVATAR")
   public String getAvatar()
   {
      return avartar;
   }
   
   public boolean equals(Object obj)
   {
      Family other = (Family) obj;
      if(this.getId() == other.getId()) return true;
      return false;
   }
}
