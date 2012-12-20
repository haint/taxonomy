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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import taxonomy.resources.client.LoaderService;
import taxonomy.resources.client.model.FilterModel;
import taxonomy.util.ORMTools;
import taxonomy.webui.client.widget.Tables;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 * 
 */
public class LoaderServiceImpl extends RemoteServiceServlet implements LoaderService {

  @Override
  public List<FilterModel> load(String tableName) throws Exception {
    //
    Connection con = ORMTools.getConnection();
    try {
      ResultSet rs = con.createStatement().executeQuery("select name, id from " + tableName);
      List<FilterModel> holder = new ArrayList<FilterModel>();
      while (rs.next()) {
        FilterModel model = new FilterModel(rs.getString(2), rs.getString(1));
        holder.add(model);
      }
      return holder;
    } finally {
      con.close();
    }
  }

  @Override
  public List<String> localeName(String locale) throws Exception {
    Connection con = ORMTools.getConnection();
    try {
      ResultSet rs =
        con.createStatement().executeQuery("select " + locale + "_names, id from " + Tables.NATURALOBJECT.getName());
      List<String> holder = new ArrayList<String>();
      while (rs.next()) {
        String values = rs.getString(1).trim();
        if (values == null || values.length() == 0)
          continue;
        for (String s : values.split("::")) {
          holder.add(s);
        }
      }
      Collections.sort(holder);
      return holder;
    } finally {
      con.close();
    }
  }
}
