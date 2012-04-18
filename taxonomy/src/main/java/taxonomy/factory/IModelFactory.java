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
package taxonomy.factory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import taxonomy.factory.AbstractModelFactory.OrderType;
import taxonomy.model.IModel;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 * Apr 18, 2012
 */
interface IModelFactory<T extends IModel> {

	public T mapToModel(ResultSet result) throws SQLException;
   
   public  T getModelById(String id) throws SQLException;
   
   public List<T> getModelsFromRange(int from, int to) throws SQLException;
   
   public LinkedList<T> getModelsFromRange(int from, int to, String orderBy, OrderType orderType) throws SQLException;
   
   public T insert(T model) throws SQLException;
   
   public T update(T model) throws SQLException;
   
   public T delete(T model) throws SQLException;
}
