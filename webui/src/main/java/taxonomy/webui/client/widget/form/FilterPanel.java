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
package taxonomy.webui.client.widget.form;

import java.util.Collection;
import java.util.List;

import taxonomy.resources.client.LoaderServiceAsync;
import taxonomy.resources.client.model.FilterModel;
import taxonomy.webui.client.widget.Tables;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 * 
 */
public class FilterPanel extends FramedPanel {

  private static final FilterPanel instance = new FilterPanel();

  public static FilterPanel getInstance() {
    return instance;
  }

  private FilterPanel() {
    /*
     * setHeadingText("Filter Form");
     * setBodyStyle("background: none; padding: 10px;");
     * 
     * final VerticalLayoutContainer p = new VerticalLayoutContainer(); add(p);
     * 
     * //ID
     * LoaderServiceAsync.Util.getInstance().load(Tables.NATURALOBJECT.getName
     * (), new AsyncCallback<List<FilterModel>>() {
     * 
     * @Override public void onSuccess(List<FilterModel> result) {
     * p.add(createField("ID", result), new VerticalLayoutData(1, -1)); }
     * 
     * @Override public void onFailure(Throwable caught) {
     * caught.printStackTrace(); } });
     * 
     * //Kingdom
     * LoaderServiceAsync.Util.getInstance().load(Tables.KINGDOM.getName(), new
     * AsyncCallback<List<String>>() {
     * 
     * @Override public void onFailure(Throwable caught) {
     * caught.printStackTrace(); }
     * 
     * @Override public void onSuccess(List<String> result) {
     * p.add(createField("Kingdom", result), new VerticalLayoutData(1, -1)); }
     * });
     * 
     * //Family
     * LoaderServiceAsync.Util.getInstance().load(Tables.FAMILY.getName(), new
     * AsyncCallback<List<String>>() {
     * 
     * @Override public void onFailure(Throwable caught) {
     * caught.printStackTrace(); }
     * 
     * @Override public void onSuccess(List<String> result) {
     * p.add(createField("Family", result), new VerticalLayoutData(1, -1)); }
     * });
     * 
     * //Genus
     * LoaderServiceAsync.Util.getInstance().load(Tables.GENUS.getName(), new
     * AsyncCallback<List<String>>() {
     * 
     * @Override public void onFailure(Throwable caught) {
     * caught.printStackTrace(); }
     * 
     * @Override public void onSuccess(List<String> result) {
     * p.add(createField("Genus", result), new VerticalLayoutData(1, -1)); } });
     * 
     * //Species
     * LoaderServiceAsync.Util.getInstance().load(Tables.SPECIES.getName(), new
     * AsyncCallback<List<String>>() {
     * 
     * @Override public void onFailure(Throwable caught) {
     * caught.printStackTrace(); }
     * 
     * @Override public void onSuccess(List<String> result) {
     * p.add(createField("Species", result), new VerticalLayoutData(1, -1)); }
     * });
     * 
     * //Indecies
     * LoaderServiceAsync.Util.getInstance().name(Tables.INDEX.getName(), new
     * AsyncCallback<List<String>>() {
     * 
     * @Override public void onFailure(Throwable caught) {
     * caught.printStackTrace(); }
     * 
     * @Override public void onSuccess(List<String> result) {
     * p.add(createField("Index", result), new VerticalLayoutData(1, -1)); } });
     * 
     * //Tags LoaderServiceAsync.Util.getInstance().name(Tables.TAG.getName(),
     * new AsyncCallback<List<String>>() {
     * 
     * @Override public void onFailure(Throwable caught) {
     * caught.printStackTrace(); }
     * 
     * @Override public void onSuccess(List<String> result) {
     * p.add(createField("Tag", result), new VerticalLayoutData(1, -1)); } });
     * 
     * //En name LoaderServiceAsync.Util.getInstance().localeName("en", new
     * AsyncCallback<List<String>>() {
     * 
     * @Override public void onFailure(Throwable caught) {
     * caught.printStackTrace(); }
     * 
     * @Override public void onSuccess(List<String> result) {
     * p.add(createField("En Name", result), new VerticalLayoutData(1, -1)); }
     * });
     * 
     * //Vn name LoaderServiceAsync.Util.getInstance().localeName("vn", new
     * AsyncCallback<List<String>>() {
     * 
     * @Override public void onFailure(Throwable caught) {
     * caught.printStackTrace(); }
     * 
     * @Override public void onSuccess(List<String> result) {
     * p.add(createField("Vn Name", result), new VerticalLayoutData(1, -1)); }
     * });
     */
  }

  private FieldLabel createField(String label, Collection<FilterModel> data) {
    ListStore<String> store = new ListStore<String>(new ModelKeyProvider<String>() {
      @Override
      public String getKey(String item) {
        return item;
      }
    });
    store.addAll(null);
    ComboBox<String> combox = new ComboBox<String>(store, new LabelProvider<String>() {
      @Override
      public String getLabel(String item) {
        return item;
      }
    });

    combox.setTypeAhead(true);
    combox.setForceSelection(true);
    combox.setTriggerAction(TriggerAction.ALL);
    return new FieldLabel(combox, label);
  }
}
