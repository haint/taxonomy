package taxonomy;

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import sun.misc.IOUtils;
import taxonomy.model.Kingdom;

public class App 
{
   public static void main( String[] args ) throws Exception
   {
      Kingdom bio = new Kingdom();
      bio.setId(1);
      bio.setCode("B");
      bio.setName("Botanical");

      Kingdom zoo = new Kingdom();
      zoo.setId(2);
      zoo.setCode("Z");
      zoo.setName("Zoological");

      Class.forName("org.sqlite.JDBC");
      String dbDir = System.getProperty("taxonomy.db.dir", "target/");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");

      try {
         Statement statement = con.createStatement();
         InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("createTable.sql");
         byte[] bytes = IOUtils.readFully(is, is.available(), false);
//         System.out.println(new String(bytes));
//         statement.executeUpdate(new String(bytes));
         statement.executeUpdate("drop table if exists kingdom");
         statement.executeUpdate("create table kingdom(id integer primary key autoincrement, code string unique not null, name string not null)");
         statement.executeUpdate("insert into kingdom values(null, 'B', 'Nancy''s notes')");
         statement.executeUpdate("insert into kingdom values(100, 'Z', '[acorn]_vn::[quả kiên]')");
         statement.executeUpdate("insert into kingdom values(101, 'C', '[acorn]_vn::[quả giẻ]')");

//         ResultSet rs = statement.executeQuery("SELECT * FROM Kingdom where name in ('[acorn]_vn::[quả kiên]', '[acorn]_vn::[quả giẻ]')");
         ResultSet rs = statement.executeQuery("SELECT * FROM Kingdom");
         rs.next();
         System.out.println(rs.getInt(1));
         System.out.println(rs.getString(2));
         System.out.println(rs.getString(3));
         rs.next();
         System.out.println(rs.getInt(1));
         System.out.println(rs.getString(2));
         System.out.println(rs.getString(3));
         rs = statement.executeQuery("SELECT last_insert_rowid() FROM kingdom");
         rs.next();
         System.out.println(rs.getInt(1));
      } catch(SQLException ex){
         throw new RuntimeException(ex);
      } finally {
         con.close();
      }
   }

   static List<String> findResources(List<String> holder, File file, FileFilter filter) throws Exception {
      if(file.isDirectory()) {
         File[] childs = file.listFiles();
         for(int i = 0; i < childs.length; i++)
            findResources(holder, childs[i], filter);
      } else if(filter.accept(file)) holder.add(file.getCanonicalPath());
      return holder;
   }
}
