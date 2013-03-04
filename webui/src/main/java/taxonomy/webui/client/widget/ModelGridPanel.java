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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import taxonomy.resources.client.TxDAOServiceAsync;
import taxonomy.resources.client.model.VModel;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SortChangeEvent;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 * 
 */
public class ModelGridPanel<M extends VModel> extends FramedPanel {
  static int PAGE_SIZE = 50;

  /** . */
  private final String name;

  /** . */
  private final Grid<M> grid;

  /** . */
  private final PagingToolBar toolbar;

  /** . */
  private final CheckBoxSelectionModel<M> sm;

  public String getName() {
    return name;
  }

  public ModelGridPanel(String tableName, List<ColumnConfig<M, ?>> cf) {
    //
    this.name = tableName;
    setHeaderVisible(false);
    
    //
    DataProxy<PagingLoadConfig, PagingLoadResult<M>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<M>>() {
      @Override
      public void load(PagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<M>> callback) {
        TxDAOServiceAsync.Util.getInstance().select(name, loadConfig, callback);
      }
    };

    //
    ListStore<M> store = new ListStore<M>(new ModelKeyProvider<M>() {
      @Override
      public String getKey(M item) {
        return item.getId().toString();
      }
    });

    //
    final PagingLoader<PagingLoadConfig, PagingLoadResult<M>> loader =
      new PagingLoader<PagingLoadConfig, PagingLoadResult<M>>(proxy);
    loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, M, PagingLoadResult<M>>(store));

    //
    toolbar = new PagingToolBar(PAGE_SIZE);
    toolbar.getElement().getStyle().setProperty("borderBottom", "none");
    toolbar.bind(loader);

    //
    sm = new CheckBoxSelectionModel<M>(new IdentityValueProvider<M>());
    OperatorToolbar.rootPanel = this;
    sm.addSelectionHandler(new SelectionHandler<M>() {
      @Override
      public void onSelection(SelectionEvent<M> event) {
        OperatorToolbar.enableModifyButton();
      }
    });
    sm.addSelectionChangedHandler(new SelectionChangedHandler<M>() {
      @Override
      public void onSelectionChanged(SelectionChangedEvent<M> event) {
        if (event.getSelection().size() == 0)
          OperatorToolbar.disableModifyButton();
      };
    });

    List<ColumnConfig<M, ?>> config = new ArrayList<ColumnConfig<M, ?>>();
    config.add(sm.getColumn());
    config.addAll(cf);
    ColumnModel<M> cm = new ColumnModel<M>(config);
    grid = new Grid<M>(store, cm) {
      @Override
      protected void onAfterFirstAttach() {
        super.onAfterFirstAttach();
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
          @Override
          public void execute() {
            loader.load();
          }
        });
      }
    };
    grid.setBorders(false);
    grid.setSelectionModel(sm);
    grid.setLoadMask(true);
    grid.setLoader(loader);
    grid.addSortChangeHandler(new SortChangeEvent.SortChangeHandler() {
      @Override
      public void onSortChange(SortChangeEvent event) {
        event.getSortInfo().getSortDir().comparator(new Comparator<M>() {
          @Override
          public int compare(M o1, M o2) {
            return o1.toString().compareTo(o2.toString());
          }
        });
      }
    });

    grid.getView().setForceFit(true);
    grid.getView().setAutoFill(true);
    grid.getView().setStripeRows(true);
    grid.getView().setColumnLines(true);

    //
    VerticalLayoutContainer con = new VerticalLayoutContainer();
    con.setBorders(true);
    con.add(toolbar, new VerticalLayoutData(1, -1));
    con.add(grid, new VerticalLayoutData(1, 1));
    con.getScrollSupport().setScrollMode(ScrollMode.AUTOY);
    setWidget(con);
  }

  public CheckBoxSelectionModel<M> getCheckBoxSelectionModel() {
    return sm;
  }

  public Grid<M> getGrid() {
    return grid;
  }

  public PagingToolBar getPagingToolbar() {
    return toolbar;
  }
}
