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

import taxonomy.resources.client.model.VNaturalObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public class ModelViewPanel extends ContentPanel {
  
  public interface ModelViewTemplate extends XTemplates {
    @XTemplate(source = "VNaturalModelObject.html")
    SafeHtml getTemplate(VNaturalObject obj);
  }

  public ModelViewPanel(VNaturalObject obj) {
    setHeaderVisible(false);
    ModelViewTemplate template = GWT.create(ModelViewTemplate.class);
    HtmlLayoutContainer container = new HtmlLayoutContainer(template.getTemplate(obj));
    
    Anchor kingdomLink = new Anchor(obj.getKingdom().getName());
    kingdomLink.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        String text = ((Anchor)event.getSource()).getText();
        Frame frame = new Frame();
        frame.setUrl("http://en.wikipedia.org/wiki/" + text);
        TxShell.center.add(frame, new TabItemConfig("Wikipedia - " + text, true));
        TxShell.center.setActiveWidget(frame);
      }
    });
    container.add(kingdomLink, new HtmlData(".Kingdom"));
    container.add(new HTML("<img src='images/photos/all_kids.jpg' class='img-polaroid'/>"), new HtmlData(".avatar"));
    add(container);
  }
}
