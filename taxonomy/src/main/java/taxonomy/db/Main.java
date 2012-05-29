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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author <a href="mailto:haithanh0809@gmail.com">Hai Thanh Nguyen</a>
 * @version $Id$
 *
 */
public class Main
{
   final static String resourceDir = System.getProperty("taxonomy.resources.dir", "target/");

   final static String dbDir = System.getProperty("taxonomy.db.dir", "target/");
   
   public static String getStringFromInputStream(InputStream is) throws IOException {
  	 BufferedInputStream bis = new BufferedInputStream(is);
  	 ByteArrayOutputStream baos  = new ByteArrayOutputStream();
  	 byte[] buff = new byte[1024];
  	 int available = -1;
  	 while((available = bis.read(buff)) > -1) {
  		 baos.write(buff, 0, available);
  	 }
  	 return new String(baos.toByteArray(), "UTF-8");
   }
   
   public static void main(String[] args) throws Exception
   {
   	long start = System.currentTimeMillis();
   	long total = start;
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");

//  	 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//  	 Connection con = DriverManager.getConnection("jdbc:odbc:taxonomy","","");
      try
      {
         Statement statement = con.createStatement();
         /*InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("createTable.sql");
         statement.executeUpdate(getStringFromInputStream(is));
         System.out.println("Create table structure at: " + (System.currentTimeMillis() - start) / 1000 + "(s)");
         start = System.currentTimeMillis();
         
         is = Thread.currentThread().getContextClassLoader().getResourceAsStream("insertIndex.sql");
         statement.executeUpdate(getStringFromInputStream(is));
         System.out.println("Insert index at: " + (System.currentTimeMillis() - start) / 1000 + "(s)");
         start = System.currentTimeMillis();
         
         is = Thread.currentThread().getContextClassLoader().getResourceAsStream("insertTag.sql");
         statement.executeUpdate(getStringFromInputStream(is));
         System.out.println("insert tag at: " + (System.currentTimeMillis() - start) / 1000 + "(s)");
         start = System.currentTimeMillis();
         
         is = Thread.currentThread().getContextClassLoader().getResourceAsStream("insertGlossary.sql");
         statement.executeUpdate(getStringFromInputStream(is));
         System.out.println("insert glossary at: " + (System.currentTimeMillis() - start) / 1000 + "(s)");
         start = System.currentTimeMillis();
         
         is = Thread.currentThread().getContextClassLoader().getResourceAsStream("insertFamily.sql");
         statement.executeUpdate(getStringFromInputStream(is));
         System.out.println("insert family at: " + (System.currentTimeMillis() - start) / 1000 + "(s)");
         start = System.currentTimeMillis();
//         
         is = Thread.currentThread().getContextClassLoader().getResourceAsStream("insertGenus.sql");
         statement.executeUpdate(getStringFromInputStream(is));
         System.out.println("insert genus at: " + (System.currentTimeMillis() - start) / 1000 + "(s)");
         start = System.currentTimeMillis();
         
         Generator.genDataObject(Thread.currentThread().getContextClassLoader().getResourceAsStream("txmain_utf8.txt"));
         System.out.println("insert object at: " + (System.currentTimeMillis() - start) / 1000 + "(s)");
         start = System.currentTimeMillis();*/
         statement.executeUpdate(getStringFromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("taxonomy.sql")));
         System.out.println("Total at: " + (System.currentTimeMillis() - total) / 1000 + "(s)");
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
