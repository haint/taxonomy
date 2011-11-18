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
package taxonomy.factory;

import taxonomy.model.Family;
import taxonomy.model.Genus;
import taxonomy.model.Glossary;
import taxonomy.model.Index;
import taxonomy.model.Kingdom;
import taxonomy.model.NaturalObject;
import taxonomy.model.Species;
import taxonomy.model.Tag;
import taxonomy.model.Variant;


/**
 * @author <a href="mailto:haithanh0809@gmail.com">Hai Thanh Nguyen</a>
 * @version $Id$
 *
 */
public class TaxonomyFactory
{
   final public static TaxonomyFactory instance = new TaxonomyFactory();
   
   public static TaxonomyFactory getInstance() {
      return instance;
   }
   
   public static AbstractFactory<?> getFactory(Class<?> clazz) throws Exception{
      if(clazz.isInstance(Genus.class)) {
         return ModelGenusFactory.INSTANCE;
      } else if(clazz.isInstance(Family.class)) {
         return ModelFamilyFactory.INSTANCE;
      } else if(clazz.isInstance(Glossary.class)) {
         return ModelGlossaryFactory.INSTANCE;
      } else if(clazz.isInstance(Index.class)) {
         return ModelIndexFactory.INSTANCE;
      } else if(clazz.isInstance(Kingdom.class)) {
         return ModelKingdomFactory.INSTANCE;
      } else if(clazz.isInstance(NaturalObject.class)) {
         return ModelNaturalObjectFactory.INSTANCE;
      } else if(clazz.isInstance(Species.class)) {
         return ModelSpeciesFactory.INSTANCE;
      } else if(clazz.isInstance(Tag.class)) {
         return ModelTagFactory.INSTANCE;
      } else if(clazz.isInstance(Variant.class)) {
         return ModelVariantFactory.INSTANCE;
      } else {
         throw new Exception("Unsupported model type: " + clazz.getCanonicalName());
      }
   }
}
