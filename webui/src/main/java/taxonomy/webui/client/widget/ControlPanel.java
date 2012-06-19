/*
 * Copyright (C) 2011 eXo Platform SAS.
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

import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Hai Thanh Nguyen</a>
 * @version $Id$
 * 
 */
public class ControlPanel extends SectionStack implements Application {
	
	public ControlPanel() {
		setWidth(250);
		setShowEdges(true);
		setShowResizeBar(true);

		SectionStackSection mainSection = new SectionStackSection("Dashboard");
		mainSection.setExpanded(true);
		mainSection.addItem(buildTree());
		setSections(mainSection);
	}
	
	private static TreeGrid buildTree() {
		Tree tree = new Tree();
		tree.setModelType(TreeModelType.CHILDREN);
		tree.setRoot(new TreeNode("Root",
         new TreeNode("Main", new TreeNode("Object")),                       		
         new TreeNode("Domain", 
                      new TreeNode("Kingdom"), new TreeNode("Family"), new TreeNode("Genus"), new TreeNode("Species")),
         new TreeNode("Utilites", new TreeNode("Locale"), new TreeNode("Variant"))
         ));
		TreeGrid grid = new TreeGrid();
		grid.setData(tree);
		grid.getData().openAll();
		grid.addNodeClickHandler(new NodeClickHandler() {
			@Override
			public void onNodeClick(NodeClickEvent event) {
				Display display = (Display) ApplicationManager.getInstance().getSystemApp(Display.class);
				display.getTab("View").setPane(new Label(event.getNode().getName()));
//				SC.say(event.getNode().getName());
			}
		});
		return grid;
	}
}
