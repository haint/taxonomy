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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Anchor;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public class WestMenuPanel extends ContentPanel {
  
  public interface WestMenuTemplate extends XTemplates {
    @XTemplate(source = "WestMenu.html")
    SafeHtml get();
  }
  
  public interface GeneralIntroTemplate extends XTemplates {
    @XTemplate(source = "GeneralIntro.html")
    SafeHtml get();
  }
  
  public interface HowToRespondTemplate extends XTemplates {
    @XTemplate(source = "HowToRespond.html")
    SafeHtml get();
  }
  
  public interface DataRetrievalTemplate extends XTemplates {
    @XTemplate(source = "DataRetrieval.html")
    SafeHtml get();
  }
  
  public interface DataEntryTemplate extends XTemplates {
    @XTemplate(source = "DataEntry.html")
    SafeHtml get();
  }
  
  /** .*/
  private static VerticalLayoutContainer general;
  
  /** .*/
  private static VerticalLayoutContainer howto;
  
  /** .*/
  private static VerticalLayoutContainer retrieval;
  
  /** .*/
  private static VerticalLayoutContainer entry;
  
  public WestMenuPanel() {
    setHeaderVisible(false);
    WestMenuTemplate template = GWT.create(WestMenuTemplate.class);
    HtmlLayoutContainer container = new HtmlLayoutContainer(template.get());
    
    //General introductions content
    general = new VerticalLayoutContainer();
    general.setScrollMode(ScrollMode.AUTOY);
    GeneralIntroTemplate generalTemplate = GWT.create(GeneralIntroTemplate.class);
    general.add(new HtmlLayoutContainer(generalTemplate.get()));
    
    //How to respond content
    howto = new VerticalLayoutContainer();
    howto.setScrollMode(ScrollMode.AUTOY);
    HowToRespondTemplate howtoTemplate = GWT.create(HowToRespondTemplate.class);
    howto.add(new HtmlLayoutContainer(howtoTemplate.get()));
    
    //Data Retrieval content
    retrieval = new VerticalLayoutContainer();
    retrieval.setScrollMode(ScrollMode.AUTOY);
    DataRetrievalTemplate retrievalTemplate = GWT.create(DataRetrievalTemplate.class);
    retrieval.add(new HtmlLayoutContainer(retrievalTemplate.get()));
    
    //Date Entry content
    entry = new VerticalLayoutContainer();
    entry.setScrollMode(ScrollMode.AUTOY);
    DataEntryTemplate entryTemplate = GWT.create(DataEntryTemplate.class);
    entry.add(new HtmlLayoutContainer(entryTemplate.get()));
    
    Anchor generalLink = new Anchor("General");
    /**
     * Can add class style by:
     * introLink.addStyleName("btn btn-primary");
     */
    generalLink.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        if (TxShell.center.getWidgetIndex(general) < 0) {
          TxShell.center.add(general, new TabItemConfig("General Introductions", true));
          TxShell.center.setActiveWidget(general);
        } else {
          TxShell.center.setActiveWidget(general);
        }
      }
    });
    
    Anchor howtoLink = new Anchor("How to respond");
    howtoLink.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        if (TxShell.center.getWidgetIndex(howto) < 0) {
          TxShell.center.add(howto, new TabItemConfig("How to repond", true));
          TxShell.center.setActiveWidget(howto);
        } else {
          TxShell.center.setActiveWidget(howto);
        }
      }
    });
    
    Anchor retrievalLink = new Anchor("Data Retrieval");
    retrievalLink.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        if (TxShell.center.getWidgetIndex(retrieval) < 0) { 
          TxShell.center.add(retrieval, new TabItemConfig("Data Retrieval", true));
          TxShell.center.setActiveWidget(retrieval);
        } else {
          TxShell.center.setActiveWidget(retrieval);
        }
      }
    });
    
    Anchor entryLink = new Anchor("Data Entry");
    entryLink.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        if (TxShell.center.getWidgetIndex(entry) < 0) {
          TxShell.center.add(entry, new TabItemConfig("Data Retrieval", true));
          TxShell.center.setActiveWidget(entry);
        } else {
          TxShell.center.setActiveWidget(entry);
        }
      }
    });
    
    container.add(generalLink, new HtmlData(".general"));
    container.add(howtoLink, new HtmlData(".howto"));
    container.add(retrievalLink, new HtmlData(".retrieval"));
    container.add(entryLink, new HtmlData(".entry"));
    add(container);
  }
}
