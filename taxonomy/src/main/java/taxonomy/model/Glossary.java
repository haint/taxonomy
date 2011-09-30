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
public class Glossary implements IModel
{

   /**
    * 
    */
   private static final long serialVersionUID = 4239408774009926225L;
   
   private int id;
   
   private String term;
   
   private String explain;
   
   private String example;
   
   private Map<Locale, String> localeTerm;

   public int getId()
   {
      return id;
   }

   public void setId(int id)
   {
      this.id = id;
   }

   public String getTerm()
   {
      return term;
   }

   public void setTerm(String term)
   {
      this.term = term;
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
   
   public String getLocaleName(Locale locale)
   {
      if(localeTerm == null) return null;
      return localeTerm.get(locale);
   }
   
   public String setLocaleName(Locale locale, String name)
   {
      if(localeTerm == null) localeTerm = new HashMap<Locale, String>();
      return localeTerm.put(locale, name);
   }
}
