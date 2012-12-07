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
import java.util.List;

import taxonomy.resources.client.TxDAOServiceAsync;
import taxonomy.resources.client.images.ExampleImages;
import taxonomy.resources.client.model.Utils;
import taxonomy.resources.client.model.VModel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Frame;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public class OperatorToolbar<M extends VModel> extends ToolBar
{
   /** . */
   private final TextButton btnWikipedia;
   
   /** .*/
   private final TextButton btnGoogle;
   
   /**. */
   private final TextButton btnRemove;
   
   /** .*/
   private final TextButton btnUpdate;
   
   public OperatorToolbar(final ModelGridPanel<M> rootPanel)
   {
      // create remove btn.
      btnRemove = new TextButton("Remove", ExampleImages.INSTANCE.remove());
      btnRemove.addSelectHandler(new SelectEvent.SelectHandler()
      {
         @Override
         public void onSelect(SelectEvent event)
         {
            final List<M> items = rootPanel.getCheckBoxSelectionModel().getSelectedItems();
            if(items.size() == 0) return;
            
            ConfirmMessageBox confirmBox = new ConfirmMessageBox("Confirm", "Are you sure you want to remove some items");
            confirmBox.addHideHandler(new HideEvent.HideHandler() {
               @Override
               public void onHide(HideEvent event)
               {
                  Dialog btn = (Dialog)event.getSource();
                  if(btn.getHideButton().getText().equals("Yes"))
                  {
                     StringBuilder sb = new StringBuilder("Delete from ");
                     sb.append(Utils.getTableByClass(items.get(0).getClass()));
                     sb.append(" where id in (");
                     for(Iterator<M> i = items.iterator(); i.hasNext();)
                     {
                        sb.append(i.next().getId());
                        if(i.hasNext()) sb.append(',');
                     }
                     sb.append(")");
                     TxDAOServiceAsync.Util.getInstance().update(sb.toString(), new AsyncCallback<Void>()
                     {
                        @Override
                        public void onSuccess(Void result)
                        {
                           rootPanel.getPagingToolbar().refresh();
                        }
                        
                        @Override
                        public void onFailure(Throwable caught)
                        {
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
      btnAdd.addSelectHandler(new SelectEvent.SelectHandler()
      {
         @Override
         public void onSelect(SelectEvent event)
         {
         }
      });
      
      // create update btn.
      btnUpdate = new TextButton("Update", ExampleImages.INSTANCE.update());
      btnUpdate.addSelectHandler(new SelectEvent.SelectHandler()
      {
         @Override
         public void onSelect(SelectEvent event)
         {
         }
      });
      
      // create filter btn.
      TextButton btnFilter = new TextButton("Filter", ExampleImages.INSTANCE.filter());
      btnFilter.addSelectHandler(new SelectEvent.SelectHandler()
      {
         @Override
         public void onSelect(SelectEvent event)
         {
         }
      });
      
      // create search on wikipedia btn.
      btnWikipedia = new TextButton("Search on Wikipedia", ExampleImages.INSTANCE.wikipedia());
      btnWikipedia.addSelectHandler(new SelectEvent.SelectHandler()
      {
         @Override
         public void onSelect(SelectEvent event)
         {
            List<M> list = rootPanel.getCheckBoxSelectionModel().getSelectedItems();
            if(list.size() == 0) return;
            for(M m : list)
            {
               Frame frame = new Frame();
               frame.setUrl("http://en.wikipedia.org/wiki/" + m.toString());
               TxShell.center.add(frame, new TabItemConfig("Wikipedia - " + m.toString(), true));
               TxShell.center.setActiveWidget(frame);
            }
         }
      });
      
      btnGoogle = new TextButton("Search on Google", new SelectEvent.SelectHandler()
      {
         @Override
         public void onSelect(SelectEvent event)
         {
            List<M> list = rootPanel.getCheckBoxSelectionModel().getSelectedItems();
            if(list.size() == 0) return;
            for(M m : list)
            {
               Window.open("http://www.google.com.vn/search?q=" + m.toString(), null, null);
            }
         }
      });
      btnGoogle.setIcon(ExampleImages.INSTANCE.google());
      
      TextButton btnDeselectAll = new TextButton("Deselect All", new SelectEvent.SelectHandler()
      {
         @Override
         public void onSelect(SelectEvent event)
         {
            rootPanel.getCheckBoxSelectionModel().deselectAll();
            OperatorToolbar.this.disableModifyButton();
         }
      });
      btnDeselectAll.setIcon(ExampleImages.INSTANCE.deselectAll());

      add(btnAdd);
      add(new SeparatorToolItem());
      add(btnUpdate);
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
   }
   
   public void disableModifyButton()
   {
      btnRemove.disable();
      btnUpdate.disable();
      btnWikipedia.disable();
      btnGoogle.disable();
   }
   
   public void enableModifyButton()
   {
      btnRemove.enable();
      btnUpdate.enable();
      btnWikipedia.enable();
      btnGoogle.enable();
   }
}