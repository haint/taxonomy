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
package taxonomy.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import sun.misc.IOUtils;


/**
 * @author <a href="mailto:haithanh0809@gmail.com">Hai Thanh Nguyen</a>
 * @version $Id$
 *
 */
public class Main
{
   final static String resourceDir = System.getProperty("taxonomy.resources.dir", "target/");

   final static String dbDir = System.getProperty("taxonomy.db.dir", "target/");
   
   public static void main(String[] args) throws Exception
   {
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");

      try
      {
         Statement statement = con.createStatement();
         InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("createTable.sql");
         byte[] bytes = IOUtils.readFully(is, is.available(), false);
         statement.executeUpdate(new String(bytes));
         
         is = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("insertIndex.sql");
         bytes = IOUtils.readFully(is, is.available(), false);
         statement.executeUpdate(new String(bytes));
         
         is = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("insertTag.sql");
         bytes = IOUtils.readFully(is, is.available(), false);
         statement.executeUpdate(new String(bytes));
         
         is = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("insertGlossary.sql");
         bytes = IOUtils.readFully(is, is.available(), false);
         statement.executeUpdate(new String(bytes));
         
         is = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("insertFamily.sql");
         bytes = IOUtils.readFully(is, is.available(), false);
         statement.executeUpdate(new String(bytes));  
         
         is = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("insertGenus.sql");
         bytes = IOUtils.readFully(is, is.available(), false);
         statement.executeUpdate(new String(bytes));
      }
      catch (SQLException ex)
      {
         throw new RuntimeException(ex);
      }
      finally
      {
         con.close();
      }
   }
}
