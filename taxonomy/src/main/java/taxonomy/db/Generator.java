/**
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package taxonomy.db;

import sun.misc.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:haint@exoplatform.com">Nguyen Thanh Hai</a>
 *
 * @datOct 3, 2011
 * For field multi value we will use seperator is '::' (ignore dash)
 * In text data fields separate by '/--/' 
 */
@Deprecated
public class Generator
{
   final static String SEPARATOR = "/--/";

   final static String resourceDir = System.getProperty("taxonomy.resources.dir", "./");

   final static String dbDir = System.getProperty("taxonomy.db.dir", "./");

   static List<String> findResources(List<String> holder, File file, FileFilter filter) throws Exception {
      if(file.isDirectory()) {
         File[] childs = file.listFiles();
         for(int i = 0; i < childs.length; i++)
            findResources(holder, childs[i], filter);
      } else if(filter.accept(file)) holder.add(file.getCanonicalPath());
      return holder;
   }

   static void initTable() throws Exception 
   {
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");

      try {
         Statement statement = con.createStatement();
         InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("createTable.sql");
         byte[] bytes = IOUtils.readFully(is, is.available(), false);
         statement.executeUpdate(new String(bytes));
      } catch(SQLException ex){
         throw new RuntimeException(ex);
      } finally {
         con.close();
      }
   }

   static void genDataIndex(InputStream is) throws Exception 
   {
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = reader.readLine();
      Set<String> holder = new HashSet<String>();
      StringBuilder b = new StringBuilder();
      while((line = reader.readLine()) != null)
      {
         line = line.substring(1, line.indexOf(']')).trim();
         String[] subLine = line.split(",");
         for(String s : subLine) holder.add(s.trim());
      }

      for(String s : holder)
      {
         b.append("INSERT INTO INDEX_ VALUES (NULL, '" + s + "');\n");
      }
      FileOutputStream os = new FileOutputStream(resourceDir + "insertIndex.sql", false);
      os.write(b.toString().getBytes("UTF-8"));
      os.close();
      con.createStatement().executeUpdate(b.toString());
      con.close();
   }

   static void genDataTag(InputStream is) throws Exception
   {
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = reader.readLine();
      StringBuilder b = new StringBuilder();
      while((line = reader.readLine()) != null)
      {
         line = line.substring(0, line.lastIndexOf(']') + 1).trim();
         String[] subLine = line.split("/--/");
         String name = subLine[0].substring(subLine[0].indexOf('[') + 1, subLine[0].indexOf(']'));
         String explain = subLine[1].substring(subLine[1].indexOf('[') + 1, subLine[1].indexOf(']'));
         b.append("INSERT INTO TAG VALUES (NULL, '" + name + "', '" + explain + "');\n");
      }
      FileOutputStream os = new FileOutputStream(resourceDir + "insertTag.sql", false);
      os.write(b.toString().getBytes("UTF-8"));
      os.close();
      con.createStatement().executeUpdate(b.toString());
      con.close();
   }

   static void genDataGlossary(InputStream is) throws Exception
   {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = reader.readLine();
      StringBuilder b = new StringBuilder();
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");
      while((line = reader.readLine()) != null)
      {
         line = line.substring(0, line.lastIndexOf(']') + 1).trim();
         String[] subLine = line.split("/--/");
         String name = subLine[0].substring(subLine[0].indexOf('[') + 1, subLine[0].indexOf(']'));
         String name_vn = subLine[1].substring(subLine[1].indexOf('[') + 1, subLine[1].indexOf(']'));
         String explain = subLine[2].substring(subLine[2].indexOf('[') + 1, subLine[2].indexOf(']'));
         explain = explain.replaceAll("\'", "\'\'");
         String exam = subLine[3].substring(subLine[3].indexOf('[') + 1, subLine[3].indexOf(']'));
         exam = exam.replaceAll("\'", "\'\'");

         Statement st = con.createStatement();
         if(!name_vn.equals(" ")) 
         {
            String query = "INSERT INTO LOCALES VALUES (NULL, 'vn', '" + name_vn + "');";
            st.executeUpdate(query);
            b.append(query).append('\n');
            ResultSet rs  = st.executeQuery("SELECT last_insert_rowid()  FROM LOCALES");
            rs.next();
            int locale_id = rs.getInt(1);
            query = "INSERT INTO GLOSSARY VALUES (NULL, '" + name + "', '" + locale_id + "', '" + explain+ "', '" + exam + "');";
            b.append(query).append('\n');
         } else {
            String query = "INSERT INTO GLOSSARY VALUES (NULL, '" + name + "', ' ', '" + explain+ "', '" + exam + "');";
            b.append(query).append('\n');
            System.out.println(query);
            st.executeUpdate(query);
         }
      } 
      FileOutputStream os = new FileOutputStream(resourceDir + "insertGlossary.sql", false);
      os.write(b.toString().getBytes("UTF-8"));
      os.close();
      con.close();
   }
   
