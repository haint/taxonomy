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

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 * 
 */
public class TxServletContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("Initital context");
    System.setProperty("taxonomy.context", this.getClass().getName());

    /*
     * TxDAOService daoService = new TxDAOServiceImpl(); try { long start =
     * System.currentTimeMillis();
     * 
     * //Kingdom createCache(daoService, "[Kingdom]");
     * System.out.println("created Kingdom model cache in " +
     * (System.currentTimeMillis() - start)/ 1000.0 + " (s)"); start =
     * System.currentTimeMillis(); //Family createCache(daoService, "[Family]");
     * System.out.println("created Family model cache in " +
     * (System.currentTimeMillis() - start)/ 1000.0 + " (s)"); start =
     * System.currentTimeMillis(); //Genus createCache(daoService, "[Genus]");
     * System.out.println("created Genus model cache in " +
     * (System.currentTimeMillis() - start)/ 1000.0 + " (s)"); start =
     * System.currentTimeMillis(); //Species createCache(daoService,
     * "[Species]"); System.out.println("created Species model cache in " +
     * (System.currentTimeMillis() - start)/ 1000.0 + " (s)"); start =
     * System.currentTimeMillis(); //NaturalObject createCache(daoService,
     * "[NaturalObject]"); System.out.println("created Nobjects model cache in "
     * + (System.currentTimeMillis() - start)/ 1000.0 + " (s)"); start =
     * System.currentTimeMillis(); //Locale createCache(daoService,
     * "[Locales]"); System.out.println("created Locale model cache in " +
     * (System.currentTimeMillis() - start)/ 1000.0 + " (s)"); start =
     * System.currentTimeMillis(); //Indexes createCache(daoService, "[Index]");
     * System.out.println("created Index model cache in " +
     * (System.currentTimeMillis() - start)/ 1000.0 + " (s)"); start =
     * System.currentTimeMillis(); //Tags createCache(daoService, "[Tag]");
     * System.out.println("created Tag model cache in " +
     * (System.currentTimeMillis() - start)/ 1000.0 + " (s)"); start =
     * System.currentTimeMillis(); //Variant createCache(daoService,
     * "[Variant]"); System.out.println("created Variant model cache in " +
     * (System.currentTimeMillis() - start)/ 1000.0 + " (s)"); start =
     * System.currentTimeMillis(); //Glossary createCache(daoService,
     * "[Glossary]"); System.out.println("created Glossary model cache in " +
     * (System.currentTimeMillis() - start)/ 1000.0 + " (s)"); start =
     * System.currentTimeMillis(); } catch(Exception e) { throw new
     * RuntimeException(e); }
     */
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

  }

  /*
   * private void createCache(TxDAOService daoService, String tableName) throws
   * Exception { Connection con = ORMTools.getConnection(); ResultSet rs =
   * con.createStatement().executeQuery("select count(id) from " + tableName);
   * rs.next(); int totalsize = rs.getInt(1); con.close(); List<VModel> models =
   * daoService.select(tableName, 0, totalsize); for(VModel model : models) {
   * TxCachedKey key = new TxCachedKey(tableName + "_" + model.getId(),
   * Filter.ID); TxCache.getInstance().put(key, model);
   * if("[NaturalObject]".equals(tableName)) { VNaturalObject obj =
   * (VNaturalObject) model; for(String s : obj.getEnNameSet()) { if(s.length()
   * == 0) continue; System.out.println(s); } } } }
   */
}
