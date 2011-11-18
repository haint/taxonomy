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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author <a href="mailto:haint@exoplatform.com">Nguyen Thanh Hai</a>
 *
 * @datOct 3, 2011
 */
public class Family implements IModel
{

   private static final long serialVersionUID = -7257088502916236613L;
   
   private int id;
   
   private Kingdom king;
   
   private String name;
   
   private Map<Locale, String> localeName;
   
   private String desc;
   
   private String avartar;
   

   public int getId() 
   {
      return id;
   }
   
   public void setId(int id)
   {
      this.id = id;
   }
   
   public Kingdom getKing()
   {
      return king;
   }

   public void setKing(Kingdom king)
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
 
   public String getLocaleName(Locale locale)
   {
      if(localeName == null) return null;
      return localeName.get(locale);
   }
   
   public String setLocaleName(Locale locale, String name)
   {
      localeName = new HashMap<Locale, String>();
      return localeName.put(locale, name);
   }
   
   public void setAvatar(String path)
   {
      this.avartar = path;
   }
   
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
