/*
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
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
package taxonomy.resources.client.model;

import java.util.HashMap;
import java.util.Map;

import taxonomy.webui.client.widget.Tables;

/**
 * Author : Nguyen Thanh Hai haithanh0809@gmail.com Dec 17, 2012
 */
public class Utils {
  /** . */
  private static Map<String, Class<? extends VModel>> tableClazz = null;

  public static Class<? extends VModel> getClassByTableName(String tableName) {
    return tableClazz.get(tableName);
  }

  static {
    Map<String, Class<? extends VModel>> map = new HashMap<String, Class<? extends VModel>>();
    map.put(Tables.FAMILY.getName(), VFamily.class);
    map.put(Tables.GENUS.getName(), VGenus.class);
    map.put(Tables.GLOSSARY.getName(), VGlossary.class);
    map.put(Tables.INDEX.getName(), VIndex.class);
    map.put(Tables.KINGDOM.getName(), VKingdom.class);
    map.put(Tables.LOCALES.getName(), VLocale.class);
    map.put(Tables.OBJECT.getName(), VNaturalObject.class);
    map.put(Tables.SPECIES.getName(), VSpecies.class);
    map.put(Tables.TAG.getName(), VTag.class);
    map.put(Tables.VARIANT.getName(), VVariant.class);
    tableClazz = map;
  }

  public static String getTableByClass(Class<? extends VModel> clazz) {
    return clazzTable.get(clazz);
  }

  /** . */
  private static Map<Class<? extends VModel>, String> clazzTable = null;

  static {
    Map<Class<? extends VModel>, String> map = new HashMap<Class<? extends VModel>, String>();
    map.put(VFamily.class, Tables.FAMILY.getName());
    map.put(VGenus.class, Tables.GENUS.getName());
    map.put(VGlossary.class, Tables.GLOSSARY.getName());
    map.put(VIndex.class, Tables.INDEX.getName());
    map.put(VKingdom.class, Tables.KINGDOM.getName());
    map.put(VLocale.class, Tables.LOCALES.getName());
    map.put(VNaturalObject.class, Tables.OBJECT.getName());
    map.put(VSpecies.class, Tables.SPECIES.getName());
    map.put(VTag.class, Tables.TAG.getName());
    map.put(VVariant.class, Tables.VARIANT.getName());
    clazzTable = map;
  }
}
