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
package taxonomy.resources.server;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import taxonomy.model.Family;
import taxonomy.model.Genus;
import taxonomy.model.Glossary;
import taxonomy.model.Index;
import taxonomy.model.Kingdom;
import taxonomy.model.Locale;
import taxonomy.model.Model;
import taxonomy.model.Species;
import taxonomy.model.Tag;
import taxonomy.model.Variant;
import taxonomy.webui.client.model.VFamily;
import taxonomy.webui.client.model.VGenus;
import taxonomy.webui.client.model.VGlossary;
import taxonomy.webui.client.model.VIndex;
import taxonomy.webui.client.model.VKingdom;
import taxonomy.webui.client.model.VLocale;
import taxonomy.webui.client.model.VModel;
import taxonomy.webui.client.model.VSpecies;
import taxonomy.webui.client.model.VTag;
import taxonomy.webui.client.model.VVariant;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * @version $Id$
 *
 */
public class Tools {
	
	private static Map<Class, Class> deserializeHolder = new HashMap<Class, Class>();
	
	private static Map<Class, Class> serializeHolder = new HashMap<Class, Class>();
	
	static {
		deserializeHolder.put(VFamily.class, Family.class);
		serializeHolder.put(Family.class, VFamily.class);
		
		deserializeHolder.put(VGenus.class, Genus.class);
		serializeHolder.put(Genus.class, VGenus.class);
		
		deserializeHolder.put(VGlossary.class, Glossary.class);
		serializeHolder.put(Glossary.class, VGlossary.class);
		
		deserializeHolder.put(VIndex.class, Index.class);
		serializeHolder.put(Index.class, VIndex.class);
		
		deserializeHolder.put(VKingdom.class, Kingdom.class);
		serializeHolder.put(Kingdom.class, VKingdom.class);
		
		deserializeHolder.put(VLocale.class, Locale.class);
		serializeHolder.put(Locale.class, VLocale.class);
		
		deserializeHolder.put(VSpecies.class, Species.class);
		serializeHolder.put(Species.class, VSpecies.class);
		
		deserializeHolder.put(VTag.class, Tag.class);
		serializeHolder.put(Tag.class, VTag.class);
		
		deserializeHolder.put(VVariant.class, Variant.class);
		serializeHolder.put(Variant.class, VVariant.class);
	}

	public static VModel serialize(String clazz, Model model) throws Exception {
		VModel vmodel = (VModel)Class.forName(clazz).newInstance();
		Method[] methods = vmodel.getClass().getMethods();
		for(Method m : methods) {
			String methodName = m.getName();
			if(!methodName.startsWith("set")) continue;
			String invokeMethodName = "get" + methodName.substring(3, methodName.length());
			Method invokeMethod = model.getClass().getMethod(invokeMethodName, null);
			
			Object invokeResult = invokeMethod.invoke(model, new Object[]{});
			if(invokeResult instanceof Model) {
				m.invoke(vmodel, serialize(serializeHolder.get(invokeResult.getClass()).getName(), (Model)invokeResult));
			} else if(invokeResult instanceof Iterator) {
				Set<VModel> holder = new HashSet<VModel>();
				Iterator<Model> i = (Iterator<Model>)invokeResult;
				while(i.hasNext()) {
					Model sel = i.next();
					holder.add(serialize(serializeHolder.get(sel.getClass()).getName(), sel));
				}
				m.invoke(vmodel, holder);
			} else {
				m.invoke(vmodel, invokeResult);
			}
		}
		return vmodel;
	}
	
	public static Model deserialize(String clazz, VModel vmodel) throws Exception {
		Model model = (Model)Class.forName(clazz).newInstance();
		Method[] methods = model.getClass().getMethods();
		for(Method m : methods) {
			String methodName = m.getName();
			if(!methodName.startsWith("set")) continue;
			String invokeMethodName = "get" + methodName.substring(3, methodName.length());
			Method invokeMethod = vmodel.getClass().getMethod(invokeMethodName, null);
			Object invokeResult = invokeMethod.invoke(vmodel, new Object[]{});
			if(invokeResult instanceof VModel) {
				m.invoke(model, deserialize(deserializeHolder.get(invokeResult.getClass()).getName(), vmodel));
			} else if(invokeResult instanceof Iterator) {
				Set<Model> holder = new HashSet<Model>();
				Iterator<VModel> i = (Iterator<VModel>)invokeResult;
				while(i.hasNext()) {
					VModel sel = i.next();
					holder.add(deserialize(deserializeHolder.get(sel.getClass()).getName(), sel));
				}
				m.invoke(model, holder);
			} else {
				m.invoke(model, invokeResult);
			}
		}
		return model;
	}
}
