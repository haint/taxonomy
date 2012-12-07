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
package taxonomy.resources.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import taxonomy.model.Model;
import taxonomy.resources.client.TxDAOService;
import taxonomy.resources.client.model.VFamily;
import taxonomy.resources.client.model.VGenus;
import taxonomy.resources.client.model.VGlossary;
import taxonomy.resources.client.model.VIndex;
import taxonomy.resources.client.model.VKingdom;
import taxonomy.resources.client.model.VLocale;
import taxonomy.resources.client.model.VModel;
import taxonomy.resources.client.model.VNaturalObject;
import taxonomy.resources.client.model.VResult;
import taxonomy.resources.client.model.VSpecies;
import taxonomy.resources.client.model.VTag;
import taxonomy.resources.client.model.VVariant;
import taxonomy.util.ORMTools;

import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public class TxDAOServiceImpl implements TxDAOService {
   
   /** . */
   private Map<String, Class<?>> tableClazz = null;
   
   public TxDAOServiceImpl()
   {
      Map<String, Class<?>> map = new HashMap<String, Class<?>>();
      map.put("[Family]", VFamily.class);
      map.put("[Genus]", VGenus.class);
      map.put("[Glossary]", VGlossary.class);
      map.put("[Index]", VIndex.class);
      map.put("[Kingdom]", VKingdom.class);
      map.put("[Locales]", VLocale.class);
      map.put("[NaturalObject]", VNaturalObject.class);
      map.put("[Species]", VSpecies.class);
      map.put("[Tag]", VTag.class);
      map.put("[Variant]", VVariant.class);
      this.tableClazz = map;
   }

	@Override
	public String getName() {
		return TxDAOService.class.getName();
	}

	@Override
	public Integer getMaxId(String tableName) throws Exception {
		Connection con = ORMTools.getConnection();
		ResultSet rs = con.createStatement().executeQuery("Select max(ID) from " + tableName);
		rs.next();
		int maxId = rs.getInt(1);
		con.close();
		return maxId;
	}

	@Override
	public VModel getGeneric(String clazz, Integer id) throws Exception {
		VModel f = Tools.serialize(clazz, ORMTools.map(Tools.deserializeHolder.get(Class.forName(clazz)), id));
		return f;
	}

	@Override
	public VResult query(String query) throws Exception {
		Connection con = ORMTools.getConnection();
		ResultSet rs = con.createStatement().executeQuery(query);
		VResult vresult = new VResult();
		while(rs.next()) {
			LinkedList<Object> holder = new LinkedList<Object>();
			int i = 1;
			while(true) {
				try {
					holder.addLast(rs.getObject(i));
					i++;
				} catch(SQLException e) {
					vresult.set(holder);
					break;
				}
			}
		}
		return vresult;
	}
	
	@Override
	public void update(String query) throws Exception
	{
	   Connection con = ORMTools.getConnection();
	   con.createStatement().executeUpdate(query);
	}

   @Override
   public List<VModel> select(String tableName, Integer from, Integer to)
   {
      try {
         Connection con = ORMTools.getConnection();
         ResultSet rs = null;
         if(from != null && to != null)
         {
            rs = con.createStatement().executeQuery("select * from " + tableName + " limit " + from.intValue() + ", " + to.intValue());
         }
         else
         {
            rs = con.createStatement().executeQuery("select * from " + tableName);
         }
         LinkedList<VModel> holder = null;
         while(rs.next())
         {
            Class clazz = tableClazz.get(tableName);
            Model model = ORMTools.map(Tools.deserializeHolder.get(clazz), rs);
            VModel vmodel = Tools.serialize(clazz.getName(), model);
            if(holder == null) holder =new LinkedList<VModel>();
            holder.add(vmodel);
         }
         return holder;
      } 
      catch(Throwable e)
      {
         throw new RuntimeException(e);
      }
   }

   @Override
   public <M extends VModel> PagingLoadResult<M> select(String tableName, PagingLoadConfig config) throws Exception
   {
      Connection con = ORMTools.getConnection();
      ResultSet rs = con.createStatement().executeQuery("Select count(ID) from " + tableName);
      rs.next();
      int totalSize = rs.getInt(1);
      System.out.println("Total size: " + totalSize);
      List<M> models = (List<M>)select(tableName, config.getOffset(), config.getLimit());
      return models == null ? null : new PagingLoadResultBean<M>(models, totalSize, config.getOffset());
   }
}
