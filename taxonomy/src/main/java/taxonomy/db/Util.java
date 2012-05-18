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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Hai Thanh Nguyen</a>
 * @version $Id$
 * 
 */
public class Util
{
   static void insertGlossary(StringBuilder b, String line, Connection con) throws Exception
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
      if (!name_vn.equals(" "))
      {
         String query = "INSERT INTO [Locales] VALUES (NULL, 'vn', '" + name_vn + "');";
         st.executeUpdate(query);
         b.append(query).append('\n');
         ResultSet rs = st.executeQuery("SELECT last_insert_rowid()  FROM [Locales]");
         rs.next();
         int locale_id = rs.getInt(1);
         query =
            "INSERT INTO [Glossary] VALUES (NULL, '" + name + "', '" + locale_id + "', '" + explain + "', '" + exam
               + "');";
         b.append(query).append('\n');
      }
      else
      {
         String query = "INSERT INTO [Glossary] VALUES (NULL, '" + name + "', ' ', '" + explain + "', '" + exam + "');";
         b.append(query).append('\n');
         //System.out.println(query);
         st.executeUpdate(query);
      }
   }

   static void insertFamilyAndGenus(StringBuilder b, String line, String table, Connection con) throws Exception
   {
      line = line.substring(0, line.lastIndexOf(']') + 1).trim();
      String[] subLine = line.split("/--/");

      int king_id = 0;
      String king = subLine[0].substring(subLine[0].indexOf('[') + 1, subLine[0].indexOf(']'));
      if (king.equals("B"))
         king_id = 1;
      else if (king.equals("Z"))
         king_id = 2;

      String name = subLine[1].substring(subLine[1].indexOf('[') + 1, subLine[1].indexOf(']'));
      String name_en = subLine[2].substring(subLine[2].indexOf('[') + 1, subLine[2].indexOf(']'));
      name_en = name_en.replaceAll("\'", "\'\'");
      String name_vn = subLine[3].substring(subLine[3].indexOf('[') + 1, subLine[3].indexOf(']'));
      String desc = subLine[4].substring(subLine[4].indexOf('[') + 1, subLine[4].indexOf(']'));
      desc = desc.replaceAll("\'", "\'\'");

      StringBuilder locale_ids = new StringBuilder();
      Statement st = con.createStatement();
      if (!name_en.equals(" "))
      {
         String query = "INSERT INTO [Locales] VALUES (NULL, 'en', '" + name_en + "');";
         //System.out.println(query);
         st.executeUpdate(query);
         b.append(query).append('\n');
         ResultSet rs = st.executeQuery("SELECT last_insert_rowid()  FROM [Locales]");
         rs.next();
         int locale_id = rs.getInt(1);
         if (locale_ids.length() > 0)
            locale_ids.append("::").append(locale_id);
         else
            locale_ids.append(locale_id);
      }
      if (!name_vn.equals(" "))
      {
         String query = "INSERT INTO [Locales] VALUES (NULL, 'vn', '" + name_vn + "');";
         st.executeUpdate(query);
         b.append(query).append('\n');
         ResultSet rs = st.executeQuery("SELECT last_insert_rowid()  FROM [Locales]");
         rs.next();
         int locale_id = rs.getInt(1);
         if (locale_ids.length() > 0)
            locale_ids.append("::").append(locale_id);
         else
            locale_ids.append(locale_id);
      }
      String query = null;
      if ("[Family]".equals(table))
         query =
            "INSERT INTO " + table + " VALUES (NULL, " + king_id + ",'" + name + "', '" + locale_ids.toString()
               + "', '" + desc + "', ' ');";
      else if ("[Genus]".equals(table))
         query =
            "INSERT INTO " + table + " VALUES (NULL, " + king_id + ",'" + name + "', '" + locale_ids.toString()
               + "', '" + desc + "', ' ', ' ');";
      b.append(query).append('\n');
      //System.out.println(query);
      st.executeUpdate(query);
   }

   static int buildGenusSpeciesId(Connection con, String value1, String value2, String value3, String gsos,
      String gso2, String table) throws Exception
   {
      value1 = value1.substring(1, value1.length() - 1);
      value1 = value1.replaceAll("\'", "\'\'");
      value2 = value2.substring(1, value2.length() - 1);
      value2 = value2.replaceAll("\'", "\'\'");
      value3 = value3.substring(1, value3.length() - 1);
      value3 = value3.replaceAll("\'", "\'\'");

      StringBuilder b = new StringBuilder();
      int variant1 = 0;
      int variant2 = 0;
      if (!value2.equals(" ") && !value2.isEmpty())
      {
         int variantType = 0;
         if (gsos.equals("S"))
            variantType = 1;
         else if (gsos.equals("O"))
            variantType = 2;
         con.createStatement().executeUpdate("Insert into [Variant] values(NULL, '" + value2 + "', " + variantType + ")");
         ResultSet tmp = con.createStatement().executeQuery("Select last_insert_rowid() from [Variant]");
         if (tmp.next())
            variant1 = tmp.getInt(1);
      }
      if (!value3.equals(" ") && !value3.isEmpty())
      {
         int variantType = 0;
         if (gso2.equals("S"))
            variantType = 1;
         else if (gso2.equals("O"))
            variantType = 2;
         con.createStatement().executeUpdate("Insert into [Variant] values(NULL, '" + value3 + "', " + variantType + ")");
         ResultSet tmp = con.createStatement().executeQuery("Select last_insert_rowid() from [Variant]");
         if (tmp.next())
            variant2 = tmp.getInt(1);
      }
      if (variant1 != 0)
         b.append(variant1);
      if (variant2 != 0)
         b.append("::").append(variant2);

      ResultSet rs = con.createStatement().executeQuery("Select ID from " + table + " where Name = '" + value1 + "'");
      int genId = 0;
      if (!rs.next())
      {
         if ("GENUS".equalsIgnoreCase(table))
         {
            con.createStatement().executeUpdate(
               "Insert into [Genus] values(NULL, 1, '" + value1 + "', NULL, NULL, NULL, '" + b.toString() + "')");
            ResultSet tmp = con.createStatement().executeQuery("Select last_insert_rowid() FROM [Genus]");
            tmp.next();
            genId = tmp.getInt(1);
         }
         else if ("[Species]".equalsIgnoreCase(table))
         {
            con.createStatement().executeUpdate(
               "Insert into [Species] values(NULL,'" + value1 + "', '" + b.toString() + "')");
            ResultSet tmp = con.createStatement().executeQuery("Select last_insert_rowid() FROM [Species]");
            tmp.next();
            genId = tmp.getInt(1);
         }
      }
      else
      {
         genId = rs.getInt(1);
         con.createStatement().executeUpdate(
            "Update " + table + " set VARIANT_IDS='" + b.toString() + "' where ID = " + genId);
      }
      return genId;
   }

   static String buildFamilyIds(Connection con, String Fami, String Fam2) throws Exception
   {
      StringBuilder b = new StringBuilder();
      Fami = Fami.substring(1, Fami.length() - 1);
      Fam2 = Fam2.substring(1, Fam2.length() - 1);
      ResultSet rs = con.createStatement().executeQuery("Select ID from [Family] where NAME = '" + Fami + "'");

      int id = 0;
      if (!rs.next())
      {
         con.createStatement().executeUpdate("Insert into [Family] values(NULL, 1, '" + Fami + "', NULL, NULL, NULL)");
      }
      else
      {
         id = rs.getInt(1);
      }
      b.append(id);

      if (Fam2.isEmpty() || " ".equals(Fam2))
         return b.toString();
      else
      {
         rs = con.createStatement().executeQuery("Select ID from [Family] where NAME = '" + Fam2 + "'");

         if (!rs.next())
         {
            con.createStatement().executeUpdate("Insert into [Family] values(NULL, 1, '" + Fami + "', NULL, NULL, NULL)");
            ResultSet tmp = con.createStatement().executeQuery("SELECT last_insert_rowid()  FROM [Family]");
            tmp.next();
            id = tmp.getInt(1);
         }
         else
         {
            id = rs.getInt(1);
         }
         b.append("::").append(id);
         return b.toString();
      }
   }

   static String buildIndexIds(Connection con, String Inde) throws Exception
   {
      StringBuilder b = new StringBuilder();
      Inde = Inde.substring(1, Inde.length() - 1);
      if (" ".equals(Inde) || Inde.isEmpty())
         return null;
      String[] indecies = Inde.split(",");
      for (int i = 0; i < indecies.length; i++)
      {
         String index = indecies[i].trim();
         index = index.replaceAll("\'", "\'\'");
         ResultSet rs = con.createStatement().executeQuery("Select ID from [Index] where [VALUE] = '" + index + "'");
         int id = 0;
         if (!rs.next())
         {
            con.createStatement().executeUpdate("Insert into [Index] values(NULL, '" + index + "')");
            ResultSet tmp = con.createStatement().executeQuery("Select last_insert_rowid() from [Index]");
            tmp.next();
            id = tmp.getInt(1);
         }
         else
         {
            id = rs.getInt(1);
         }
         if (i > 0)
            b.append("::");
         b.append(id);
      }
      return b.toString();
   }
}