   static void genDataFamilyAndGenus(InputStream is, String table) throws Exception 
   {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = reader.readLine();
      StringBuilder b = new StringBuilder();
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");
      while((line = reader.readLine()) != null)
      {
         line = line.substring(0, line.lastIndexOf(']') + 1).trim();
         String[] subLine = line.split("/--/");
         
         int king_id = 0;
         String king = subLine[0].substring(subLine[0].indexOf('[') + 1, subLine[0].indexOf(']'));
         if(king.equals("B")) king_id = 1;
         else if(king.equals("Z")) king_id = 2;
         
         String name = subLine[1].substring(subLine[1].indexOf('[') + 1, subLine[1].indexOf(']'));
         String name_en = subLine[2].substring(subLine[2].indexOf('[') + 1, subLine[2].indexOf(']'));
         name_en = name_en.replaceAll("\'", "\'\'");
         String name_vn = subLine[3].substring(subLine[3].indexOf('[') + 1, subLine[3].indexOf(']'));
         String desc = subLine[4].substring(subLine[4].indexOf('[') + 1, subLine[4].indexOf(']'));
         desc = desc.replaceAll("\'", "\'\'");
         
         StringBuilder locale_ids = new StringBuilder();
         Statement st = con.createStatement();
         if(!name_en.equals(" "))
         {
            String query = "INSERT INTO LOCALES VALUES (NULL, 'en', '" + name_en + "');";
            System.out.println(query);
            st.executeUpdate(query);
            b.append(query).append('\n');
            ResultSet rs  = st.executeQuery("SELECT last_insert_rowid()  FROM LOCALES");
            rs.next();
            int locale_id = rs.getInt(1);
            if(locale_ids.length() > 0) locale_ids.append("::").append(locale_id);
            else locale_ids.append(locale_id);
         } 
         if(!name_vn.equals(" "))
         {
            String query = "INSERT INTO LOCALES VALUES (NULL, 'vn', '" + name_vn + "');";
            st.executeUpdate(query);
            b.append(query).append('\n');
            ResultSet rs  = st.executeQuery("SELECT last_insert_rowid()  FROM LOCALES");
            rs.next();
            int locale_id = rs.getInt(1);
            if(locale_ids.length() > 0) locale_ids.append("::").append(locale_id);
            else locale_ids.append(locale_id);
         }
         String query = null;
         if("FAMILY".equals(table)) 
            query = "INSERT INTO " + table + " VALUES (NULL, " + king_id + ",'" + name + "', '" + locale_ids.toString() + "', '" + desc + "', ' ');";
         else if("GENUS".equals(table))
            query = "INSERT INTO " + table + " VALUES (NULL, " + king_id + ",'" + name + "', '" + locale_ids.toString() + "', '" + desc + "', ' ', ' ');";
         b.append(query).append('\n');
         System.out.println(query);
         st.executeUpdate(query);
      }
      String outFile = null;
      if("FAMILY".equals(table)) outFile = "insertFamily.sql";
      else if("GENUS".equals(table)) outFile = "insertGenus.sql";
      FileOutputStream os = new FileOutputStream(resourceDir + outFile, false);
      os.write(b.toString().getBytes("UTF-8"));
      os.close();
      con.close();
   }
   
   static void genDataObject(InputStream is) throws Exception
   {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = reader.readLine();
      StringBuilder b = new StringBuilder();
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");
      while((line = reader.readLine()) != null)
      {
         line = line.substring(0, line.lastIndexOf(']') + 1).trim();
         String[] subLine = line.split("/--/");
         String King = subLine[1]; String Fami = subLine[2];  String Fam2 = subLine[3];  String Inde = subLine[4]; String Genu = subLine[5];  String Gen2 = subLine[6];
         String Gen3 = subLine[7];  String Spec = subLine[8];  String Spe2 = subLine[9]; String Spe3 = subLine[10]; String Gsos = subLine[11]; String Gso2 = subLine[12];
         String Eng1 = subLine[13]; String Eng2 = subLine[14]; String Vie1 = subLine[15]; String Vie2 = subLine[16]; String Keyw = subLine[17]; String Utim = subLine[18];
         String Udat = subLine[19]; String Refs = subLine[20]; String Desc = subLine[21];
         
         //
         int king_id = 0;
         if(King.equals("B")) king_id = 1;
         else if(King.equals("Z")) king_id = 2;
         String familyIds  = buildFamilyIds(con, Fami, Fam2);
         String indexIds = buildIndexIds(con, Inde);
         int genusId = buildGenusId(con, Genu);
         int specId = buildSpecId(con, Spec);
         String tagId = buildTagId(con, Keyw);
      }
   }
   
   static String buildFamilyIds(Connection con, String Fami, String Fam2) throws Exception
   {
      StringBuilder b = new StringBuilder();
      con.createStatement().executeQuery("Select ID from FAMILY");
      return b.toString();
   }
   
   static String buildIndexIds(Connection con, String Inde)
   {
      StringBuilder b = new StringBuilder();
      return b.toString();
   }
   
   static int buildGenusId(Connection con, String Genu)
   {
      return -1;
   }
   
   static int buildSpecId(Connection con, String Spec)
   {
      return -1;
   }
   
   static String buildTagId(Connection con, String Keyw)
   {
      StringBuilder b = new StringBuilder();
      return b.toString();
   }
   
   static void buildGenusVariant(Connection con, int genId, String Gen2, String Gen3)
   {
      
   }
   
   static void buildSpeciesVariant(Connection con, int specId, String Spe2, String Spe3)
   {
      
   }
   
   public static void main(String[] args) throws Exception
   {
      Generator.initTable();
      Generator.genDataIndex(Thread.currentThread().getContextClassLoader().getResourceAsStream("txinde_utf8.txt"));
      Generator.genDataTag(Thread.currentThread().getContextClassLoader().getResourceAsStream("txkeyw_utf8.txt"));
      Generator.genDataGlossary(Thread.currentThread().getContextClassLoader().getResourceAsStream("txglos_utf8.txt"));
      Generator.genDataFamilyAndGenus(Thread.currentThread().getContextClassLoader().getResourceAsStream("txfami_utf8.txt"), "FAMILY");
      Generator.genDataFamilyAndGenus(Thread.currentThread().getContextClassLoader().getResourceAsStream("txgenu_utf8.txt"), "GENUS");
      Generator.genDataObject(Thread.currentThread().getContextClassLoader().getResourceAsStream("txmain_utf8.txt"));
   }
}
