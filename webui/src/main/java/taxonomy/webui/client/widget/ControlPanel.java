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

import taxonomy.model.Family;
import taxonomy.webui.client.MockServiceAsync;
import taxonomy.webui.client.TaxonomyDAOServiceAsync;
import taxonomy.webui.client.model.VFamily;
import taxonomy.webui.client.model.VModel;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Hai Thanh Nguyen</a>
 * @version $Id$
 * 
 */
public class ControlPanel extends SectionStack implements Application {
	public ControlPanel() {
		setWidth(250);
		setVisibilityMode(VisibilityMode.MULTIPLE);
		setAnimateSections(true);
		setCanReorderSections(true);
		setShowEdges(true);
		setShowResizeBar(true);

		SectionStackSection mainSection = new SectionStackSection("Main Example Items");
		mainSection.setExpanded(true);
		setSections(mainSection);
		
		DynamicForm daoForm = new DynamicForm();

		ButtonItem mockBtn = new ButtonItem("MockService");
		mockBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				MockServiceAsync service = MockServiceAsync.Util.getInstance();
				service.sayHello(new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {
						SC.say(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						SC.say("RPC call encounter an error.");
					}
				});
			}
		});
		
		
		final TextItem tableNameTxt = new TextItem("tableNameTxt", "Table Name");
		ButtonItem daoBtn = new ButtonItem("DaoService");
		daoBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TaxonomyDAOServiceAsync service = TaxonomyDAOServiceAsync.Util.getInstance();
				service.getMaxId(tableNameTxt.getValueAsString(), new AsyncCallback<Integer>() {
					public void onSuccess(Integer result) {
						SC.say("The Max ID of " + tableNameTxt.getValueAsString() + " : " + result);
					}
					public void onFailure(Throwable caught) {
						SC.say(caught.getMessage());
					}
				});
			}
		});
		
		final TextItem familyIdTxt = new TextItem("familyIdTxt", "Family ID");
		ButtonItem familyBtn = new ButtonItem("GetFamily");
		familyBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TaxonomyDAOServiceAsync service = TaxonomyDAOServiceAsync.Util.getInstance();
				service.getGeneric("taxonomy.model.Family", Integer.parseInt(familyIdTxt.getValueAsString()), new AsyncCallback<VModel>() {
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						SC.say(caught.getMessage());
					}

					public void onSuccess(VModel result) {
						SC.say(((VFamily) result).getId().toString());
					}
				});
			}
		});
		
		daoForm.setFields(mockBtn, tableNameTxt, daoBtn, familyIdTxt, familyBtn);
		mainSection.addItem(daoForm);
	}
}
