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
@Table("[Glossary]")
public class Glossary extends Model<Glossary>
{
   private static final long serialVersionUID = 4239408774009926225L;
   
   private String name;
   
   private String explain;
   
   private String example;
   
   private Set<Locale> locales;

   @OneToOne("NAME")
   public void setName(String name) {
   	this.name = name;
   }
   
   @OneToOne("NAME")
   public String getName() {
   	return name;
   }

   @OneToOne("EXPLAINTION")
   public String getExplaintion() 
   {
      return explain;
   }
   
   @OneToOne("EXPLAINTION")
   public void setExplaintion(String explain)
   {
      this.explain = explain;
   }
   
   @OneToOne("EXAMPLE")
   public String getExample()
   {
      return example;
   }
   
   @OneToOne("EXAMPLE")
   public void setExample(String example)
   {
      this.example = example;
   }
   
   @OneToMany(field = "LOCALE_IDS", model = Locale.class)
   public void setLocales(Set<Locale> locales) {
   	this.locales = locales;
   }
   
   @ManyToOne(field = "LOCALE_IDS", model = Locale.class)
   public Iterator<Locale> getLocales() {
   	if(locales == null) return null;
   	return locales.iterator();
   }
   
   public void addLocale(Locale locale) {
   	if(locales == null) locales = new HashSet<Locale>();
   	locales.add(locale);
   }
}
