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
package taxonomy.webui.client.widget;

import java.util.Set;

import taxonomy.resources.client.model.VFamily;
import taxonomy.resources.client.model.VGenus;
import taxonomy.resources.client.model.VGlossary;
import taxonomy.resources.client.model.VIndex;
import taxonomy.resources.client.model.VKingdom;
import taxonomy.resources.client.model.VLocale;
import taxonomy.resources.client.model.VNaturalObject;
import taxonomy.resources.client.model.VSpecies;
import taxonomy.resources.client.model.VTag;
import taxonomy.resources.client.model.VVariant;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public class ModelProperties
{
   interface GlossaryProperties extends PropertyAccess<VGlossary>
   {
      ValueProvider<VGlossary, Integer> id();
      
      ValueProvider<VGlossary, String> name();
      
      ValueProvider<VGlossary, String> example();
      
      ValueProvider<VGlossary, Set<VLocale>> locales();
   }
   
   interface IndexProperties extends PropertyAccess<VIndex>
   {
      ValueProvider<VIndex, Integer> id();
      
      ValueProvider<VIndex, String> value();
   }

   interface KingdomProperties extends PropertyAccess<VKingdom>
   {
      @Path("id")
      ModelKeyProvider<VKingdom> id();

      ValueProvider<VKingdom, String> name();

      ValueProvider<VKingdom, String> code();
   }
   
   interface FamilyProperties extends PropertyAccess<VFamily>
   {
      ValueProvider<VFamily, Integer> id();
      
      ValueProvider<VFamily, VKingdom> kingdom();
      
      ValueProvider<VFamily, Set<VLocale>> locales();
      
      ValueProvider<VFamily, String> name();
   }
   
   interface GenusProperites extends FamilyProperties
   {
      ValueProvider<VGenus, Set<VVariant>> variants(); 
   }
   
   interface SpeciesProperties extends PropertyAccess<VSpecies>
   {
      ValueProvider<VSpecies, Integer> id();
      
      ValueProvider<VSpecies, String> name();
      
      ValueProvider<VSpecies, Set<VVariant>> variants();
   }
   
   interface ObjectProperties extends PropertyAccess<VNaturalObject>
   {
      ValueProvider<VNaturalObject, Integer> id();
      
      ValueProvider<VNaturalObject, VKingdom> kingdom();
      
      ValueProvider<VNaturalObject, Set<VFamily>> families();
      
      ValueProvider<VNaturalObject, VGenus> genus();
      
      ValueProvider<VNaturalObject, VSpecies> species();
      
      ValueProvider<VNaturalObject, Set<VIndex>> indecies();
      
      ValueProvider<VNaturalObject, Set<VTag>> tags();
      
      ValueProvider<VNaturalObject, Set<String>> enNameSet();
      
      ValueProvider<VNaturalObject, Set<String>> vnNameSet();
   }
   
   interface LocalesProperties extends PropertyAccess<VLocale>
   {
      ValueProvider<VLocale, Integer> id();
      
      ValueProvider<VLocale, String> name();
      
      ValueProvider<VLocale, String> value();
   }
   
   interface VariantPropertis extends PropertyAccess<VVariant>
   {
      ValueProvider<VVariant, Integer> id();
      
      ValueProvider<VVariant, String> value();
   }
   
   interface TagProperties extends PropertyAccess<VTag>
   {
      ValueProvider<VTag, Integer> id();
      
      ValueProvider<VTag, String> name();
      
      ValueProvider<VTag, String> explaintion();
   }
}
