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

import java.util.HashMap;
import java.util.Map;

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
   private static TaxonomyFactory instance;
   
   private final Map<Class, IModelFactory<?>>	factories;
   
   private TaxonomyFactory() {
   	factories = new HashMap<Class, IModelFactory<?>>();
   	factories.put(Genus.class, new GenusModelFactory());
   	factories.put(Family.class, new FamilyModelFactory());
   	factories.put(Glossary.class, new GlossaryModelFactory());
   	factories.put(Index.class, new IndexModelFactory());
   	factories.put(Kingdom.class, new KingdomModelFactory());
   	factories.put(NaturalObject.class, new NaturalObjectModelFactory());
   	factories.put(Species.class, new SpeciesModelFactory());
   	factories.put(Tag.class, new TagModelFactory());
   	factories.put(Variant.class, new VariantModelFactory());
   }
   
   public static TaxonomyFactory getInstance() {
      return instance == null ? (instance = new TaxonomyFactory()) : instance;
   }
   
   public IModelFactory<?> getFactory(Class<?> clazz) {
   	IModelFactory<?> factory = factories.get(clazz);
   	if(factory == null)
         throw new UnsupportedOperationException("Unsupported model type: " + clazz.getCanonicalName());
   	return factory;
   }
}
