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

import java.util.HashMap;

import taxonomy.resources.client.model.VFamily;
import taxonomy.resources.client.model.VGenus;
import taxonomy.resources.client.model.VGlossary;
import taxonomy.resources.client.model.VIndex;
import taxonomy.resources.client.model.VKingdom;
import taxonomy.resources.client.model.VLocale;
import taxonomy.resources.client.model.VModel;
import taxonomy.resources.client.model.VNaturalObject;
import taxonomy.resources.client.model.VSpecies;
import taxonomy.resources.client.model.VTag;
import taxonomy.resources.client.model.VVariant;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 * 
 */
public class TableMap extends HashMap<String, Class<? extends VModel>> {
  static TableMap INSTANCE = new TableMap();

  private TableMap() {
    put("[Family]", VFamily.class);
    put("[Genus]", VGenus.class);
    put("[Glossary]", VGlossary.class);
    put("[Index]", VIndex.class);
    put("[Kingdom]", VKingdom.class);
    put("[Locales]", VLocale.class);
    put("[NaturalObject]", VNaturalObject.class);
    put("[Species]", VSpecies.class);
    put("[Tag]", VTag.class);
    put("[Variant]", VVariant.class);
  }
}
