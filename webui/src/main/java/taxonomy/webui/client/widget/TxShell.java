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
import java.util.List;

import taxonomy.resources.client.images.ExampleImages;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public class TxShell extends BorderLayoutContainer {

   /** .*/
	private ContentPanel west;
	
	/** .*/
	static TabPanel center;

	@Inject
	public TxShell() {
		monitorWindowResize = true;
		Window.enableScrolling(false);
		setPixelSize(Window.getClientWidth(), Window.getClientHeight());

		setStateful(false);
		setStateId("explorerLayout");

//		BorderLayoutStateHandler state = new BorderLayoutStateHandler(this);
//		state.loadState();

		HTML north = new HTML();
		north.setHTML("<div id='demo-theme'></div><div id=demo-title>Taxonomy Web Base Application</div>");
		north.getElement().setId("demo-header");

		BorderLayoutData northData = new BorderLayoutData(35);
		setNorthWidget(north, northData);

		BorderLayoutData westData = new BorderLayoutData(250);
		westData.setMargins(new Margins(5, 0, 5, 5));
		westData.setSplit(true);
		westData.setCollapsible(true);
		westData.setCollapseHidden(true);
		westData.setCollapseMini(true);

		west = new ContentPanel();
		west.setHeadingText("Navigation");
		west.setBodyBorder(true);
		VerticalLayoutContainer westContainer = new VerticalLayoutContainer();
		west.add(westContainer);
		
		final Tree<String, String> tree = buildTreeNavigation();
		ToolBar toolbar = new ToolBar();
		toolbar.add(new TextButton("Expand All", new SelectHandler()
      {
         @Override
         public void onSelect(SelectEvent event)
         {
            tree.expandAll();
         }
      }));
		toolbar.add(new TextButton("Collapse All", new SelectHandler()
		{
         @Override
         public void onSelect(SelectEvent event)
         {
            tree.collapseAll();
         }
		}));
		westContainer.add(toolbar, new VerticalLayoutData(1, -1));
		westContainer.add(tree, new VerticalLayoutData(1, 1));
		
		MarginData centerData = new MarginData();
		centerData.setMargins(new Margins(5));

		center = new TabPanel();
		center.setTabScroll(true);
		center.setCloseContextMenu(true);
		
		setWestWidget(west, westData);
		setCenterWidget(center, centerData);
	}
	
	public Tree<String, String> buildTreeNavigation() {
		TreeStore<String> store = new TreeStore<String>(new ModelKeyProvider<String>() {
			@Override
			public String getKey(String item) {
				return item;
			}
		});
		
		String main = "Main";
		store.add(main);
		List<String> child1 = new ArrayList<String>();
		Collections.addAll(child1, "[Kingdom]", "[Family]", "[Genus]", "[Species]", "[NaturalObject]");
		store.add(main, child1);
		
		String ext = "Extension";
		store.add(ext);
		List<String> child2	= new ArrayList<String>();
		Collections.addAll(child2, "[Index]", "[Tag]", "[Glossary]", "[Variant]", "[Locales]");
		store.add(ext, child2);
		
		final Tree<String, String> tree = new Tree<String, String>(store, new ValueProvider<String, String>() {
			@Override
			public String getValue(String object) { return object; }
			@Override
			public void setValue(String object, String value) {}
			@Override
			public String getPath() { return null; }
		});
		
		tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.table());
		tree.getSelectionModel().addSelectionHandler(new SelectionHandler<String>() {
			
			@Override
			public void onSelection(SelectionEvent<String> event) {
				final String item = event.getSelectedItem();
				ModelGridPanel panel = null;
				if("Main".equals(item) || "Extension".equals(item)) 
				{
				   tree.setExpanded(item, true, true);
				   return;
				}
				
				Tables table = Tables.valueOf(item.substring(1, item.length() - 1).toUpperCase());
				switch (table)
            {
               case KINGDOM :
                  panel = ModelGridFactory.createKingdom(); break;
               case FAMILY:
                  panel = ModelGridFactory.createFamily(); break;
               case GENUS:
                  panel = ModelGridFactory.createGenus(); break;
               case SPECIES:
                  panel = ModelGridFactory.createSpecies(); break;
               case NATURALOBJECT:
                  panel = ModelGridFactory.createNObject(); break;
               case VARIANT:
                  panel = ModelGridFactory.createVariant(); break;
               case LOCALES:
                  panel = ModelGridFactory.createLocale(); break;
               case GLOSSARY:
                  panel = ModelGridFactory.createGlossary(); break;
               case INDEX:
                  panel = ModelGridFactory.createIndex(); break;
               case TAG:
                  panel = ModelGridFactory.createTag(); break;
               default :
                  break;
            }
				
				if(center.getWidgetIndex(panel) < 0)
				{
				   TabItemConfig tabConfig = new TabItemConfig(item, true);
				   center.add(panel, tabConfig);
				}
				center.setActiveWidget(panel);
				tree.getSelectionModel().deselect(item);
			};
		});
		return tree;
	}
	
	@Override
	protected void onWindowResize(int width, int height) {
		setPixelSize(width, height);
	}
}
