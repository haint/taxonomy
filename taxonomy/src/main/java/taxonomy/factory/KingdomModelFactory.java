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
import java.util.LinkedList;
import java.util.List;

import taxonomy.model.Kingdom;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Hai Thanh Nguyen</a>
 * @version $Id$
 *
 */
public class KingdomModelFactory extends AbstractModelFactory<Kingdom>
{
   @Override
   public Kingdom getModelById(String id) throws SQLException
   {
      return null;
   }

   @Override
   public List<Kingdom> getModelsFromRange(int from, int to) throws SQLException
   {
      return null;
   }

   @Override
   public LinkedList<Kingdom> getModelsFromRange(int from, int to, String orderBy, AbstractModelFactory.OrderType orderType) throws SQLException
   {
      return null;
   }

   @Override
   public Kingdom insert(Kingdom model) throws SQLException
   {
      return null;
   }

   @Override
   public Kingdom update(Kingdom model) throws SQLException
   {
      return null;
   }

   @Override
   public Kingdom delete(Kingdom model) throws SQLException
   {
      return null;
   }

	/**
	 * @see taxonomy.factory.IModelFactory#mapToModel(java.sql.ResultSet)
	 */
	@Override
	public Kingdom mapToModel(ResultSet result) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
