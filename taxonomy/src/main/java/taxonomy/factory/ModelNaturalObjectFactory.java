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

import java.util.List;

import taxonomy.model.NaturalObject;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Hai Thanh Nguyen</a>
 * @version $Id$
 *
 */
class ModelNaturalObjectFactory extends AbstractFactory<NaturalObject>
{
   public ModelNaturalObjectFactory() {
      super();
      INSTANCE = new ModelNaturalObjectFactory();
   }

   /**
    * @see taxonomy.factory.AbstractFactory#getModelById(java.lang.String)
    */
   @Override
   public NaturalObject getModelById(String id) throws Exception
   {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * @see taxonomy.factory.AbstractFactory#getModelsFromRange(int, int)
    */
   @Override
   public List<NaturalObject> getModelsFromRange(int from, int to) throws Exception
   {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * @see taxonomy.factory.AbstractFactory#getModelsFromRange(int, int, java.lang.String, taxonomy.factory.AbstractFactory.OrderType)
    */
   @Override
   public List<NaturalObject> getModelsFromRange(int from, int to, String orderBy,
      taxonomy.factory.AbstractFactory.OrderType orderType) throws Exception
   {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * @see taxonomy.factory.AbstractFactory#insert(taxonomy.model.IModel)
    */
   @Override
   public NaturalObject insert(NaturalObject model) throws Exception
   {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * @see taxonomy.factory.AbstractFactory#update(taxonomy.model.IModel)
    */
   @Override
   public NaturalObject update(NaturalObject model) throws Exception
   {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * @see taxonomy.factory.AbstractFactory#delete(taxonomy.model.IModel)
    */
   @Override
   public NaturalObject delete(NaturalObject model) throws Exception
   {
      // TODO Auto-generated method stub
      return null;
   }

}
