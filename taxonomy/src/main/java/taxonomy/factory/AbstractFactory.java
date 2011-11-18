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

import taxonomy.model.IModel;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Hai Thanh Nguyen</a>
 * @version $Id$
 *
 */
public abstract class AbstractFactory<T extends IModel>
{
   public static enum OrderType {
      ASC, DESC
   };
   
   protected static AbstractFactory<?> INSTANCE;
   
   public AbstractFactory()
   {
   }
   
   public abstract T getModelById(String id) throws Exception;
   
   public abstract List<T> getModelsFromRange(int from, int to) throws Exception;
   
   public abstract List<T> getModelsFromRange(int from, int to, String orderBy, OrderType orderType) throws Exception;
   
   public abstract T insert(T model) throws Exception;
   
   public abstract T update(T model) throws Exception;
   
   public abstract T delete(T model) throws Exception;
}
