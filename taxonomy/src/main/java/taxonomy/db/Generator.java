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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:haint@exoplatform.com">Nguyen Thanh Hai</a>
 * 
 * @datOct 3, 2011 For field multi value we will use seperator is '::' (ignore
 *         dash) In text data fields separate by '/--/'
 */
@Deprecated
public class Generator
{
   final static String SEPARATOR = "/--/";

   final static String resourceDir = System.getProperty("taxonomy.resources.dir", "target/");

   final static String dbDir = System.getProperty("taxonomy.db.dir", "target/");

   static List<String> findResources(List<String> holder, File file, FileFilter filter) throws Exception
   {
      if (file.isDirectory())
      {
         File[] childs = file.listFiles();
         for (int i = 0; i < childs.length; i++)
            findResources(holder, childs[i], filter);
      }
      else if (filter.accept(file))
         holder.add(file.getCanonicalPath());
      return holder;
   }

   static void initTable() throws Exception
   {
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");

      try
      {
         Statement statement = con.createStatement();
         InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("createTable.sql");
         statement.executeUpdate(Main.getStringFromInputStream(is));
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

   static void genDataIndex(InputStream is) throws Exception
   {
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = reader.readLine();
      Set<String> holder = new HashSet<String>();
      StringBuilder b = new StringBuilder();
      while ((line = reader.readLine()) != null)
      {
         line = line.substring(1, line.indexOf(']')).trim();
         String[] subLine = line.split(",");
         for (String s : subLine)
            holder.add(s.trim());
      }

      for (String s : holder)
      {
         b.append("INSERT INTO [Index] VALUES (NULL, '" + s + "');\n");
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
      while ((line = reader.readLine()) != null)
      {
         line = line.substring(0, line.lastIndexOf(']') + 1).trim();
         String[] subLine = line.split("/--/");
         String name = subLine[0].substring(subLine[0].indexOf('[') + 1, subLine[0].indexOf(']'));
         String explain = subLine[1].substring(subLine[1].indexOf('[') + 1, subLine[1].indexOf(']'));
         b.append("INSERT INTO [Tag] VALUES (NULL, '" + name + "', '" + explain + "');\n");
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
      while ((line = reader.readLine()) != null)
      {
         Util.insertGlossary(b, line, con);
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
      while ((line = reader.readLine()) != null)
      {
         Util.insertFamilyAndGenus(b, line, table, con);
      }
      String outFile = null;
      if ("[Family]".equals(table))
         outFile = "insertFamily.sql";
      else if ("[Genus]".equals(table))
         outFile = "insertGenus.sql";
      FileOutputStream os = new FileOutputStream(resourceDir + outFile, false);
      os.write(b.toString().getBytes("UTF-8"));
      os.close();
      con.close();
   }

   static void genDataObject(InputStream is) throws Exception
   {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = reader.readLine();
//      StringBuilder b = new StringBuilder();
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");
      int count = 0;
      while ((line = reader.readLine()) != null)
      {
         line = line.substring(0, line.lastIndexOf(']') + 1).trim();
         String[] subLine = line.split(" /--/ ");
         String King = subLine[1].trim();
         String Fami = subLine[2];
         String Fam2 = subLine[3];
         String Inde = subLine[4];
         String Genu = subLine[5];
//         if("[ ]".equals(Genu)) System.out.println("Genu - " + line);
         String Gen2 = subLine[6];
         String Gen3 = subLine[7];
         String Spec = subLine[8];
//         if("[ ]".equals(Spec)) System.out.println("Spec - " + line);
         String Spe2 = subLine[9];
         String Spe3 = subLine[10];
         String Gsos = subLine[11];
         String Gso2 = subLine[12];
         String Eng1 = subLine[13];
         String Eng2 = subLine[14];
         String en_name = buildName(Eng1, Eng2);
         String Vie1 = subLine[15];
         String Vie2 = subLine[16];
         String vn_name = buildName(Vie1,Vie2);
         String Keyw = subLine[17];
         String Utim = subLine[18];
         String Udat = subLine[19];
         String Refs = subLine[20];
         Refs = Refs.substring(1, Refs.length() - 1);
         Refs = Refs.replaceAll("\'", "\'\'");
         String Desc = subLine[21];
         Desc = Desc.substring(1, Desc.length() - 1);
         Desc = Desc.replaceAll("\'", "\'\'");

         //
         int king_id = 0;
         if (King.equals("[B]"))
            king_id = 1;
         else if (King.equals("[Z]"))
            king_id = 2;
         else 
        	 throw new IllegalStateException("Kingdom code: " + King + " invalid");
         //
         
         String familyIds = Util.buildFamilyIds(con, Fami, Fam2);
         String indexIds = Util.buildIndexIds(con, Inde);
         int genusId = Util.buildGenusSpeciesId(con, Genu, Gen2, Gen3, Gsos, Gso2, "[Genus]");
         int specId = Util.buildGenusSpeciesId(con, Spec, Spe2, Spe3, Gsos, Gso2, "[Species]");
         String tagId = buildTagId(con, Keyw);
         String createDate = buildCreateDate(con, Utim, Udat);
         //System.out.println("Insert into [NaturalObject] values (NULL, " + king_id + ", '" + familyIds + "', '" + indexIds + "', " + genusId + ", " + specId + ", '" + en_name + "', '" + vn_name + "', '" + tagId + "', '" + createDate + "', NULL, '" + Refs + "', '" + Desc + "', NULL)");
         con.createStatement().executeUpdate(
            "Insert into [NaturalObject] values (NULL, " + king_id + ", '" + familyIds + "', '" + indexIds + "', " + genusId + ", " + specId + ", '" + en_name + "', '" + vn_name + "', '" + tagId + "', '" + createDate + "', NULL, '" + Refs + "', '" + Desc + "', NULL)");
         count++;
      }
      System.out.println("Total lines: " + count);
   }
   
   static String buildName(String name1, String name2)
   {
      StringBuilder b = new StringBuilder();
      if(!name1.equals("[ ]"))
      {
         b.append(name1);
      }
      if(!name2.equals("[ ]"))
      {
         b.append("::").append(name2);
      }
      String tmp = b.toString();
      tmp = tmp.replaceAll("\'", "\'\'");
      return tmp;
   }
   
   static String buildCreateDate(Connection con, String Utim, String Udat) throws Exception 
   {
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      Utim = Utim.substring(1, Utim.length() - 1).trim();
      Udat = Udat.substring(1, Udat.length() - 1).trim();
      
      int hour = 0;
      int minute = 0;
      int second = 0;
      if(!Utim.equals(" ") && !Utim.isEmpty()) {
         String[] args1 = Utim.split(":");
         hour = Integer.valueOf(args1[0]);
         minute = Integer.valueOf(args1[1]);
         second = Integer.valueOf(args1[2]);
      }
      
      int month = 0;
      int day = 0;
      int year = 0;
      if(!Udat.equals(" ") && !Udat.isEmpty())
      {
         String[] args2 = Udat.split("/");
         month = Integer.valueOf(args2[0]) - 1;
         day = Integer.valueOf(args2[1]);
         year = Integer.valueOf(args2[2]) + 1900;
      }
      
      Calendar cal = Calendar.getInstance();
      cal.set(year, month, day, hour, minute, second);
      
      return dateFormat.format(cal.getTimeInMillis());
   }
   
   static String buildTagId(Connection con, String Keyw) throws Exception
   {
      StringBuilder b = new StringBuilder();
      String[] tags = Keyw.split("/");
      for(int i = 0; i < tags.length; i++) {
         String tag = tags[i].trim();
         tag = tag.replaceAll("\'", "\'\'");
         ResultSet rs = con.createStatement().executeQuery("Select ID from [Tag] where Name = '" + tag + "'");
         if(rs.next()) 
         {
            if(i > 0) b.append("::");
            b.append(rs.getInt(1));
         }
         else
         {
            con.createStatement().executeUpdate("Insert into [Tag] values(NULL, '" + tag + "', NULL)");
            ResultSet tmp = con.createStatement().executeQuery("Select last_insert_rowid() from [Tag]");
            if(i > 0) b.append("::");
            b.append(tmp.getInt(1));
         }
            
      }
      return b.toString();
   }

   public static void main(String[] args) throws Exception
   {
       Generator.initTable();
       Generator.genDataIndex(Thread.currentThread().getContextClassLoader().getResourceAsStream("txinde_utf8.txt"));
       Generator.genDataTag(Thread.currentThread().getContextClassLoader().getResourceAsStream("txkeyw_utf8.txt"));
       Generator.genDataGlossary(Thread.currentThread().getContextClassLoader().getResourceAsStream("txglos_utf8.txt"));
       Generator.genDataFamilyAndGenus(Thread.currentThread().getContextClassLoader().getResourceAsStream("txfami_utf8.txt"),
       "[Family]");
       Generator.genDataFamilyAndGenus(Thread.currentThread().getContextClassLoader().getResourceAsStream("txgenu_utf8.txt"),
       "[Genus]");
      Generator.genDataObject(Thread.currentThread().getContextClassLoader().getResourceAsStream("txmain_utf8.txt"));
   }
}
