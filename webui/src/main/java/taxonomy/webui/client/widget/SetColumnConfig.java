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

import java.util.Iterator;
import java.util.Set;

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
public class SetColumnConfig<M extends VModel, N> extends ColumnConfig<M, Set<N>> {

  public SetColumnConfig(ValueProvider<M, Set<N>> valueProvider, int width, String columnName) {
    super(valueProvider, width, columnName);
    setCell(new AbstractCell<Set<N>>() {
      @Override
      public void render(com.google.gwt.cell.client.Cell.Context context, Set<N> value, SafeHtmlBuilder sb) {
        if (value == null)
          return;
        int index = 0;
        for (Iterator<?> i = value.iterator(); i.hasNext();) {
          sb.appendHtmlConstant("<span style='color:" + ((index % 2 == 0) ? "green" : "blue") + "'>" + i.next()
            + "</span>");
          if (i.hasNext()) {
            sb.appendHtmlConstant("<span> | </span>");
          }
          index++;
        }
      }
    });
  }

  public SetColumnConfig(ValueProvider<M, Set<N>> valueProvider, String columnName) {
    this(valueProvider, 50, columnName);
  }
}
