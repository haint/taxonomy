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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import taxonomy.resources.client.images.ExampleImages;
import taxonomy.resources.client.model.VFamily;
import taxonomy.resources.client.model.VGenus;
import taxonomy.resources.client.model.VGlossary;
import taxonomy.resources.client.model.VIndex;
import taxonomy.resources.client.model.VKingdom;
import taxonomy.resources.client.model.VLocale;
import taxonomy.resources.client.model.VNaturalObject;
import taxonomy.resources.client.model.VSpecies;
import taxonomy.resources.client.model.VTag;
import taxonomy.resources.client.model.VVariant;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.RowExpander;
import com.sencha.gxt.widget.core.client.grid.RowNumberer;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public class ModelGridFactory
{

   /** .*/
   private static ModelGridPanel<VKingdom> kingdom;
   
   /** .*/
   private static ModelGridPanel<VGenus> genus;
   
   /** .*/
   private static ModelGridPanel<VFamily> family;
   
   /** .*/
   private static ModelGridPanel<VSpecies> species;
   
   /** .*/
   private static ModelGridPanel<VNaturalObject> objects;
   
   /** .*/
   private static ModelGridPanel<VLocale> locales;
   
   /** . */
   private static ModelGridPanel<VVariant> variant;
   
   /** .*/
   private static ModelGridPanel<VTag> tag;
   
   /** . */
   private static ModelGridPanel<VIndex> index;
   
   /** . */
   private static ModelGridPanel<VGlossary> glossary;
   
   interface GlossaryProperties extends PropertyAccess<VGlossary>
   {
      ValueProvider<VGlossary, Integer> id();
      
      ValueProvider<VGlossary, String> name();
      
      ValueProvider<VGlossary, String> example();
      
      ValueProvider<VGlossary, Set<VLocale>> locales();
   }
   
   interface IndexProperties extends PropertyAccess<VIndex>
   {
      ValueProvider<VIndex, Integer> id();
      
      ValueProvider<VIndex, String> value();
   }

   interface KingdomProperties extends PropertyAccess<VKingdom>
   {
      @Path("id")
      ModelKeyProvider<VKingdom> id();

      ValueProvider<VKingdom, String> name();

      ValueProvider<VKingdom, String> code();
   }
   
   interface FamilyProperties extends PropertyAccess<VFamily>
   {
      ValueProvider<VFamily, Integer> id();
      
      ValueProvider<VFamily, VKingdom> kingdom();
      
      ValueProvider<VFamily, Set<VLocale>> locales();
      
      ValueProvider<VFamily, String> name();
   }
   
   interface GenusProperites extends FamilyProperties
   {
      ValueProvider<VGenus, Set<VVariant>> variants(); 
   }
   
   interface SpeciesProperties extends PropertyAccess<VSpecies>
   {
      ValueProvider<VSpecies, Integer> id();
      
      ValueProvider<VSpecies, String> name();
      
      ValueProvider<VSpecies, Set<VVariant>> variants();
   }
   
   interface ObjectProperties extends PropertyAccess<VNaturalObject>
   {
      ValueProvider<VNaturalObject, Integer> id();
      
      ValueProvider<VNaturalObject, VKingdom> kingdom();
      
      ValueProvider<VNaturalObject, Set<VFamily>> families();
      
      ValueProvider<VNaturalObject, VGenus> genus();
      
      ValueProvider<VNaturalObject, VSpecies> species();
      
      ValueProvider<VNaturalObject, Set<VIndex>> indecies();
      
      ValueProvider<VNaturalObject, Set<VTag>> tags();
      
      ValueProvider<VNaturalObject, Set<String>> enNameSet();
      
      ValueProvider<VNaturalObject, Set<String>> vnNameSet();
   }
   
   interface LocalesProperties extends PropertyAccess<VLocale>
   {
      ValueProvider<VLocale, Integer> id();
      
      ValueProvider<VLocale, String> name();
      
      ValueProvider<VLocale, String> value();
   }
   
   interface VariantPropertis extends PropertyAccess<VVariant>
   {
      ValueProvider<VVariant, Integer> id();
      
      ValueProvider<VVariant, String> value();
   }
   
   interface TagProperties extends PropertyAccess<VTag>
   {
      ValueProvider<VTag, Integer> id();
      
      ValueProvider<VTag, String> name();
      
      ValueProvider<VTag, String> explaintion();
   }
   
   public static ModelGridPanel<VKingdom> createKingdom()
   {
      if(kingdom == null)
      {
         KingdomProperties properties = GWT.create(KingdomProperties.class);
         List<ColumnConfig<VKingdom, ?>> cf = new ArrayList<ColumnConfig<VKingdom,?>>();
         cf.add(new RowNumberer<VKingdom>(new IdentityValueProvider<VKingdom>()));
         cf.add(new ColumnConfig<VKingdom, String>(properties.name(), 200, "Name"));
         cf.add(new ColumnConfig<VKingdom, String>(properties.code(), 150, "Code"));
         kingdom = new ModelGridPanel<VKingdom>(Tables.KINGDOM.getName(), cf);
      }
      return kingdom;
   }
   
   public static ModelGridPanel<VFamily> createFamily()
   {
      if(family == null)
      {
         FamilyProperties properties = GWT.create(FamilyProperties.class);
         List<ColumnConfig<VFamily, ?>> cf = new ArrayList<ColumnConfig<VFamily,?>>();
         
         IdentityValueProvider<VFamily> identity = new IdentityValueProvider<VFamily>();
         final RowExpander<VFamily> desc = new RowExpander<VFamily>(identity, new AbstractCell<VFamily>()
         {
            @Override
            public void render(com.google.gwt.cell.client.Cell.Context context, VFamily value, SafeHtmlBuilder sb)
            {
               sb.appendHtmlConstant("<div style='float:left; margin: 5px 5px 10px;'><img style='height:100px' src='" +
               		(value.getAvatar() != null ? value.getAvatar() : "images/photos/all_kids.jpg") + "'/></div>");
               sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Description:</b> " + value.getDescription() + "</p>");
            }
         });
         
         cf.add(desc);
         cf.add(new ColumnConfig<VFamily, Integer>(properties.id(), 20, "ID"));
         cf.add(new ColumnConfig<VFamily, String>(properties.name(), 50, "Name"));
         cf.add(new ColumnConfig<VFamily, VKingdom>(properties.kingdom(), 50, "Kingdom"));
         cf.add(new LocalesColumnConfig<VFamily>(properties.locales()));
         family = new ModelGridPanel<VFamily>(Tables.FAMILY.getName(), cf);
         foo(desc, family);
      }
      return family;
   }
   
   private static void foo(final RowExpander desc, ModelGridPanel gridPanel) {
      desc.initPlugin(gridPanel.getGrid());
      gridPanel.getGrid().addRowClickHandler(new RowClickEvent.RowClickHandler()
      {
         @Override
         public void onRowClick(RowClickEvent event)
         {
            ModelGridFactory.collapseAll(desc);
            desc.expandRow(event.getRowIndex());
         }
      });
      PagingToolBar toolbar = gridPanel.getPagingToolbar();
      toolbar.add(new SeparatorToolItem());
      TextButton btnExpand = new TextButton("Expand All", ExampleImages.INSTANCE.add());
      btnExpand.addSelectHandler(new SelectEvent.SelectHandler()
      {
         @Override
         public void onSelect(SelectEvent event)
         {
            expandAll(desc);
         }
      });
      toolbar.add(btnExpand);
      toolbar.add(new SeparatorToolItem());
      TextButton btnCollapse = new TextButton("Collapse All", ExampleImages.INSTANCE.connect());
      btnCollapse.addSelectHandler(new SelectEvent.SelectHandler()
      {
         @Override
         public void onSelect(SelectEvent event)
         {
            collapseAll(desc);
         }
      });
      toolbar.add(btnCollapse);
      toolbar.add(new SeparatorToolItem());
   }
   
   public static ModelGridPanel<VGenus> createGenus()
   {
      if(genus == null)
      {
         GenusProperites properties = GWT.create(GenusProperites.class);
         List<ColumnConfig<VGenus, ?>> cf = new ArrayList<ColumnConfig<VGenus,?>>();
         
         IdentityValueProvider<VGenus> identity = new IdentityValueProvider<VGenus>();
         final RowExpander<VGenus> desc = new RowExpander<VGenus>(identity, new AbstractCell<VGenus>()
         {
            @Override
            public void render(com.google.gwt.cell.client.Cell.Context context, VGenus value, SafeHtmlBuilder sb)
            {
               sb.appendHtmlConstant("<div style='float:left; margin: 5px 5px 10px;'><img style='height:100px' src='" +
                     (value.getAvatar() != null ? value.getAvatar() : "images/photos/all_kids.jpg") + "'/></div>");
               sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Description:</b> " + value.getDescription() + "</p>");
            }
         });
         
         cf.add(desc);
         cf.add(new ColumnConfig<VGenus, Integer>(properties.id(), 20, "ID"));
         cf.add(new ColumnConfig<VGenus, String>(properties.name(), 50, "Name"));
         cf.add(new SetColumnConfig<VGenus, VVariant>(properties.variants(), "Variant"));
         cf.add(new ColumnConfig<VGenus, VKingdom>(properties.kingdom(), 50, "Kingdom"));
         cf.add(new LocalesColumnConfig<VGenus>(properties.locales()));
         genus = new ModelGridPanel<VGenus>(Tables.GENUS.getName(), cf);
         foo(desc, genus);
      }
      return genus;
   }
   
   public static ModelGridPanel<VSpecies> createSpecies()
   {
      if(species == null)
      {
         SpeciesProperties properties = GWT.create(SpeciesProperties.class);
         List<ColumnConfig<VSpecies, ?>> cf = new ArrayList<ColumnConfig<VSpecies,?>>();
         cf.add(new ColumnConfig<VSpecies, Integer>(properties.id(), 20, "ID"));
         cf.add(new ColumnConfig<VSpecies, String>(properties.name(), 50, "Name"));
         cf.add(new SetColumnConfig<VSpecies, VVariant>(properties.variants(), "Variant"));
         species = new ModelGridPanel<VSpecies>(Tables.SPECIES.getName(), cf);
      }
      return species;
   }
 
   public static ModelGridPanel<VNaturalObject> createNObject()
   {
      if(objects == null)
      {
         ObjectProperties properties = GWT.create(ObjectProperties.class);
         List<ColumnConfig<VNaturalObject, ?>> cf = new ArrayList<ColumnConfig<VNaturalObject,?>>();
         IdentityValueProvider<VNaturalObject> identity = new IdentityValueProvider<VNaturalObject>();
         final RowExpander<VNaturalObject> desc = new RowExpander<VNaturalObject>(identity, new AbstractCell<VNaturalObject>() {
            @Override
            public void render(com.google.gwt.cell.client.Cell.Context context, VNaturalObject value, SafeHtmlBuilder sb)
            {
               sb.appendHtmlConstant("<div style='float:left; margin: 5px 5px 10px;'><img style='height:100px' src='" +
                        (value.getAvatar() != null ? value.getAvatar() : "images/photos/all_kids.jpg") + "'/></div>");
               sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><span><b>Reference:</b> " + value.getReferences() + "<span><br/><b>Description:</b> " + value.getDesc() + "</p>");
            }
         });
         cf.add(desc);
         cf.add(new ColumnConfig<VNaturalObject, Integer>(properties.id(), 20, "ID"));
         cf.add(new ColumnConfig<VNaturalObject, VKingdom>(properties.kingdom(), 50, "Kingdom"));
         cf.add(new SetColumnConfig<VNaturalObject, VFamily>(properties.families(), "Family"));
         cf.add(new ColumnConfig<VNaturalObject, VGenus>(properties.genus(), 50, "Genus"));
         cf.add(new ColumnConfig<VNaturalObject, VSpecies>(properties.species(), 50, "Species"));
         cf.add(new SetColumnConfig<VNaturalObject, String>(properties.vnNameSet(), 80, "Vietnamese Name"));
         cf.add(new SetColumnConfig<VNaturalObject, String>(properties.enNameSet(), 80, "English Name"));
         cf.add(new SetColumnConfig<VNaturalObject, VIndex>(properties.indecies(), "Indecies"));
         cf.add(new SetColumnConfig<VNaturalObject, VTag>(properties.tags(), "Tags"));
         cf.add(new ColumnConfig<VNaturalObject, String>(new ValueProvider<VNaturalObject, String>()
         {
            @Override
            public String getValue(VNaturalObject object)
            {
               return object.getCreateDate();
            }

            @Override
            public void setValue(VNaturalObject object, String value)
            {
               try
               {
                  object.setCreateDate(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).parse(value));
               }
               catch (Exception e)
               {
                  object.setCreateDate(new Date());
               }
            }

            @Override
            public String getPath()
            {
               return "createDate";
            }
         }, 50, "Created Date"));
         cf.add(new ColumnConfig<VNaturalObject, String>(new ValueProvider<VNaturalObject, String>()
         {
            @Override
            public String getValue(VNaturalObject object)
            {
               return object.getModifyDate();
            }

            @Override
            public void setValue(VNaturalObject object, String value)
            {
               try
               {
                  object.setCreateDate(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).parse(value));
               }
               catch (Exception e)
               {
                  object.setCreateDate(new Date());
               }
            }

            @Override
            public String getPath()
            {
               return "modifyDate";
            }
         }, 50, "Modify Date"));
         objects = new ModelGridPanel<VNaturalObject>(Tables.NATURALOBJECT.getName(), cf);
         foo(desc, objects);
      }
      return objects;
   }
   
   public static ModelGridPanel<VLocale> createLocale()
   {
      if(locales == null)
      {
         List<ColumnConfig<VLocale, ?>> cf = new ArrayList<ColumnConfig<VLocale,?>>();
         LocalesProperties properties = GWT.create(LocalesProperties.class);
         cf.add(new ColumnConfig<VLocale, Integer>(properties.id(), 20,"ID"));
         ColumnConfig<VLocale, String> name = new ColumnConfig<VLocale, String>(properties.name(), 50, "Name");
         name.setCell(new AbstractCell<String>(){
            @Override
            public void render(com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb)
            {
               sb.appendHtmlConstant("<img src='images/flags/" + (value.equals("en") ? "gb.png" : "vn.png") + "' />");
            }
         });
         cf.add(name);
         cf.add(new ColumnConfig<VLocale, String>(properties.value(), 100, "Value"));
         locales = new ModelGridPanel<VLocale>(Tables.LOCALES.getName(), cf);
      }
      return locales;
   }
   
   public static ModelGridPanel<VVariant> createVariant()
   {
      if(variant == null)
      {
         List<ColumnConfig<VVariant, ?>> cf = new ArrayList<ColumnConfig<VVariant,?>>();
         VariantPropertis properties = GWT.create(VariantPropertis.class);
         cf.add(new ColumnConfig<VVariant, Integer>(properties.id(), 20, "Id"));
         cf.add(new ColumnConfig<VVariant, String>(properties.value(), 100, "Value"));
         cf.add(new ColumnConfig<VVariant, String>(new ValueProvider<VVariant, String>()
         {
            @Override
            public String getValue(VVariant object)
            {
               if(object.getType() == 1) return "Synonym";
               else if(object.getType() == 2) return "Old";
               else return "Unknown";
            }

            public void setValue(VVariant object, String value) { object.setType(Integer.parseInt(value)); }

            public String getPath() { return null; }
            
         }, 50,"Type"));
         variant = new ModelGridPanel<VVariant>(Tables.VARIANT.getName(), cf);
      }
      return variant;
   }
   
   
   public static ModelGridPanel<VTag> createTag()
   {
      if(tag == null)
      {
         TagProperties properties = GWT.create(TagProperties.class);
         List<ColumnConfig<VTag, ?>> cf = new ArrayList<ColumnConfig<VTag,?>>();
         cf.add(new ColumnConfig<VTag, Integer>(properties.id(), 20, "Id"));
         cf.add(new ColumnConfig<VTag, String>(properties.name(), 50, "Name"));
         cf.add(new ColumnConfig<VTag, String>(properties.explaintion(), 100, "Explaintion"));
         tag = new ModelGridPanel<VTag>(Tables.TAG.getName(), cf);
      }
      return tag;
   }
   
   public static ModelGridPanel<VIndex> createIndex()
   {
      if(index == null)
      {
         List<ColumnConfig<VIndex, ?>> cf = new ArrayList<ColumnConfig<VIndex,?>>();
         IndexProperties properties = GWT.create(IndexProperties.class);
         cf.add(new ColumnConfig<VIndex, Integer>(properties.id(), 20, "Id"));
         cf.add(new ColumnConfig<VIndex, String>(properties.value(), 100, "Value"));
         index = new ModelGridPanel<VIndex>(Tables.INDEX.getName(), cf);
      }
      return index;
   }
   
   
   public static ModelGridPanel<VGlossary> createGlossary()
   {
      if(glossary == null)
      {
         List<ColumnConfig<VGlossary, ?>> cf = new ArrayList<ColumnConfig<VGlossary,?>>();
         GlossaryProperties properties = GWT.create(GlossaryProperties.class);
         
         IdentityValueProvider<VGlossary> identity = new IdentityValueProvider<VGlossary>();
         final RowExpander<VGlossary> desc = new RowExpander<VGlossary>(identity, new AbstractCell<VGlossary>()
         {
            @Override
            public void render(com.google.gwt.cell.client.Cell.Context context, VGlossary value, SafeHtmlBuilder sb)
            {
               sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Explaintion:</b> " + value.getExplaintion() + "</p>");
            }
         });
         
         cf.add(desc);
         cf.add(new ColumnConfig<VGlossary, Integer>(properties.id(), 20, "Id"));
         cf.add(new ColumnConfig<VGlossary, String>(properties.name(), 50, "Name"));
         cf.add(new ColumnConfig<VGlossary, String>(properties.example(), 50, "Example"));
         cf.add(new LocalesColumnConfig<VGlossary>(properties.locales()));
         glossary = new ModelGridPanel<VGlossary>(Tables.GLOSSARY.getName(), cf);
         desc.initPlugin(glossary.getGrid());
         glossary.getGrid().addRowClickHandler(new RowClickEvent.RowClickHandler() {
            @Override
            public void onRowClick(RowClickEvent event)
            {
               ModelGridFactory.collapseAll(desc);
               desc.expandRow(event.getRowIndex());
            }
         });
         PagingToolBar toolbar = glossary.getPagingToolbar();
         toolbar.add(new SeparatorToolItem());
         TextButton btnExpand = new TextButton("Expand All", ExampleImages.INSTANCE.add());
         btnExpand.addSelectHandler(new SelectEvent.SelectHandler()
         {
            @Override
            public void onSelect(SelectEvent event)
            {
               expandAll(desc);
            }
         });
         toolbar.add(btnExpand);
         toolbar.add(new SeparatorToolItem());
         TextButton btnCollapse = new TextButton("Collapse All", ExampleImages.INSTANCE.connect());
         btnCollapse.addSelectHandler(new SelectEvent.SelectHandler()
         {
            @Override
            public void onSelect(SelectEvent event)
            {
               collapseAll(desc);
            }
         });
         toolbar.add(btnCollapse);
         toolbar.add(new SeparatorToolItem());
      }
      return glossary;
   }
   
   private static void collapseAll(RowExpander expander) {
      for(int i = 0; i < ModelGridPanel.PAGE_SIZE; i++)
      {
         expander.collapseRow(i);
      }
   }
   
   private static void expandAll(RowExpander expander) {
      for(int i = 0; i < ModelGridPanel.PAGE_SIZE; i++)
      {
         expander.expandRow(i);
      }
   }
}
