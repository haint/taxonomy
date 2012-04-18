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

import taxonomy.model.Family;
import taxonomy.model.Kingdom;
import taxonomy.util.LocaleResolver;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Hai Thanh Nguyen</a>
 * @version $Id$
 *
 */
public class FamilyModelFactory extends AbstractModelFactory<Family>
{
   @Override
   public Family getModelById(String id) throws SQLException
   {
   	ResultSet result = connector.select("Select * from FAMILY where ID = " +id);
      return mapToModel(result);
   }

   @Override
   public List<Family> getModelsFromRange(int from, int to) throws SQLException
   {
      return null;
   }

   @Override
   public LinkedList<Family> getModelsFromRange(int from, int to, String orderBy, AbstractModelFactory.OrderType orderType) throws SQLException
   {
      return null;
   }

   @Override
   public Family insert(Family model) throws SQLException
   {
      return null;
   }

   @Override
   public Family update(Family model) throws SQLException
   {
      return null;
   }

   @Override
   public Family delete(Family model) throws SQLException
   {
      return null;
   }

	@Override
	public Family mapToModel(ResultSet result) throws SQLException {
		if(!result.next()) return null;
		int id = result.getInt("ID");
		String kingdom_id = result.getString("KINGDOM_ID");
		String name = result.getString("NAME");
		String locale_ids = result.getString("LOCALE_IDS");
		String desc = result.getString("DESCRIPTION");
		String avatar = result.getString("AVATAR");
		
		KingdomModelFactory factory = (KingdomModelFactory)TaxonomyFactory.getInstance().getFactory(Kingdom.class);
		Kingdom kingdom = factory.getModelById(kingdom_id);
		
		Family family = new Family();
		family.setId(id);
		family.setName(name);
		family.setDescription(desc);
		family.setKingdom(kingdom);
		family.setAvatar(avatar.trim().isEmpty() ? null : avatar);
		try {
			family.setLocaleName(LocaleResolver.resolve(locale_ids));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return family;
	}
}
