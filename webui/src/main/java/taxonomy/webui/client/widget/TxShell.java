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

import taxonomy.webui.client.ioc.TxGinjector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.state.client.BorderLayoutStateHandler;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public class TxShell extends BorderLayoutContainer {

	private ContentPanel west;
	private SimpleContainer center;

	@Inject
	public TxShell() {
		monitorWindowResize = true;
		Window.enableScrolling(false);
		setPixelSize(Window.getClientWidth(), Window.getClientHeight());

		setStateful(false);
		setStateId("explorerLayout");

		BorderLayoutStateHandler state = new BorderLayoutStateHandler(this);
		state.loadState();

		HTML north = new HTML();
		north.setHTML("<div id='demo-theme'></div><div id=demo-title>Taxonomy Web Base Application</div>");
		north.getElement().setId("demo-header");

		BorderLayoutData northData = new BorderLayoutData(35);
		setNorthWidget(north, northData);

		BorderLayoutData westData = new BorderLayoutData(200);
		westData.setMargins(new Margins(5, 0, 5, 5));
		westData.setSplit(true);
		westData.setCollapsible(true);
		westData.setCollapseHidden(true);
		westData.setCollapseMini(true);

		west = new ContentPanel();
		west.setHeadingText("Navigation");
		west.setBodyBorder(true);

		MarginData centerData = new MarginData();
		centerData.setMargins(new Margins(5));

		center = new SimpleContainer();
		setWestWidget(west, westData);
		setCenterWidget(center, centerData);
	}

	@Override
	protected void onWindowResize(int width, int height) {
		setPixelSize(width, height);
	}
}
