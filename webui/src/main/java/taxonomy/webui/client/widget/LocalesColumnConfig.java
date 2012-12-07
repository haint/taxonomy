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
package taxonomy.webui.client.widget;

import java.util.Set;

import taxonomy.resources.client.model.VLocale;
import taxonomy.resources.client.model.VModel;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 * 
 */
public class LocalesColumnConfig<M extends VModel> extends ColumnConfig<M, Set<VLocale>>
{

   public LocalesColumnConfig(ValueProvider<? super M, Set<VLocale>> valueProvider)
   {
      super(valueProvider, 100, "Locales");

      setCell(new AbstractCell<Set<VLocale>>()
      {
         @Override
         public void render(com.google.gwt.cell.client.Cell.Context context, Set<VLocale> value, SafeHtmlBuilder sb)
         {
            if (value == null)
            {
               return;
            }
            String vn = null;
            String en = null;
            for (VLocale v : value)
            {
               if ("en".equals(v.getName()))
               {
                  en = v.getValue();
               }
               else if ("vn".equals(v.getName()))
               {
                  vn = v.getValue();
               }
            }
            if(vn != null)
            {
               sb.appendHtmlConstant("<div style='float:left; width: 200px;'><span style='color: blue'><img src='images/flags/vn.png'/> : " + vn + "</span></div>");
            }
            if(en != null)
            {
               sb.appendHtmlConstant("<span style='color: green'><img src='images/flags/gb.png'/> : " + en + "</span>");
            }
         }
      });
   }
}
