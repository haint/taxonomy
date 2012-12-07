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
package taxonomy.resources.client.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:haint@exoplatform.com">Nguyen Thanh Hai</a>
 *
 */
public class VGlossary extends VModel
{
	private static final long serialVersionUID = 1L;

	private String name;
   
   private String explain;
   
   private String example;
   
   private Set<VLocale> locales;

   public void setName(String name) {
   	this.name = name;
   }
   
   public String getName() {
   	return name;
   }

   public String getExplaintion() 
   {
      return explain;
   }
   
   public void setExplaintion(String explain)
   {
      this.explain = explain;
   }
   
   public String getExample()
   {
      return example;
   }
   
   public void setExample(String example)
   {
      this.example = example;
   }
   
   public void setLocales(Set<VLocale> locales) {
   	this.locales = locales;
   }
   
   public Set<VLocale> getLocales() {
   	return locales;
   }
   
   public void addLocale(VLocale locale) {
   	if(locales == null) locales = new HashSet<VLocale>();
   	locales.add(locale);
   }
   
   @Override
   public String toString()
   {
      return name;
   }
}