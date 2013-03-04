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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import taxonomy.resources.client.TxDAOServiceAsync;
import taxonomy.resources.client.images.ExampleImages;
import taxonomy.resources.client.model.Utils;
import taxonomy.resources.client.model.VModel;
import taxonomy.resources.client.model.VNaturalObject;
import taxonomy.webui.client.widget.form.FilterPanel;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.menu.SeparatorMenuItem;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 * 
 */
public class OperatorToolbar<M extends VModel> extends ToolBar {
  /** . */
  private static TextButton btnWikipedia;

  /** . */
  private static TextButton btnGoogle;

  /** . */
  private static TextButton btnRemove;

  /** .*/
  static ModelGridPanel rootPanel;
  
  public OperatorToolbar() {
    // create remove btn.
    btnRemove = new TextButton("Remove", ExampleImages.INSTANCE.remove());
    btnRemove.addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        final List<M> items = rootPanel.getCheckBoxSelectionModel().getSelectedItems();
        if (items.size() == 0)
          return;

        ConfirmMessageBox confirmBox = new ConfirmMessageBox("Confirm", "Are you sure you want to remove some items in " + rootPanel.getName());
        confirmBox.addHideHandler(new HideEvent.HideHandler() {
          @Override
          public void onHide(HideEvent event) {
            Dialog btn = (Dialog)event.getSource();
            if (btn.getHideButton().getText().equals("Yes")) {
              StringBuilder sb = new StringBuilder("Delete from ");
              sb.append(Utils.getTableByClass(items.get(0).getClass()));
              sb.append(" where id in (");
              for (Iterator<M> i = items.iterator(); i.hasNext();) {
                sb.append(i.next().getId());
                if (i.hasNext())
                  sb.append(',');
              }
              sb.append(")");
              TxDAOServiceAsync.Util.getInstance().update(sb.toString(), new AsyncCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                  rootPanel.getPagingToolbar().refresh();
                }

                @Override
                public void onFailure(Throwable caught) {
                  AlertMessageBox mgs = new AlertMessageBox("Error", caught.getMessage());
                  mgs.show();
                  caught.printStackTrace();
                }
              });
            }
          }
        });
        confirmBox.show();
      }
    });

    // create add btn.
    TextButton btnAdd = new TextButton("Add", ExampleImages.INSTANCE.addNew());
    btnAdd.addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
      }
    });

    // create filter btn.
    TextButton btnFilter = new TextButton("Filter", ExampleImages.INSTANCE.filter());
    btnFilter.addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        Window window = new Window();
        window.setModal(true);
        window.setBlinkModal(true);
        window.setPixelSize(350, 400);
        window.add(FilterPanel.getInstance());
        window.show();
      }
    });

    // create search on wikipedia btn.
    btnWikipedia = new TextButton("Search on Wikipedia", ExampleImages.INSTANCE.wikipedia());
    btnWikipedia.addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        List<M> list = rootPanel.getCheckBoxSelectionModel().getSelectedItems();
        if (list.size() == 0)
          return;
        for (M m : list) {
          Frame frame = new Frame();
          frame.setUrl("http://en.wikipedia.org/wiki/" + m.toString());
          TxShell.center.add(frame, new TabItemConfig("Wikipedia - " + m.toString(), true));
          TxShell.center.setActiveWidget(frame);
        }
      }
    });

    btnGoogle = new TextButton("Search on Google", new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        List<M> list = rootPanel.getCheckBoxSelectionModel().getSelectedItems();
        if (list.size() == 0)
          return;
        for (M m : list) {
          com.google.gwt.user.client.Window.open("http://www.google.com.vn/search?q=" + m.toString(), null, null);
        }
      }
    });
    btnGoogle.setIcon(ExampleImages.INSTANCE.google());

    TextButton btnDeselectAll = new TextButton("Deselect All", new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        rootPanel.getCheckBoxSelectionModel().deselectAll();
        OperatorToolbar.this.disableModifyButton();
      }
    });
    btnDeselectAll.setIcon(ExampleImages.INSTANCE.deselectAll());

    addMenuButton();
    add(new SeparatorToolItem());
    add(btnAdd);
    add(new SeparatorToolItem());
    add(btnRemove);
    add(new SeparatorToolItem());
    add(btnFilter);
    add(new SeparatorToolItem());
    add(btnWikipedia);
    add(new SeparatorToolItem());
    add(btnGoogle);
    add(new SeparatorToolItem());
    add(new FillToolItem());
    add(new SeparatorToolItem());
    add(btnDeselectAll);
    setHeight(50);
    disableModifyButton();
    
    //Temporary
    foo();
  }
  
  private void addMenuButton() {
    SelectionHandler<Item> handler = new SelectionHandler<Item>() {
      @Override
      public void onSelection(SelectionEvent<Item> event) {
        MenuItem item = (MenuItem)event.getSelectedItem();
        String text = item.getText();
        
        ModelGridPanel panel = null;
        Tables table = Tables.valueOf(text.substring(1, text.length() - 1).toUpperCase());
        switch (table) {
          case KINGDOM :
            panel = ModelGridFactory.createKingdom();
            break;
          case FAMILY :
            panel = ModelGridFactory.createFamily();
            break;
          case GENUS :
            panel = ModelGridFactory.createGenus();
            break;
          case SPECIES :
            panel = ModelGridFactory.createSpecies();
            break;
          case NATURALOBJECT :
            panel = ModelGridFactory.createNObject();
            break;
          case VARIANT :
            panel = ModelGridFactory.createVariant();
            break;
          case LOCALES :
            panel = ModelGridFactory.createLocale();
            break;
          case GLOSSARY :
            panel = ModelGridFactory.createGlossary();
            break;
          case INDEX :
            panel = ModelGridFactory.createIndex();
            break;
          case TAG :
            panel = ModelGridFactory.createTag();
            break;
          default :
            break;
        }

        if (TxShell.center.getWidgetIndex(panel) < 0) {
          TabItemConfig tabConfig = new TabItemConfig(text, true);
          TxShell.center.add(panel, tabConfig);
        }
        TxShell.center.setActiveWidget(panel);
      }
    };
    
    List<String> child1 = new ArrayList<String>();
    Collections.addAll(child1, "[Kingdom]", "[Family]", "[Genus]", "[Species]", "[NaturalObject]");
    
    List<String> child2 = new ArrayList<String>();
    Collections.addAll(child2, "[Index]", "[Tag]", "[Glossary]", "[Variant]", "[Locales]");

    TextButton menuTextItem = new TextButton("Menu");
    menuTextItem.setIcon(ExampleImages.INSTANCE.menu());
    
    Menu modelsMenu = new Menu();
    modelsMenu.addSelectionHandler(handler);
    
    for (String s : child1) {
      MenuItem item = new MenuItem(s);
      modelsMenu.add(item);
    }
    modelsMenu.add(new SeparatorMenuItem());
    for (String s : child2) {
      MenuItem item = new MenuItem(s);
      modelsMenu.add(item);
    }
    menuTextItem.setMenu(modelsMenu);
    add(menuTextItem);
  }

  static void disableModifyButton() {
    btnRemove.disable();
    btnWikipedia.disable();
    btnGoogle.disable();
  }

  static void enableModifyButton() {
    btnRemove.enable();
    btnWikipedia.enable();
    btnGoogle.enable();
  }
  
  public interface CodeSnippetStyle extends CssResource {
    String cell1();
    String cell2();
    String cell3();
  }
  
  public interface CodeSnippetHtml extends XTemplates {
    @XTemplate(source = "CodeSnippet.html")
    SafeHtml getTemplate();
  }
  
  private void foo() {
    TextButton demo = new TextButton("Demo", new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        final Window window = new Window();
        window.setBodyBorder(false);
        window.setHeadingText("Hello world Dialog");
        window.setMaximizable(true);
        window.setModal(true);
        window.setWidth(500);
        window.setHeight("auto");
        
        TxDAOServiceAsync.Util.getInstance().getGeneric(VNaturalObject.class.getName(), 1, new AsyncCallback<VModel>() {
          @Override
          public void onFailure(Throwable caught) {
            window.add(new HTML(caught.getMessage()));
            caught.printStackTrace();
            window.show();
          }

          @Override
          public void onSuccess(VModel result) {
            System.out.println(result);
            ModelViewPanel panel = new ModelViewPanel((VNaturalObject)result);
            window.add(panel);
            window.show();
          }
        });
      }
    });
    add(demo);
  }
}
