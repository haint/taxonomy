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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import taxonomy.db.TaxonomyConnector;
import taxonomy.model.Family;
import taxonomy.model.IModel;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Hai Thanh Nguyen</a>
 * @version $Id$
 *
 */
abstract class AbstractModelFactory<T extends IModel> implements IModelFactory<T>
{
   public static enum OrderType {
      ASC, DESC
   };
   
   protected final TaxonomyConnector connector;
   
   AbstractModelFactory()
   {
   	try {
			connector = new TaxonomyConnector();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
   }
   
   public List<T> getAll() throws SQLException {
   	return getModelsFromRange(0, 20);
   }
   
   public T getModelById(String id) throws SQLException
   {
   	String table = getClass().getSimpleName().substring(0, getClass().getSimpleName().indexOf("ModelFactory"));
   	ResultSet result = connector.select("Select * from [" + table + "] where ID = " +id);
      return mapToModel(result);
   }

   public List<T> getModelsFromRange(int from, int to) throws SQLException
   {
   	String table = getClass().getSimpleName().substring(0, getClass().getSimpleName().indexOf("ModelFactory"));
   	ResultSet rs = connector.select("Select * from [" + table + "] limit " + from + "," + to);
   	List<T> holder = new ArrayList<T>();
   	T t = null;
   	while((t = mapToModel(rs)) != null) {
   		holder.add(t);
   	}
      return holder;
   }

   public LinkedList<T> getModelsFromRange(int from, int to, String orderBy, AbstractModelFactory.OrderType orderType) throws SQLException
   {
      return null;
   }

}
