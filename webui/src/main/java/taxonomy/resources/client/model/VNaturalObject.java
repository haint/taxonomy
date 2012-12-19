/**
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package taxonomy.resources.client.model;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

/**
 * @author <a href="mailto:haint@exoplatform.com">Nguyen Thanh Hai</a>
 * 
 * @datOct 4, 2011
 */

public class VNaturalObject extends VModel {

	private static final long serialVersionUID = 1L;

	private VKingdom king;

	private Set<VFamily> families;

	private VGenus genus;

	private VSpecies species;

	private Set<VIndex> indecies;

	private Set<VTag> tags;

	private Set<String> enNames;

	private Set<String> vnNames;

	private Date createDate;

	private Date modifyDate;

	private String references;

	private String desc;

	private String avatar;

	public VKingdom getKingdom() {
		return king;
	}

	public void setKingdom(VKingdom king) {
		this.king = king;
	}

	public Set<VFamily> getFamilies() {
		return families;
	}
	
	public void setFamilies(Set<VFamily> families) {
		this.families = families;
	}

	public void addFamil(VFamily family) {
		if (families == null) families = new HashSet<VFamily>();
		families.add(family);
	}

	public VGenus getGenus() {
		return genus;
	}

	public void setGenus(VGenus genus) {
		this.genus = genus;
	}

	public VSpecies getSpecies() {
		return species;
	}

	public void setSpecies(VSpecies sp) {
		this.species = sp;
	}

	public Set<VIndex> getIndecies() {
		return indecies;
	}

	public void setIndecies(Set<VIndex> indecies) {
		this.indecies = indecies;
	}

	public void addIndex(VIndex index) {
		if (indecies == null) indecies = new HashSet<VIndex>();
		indecies.add(index);
	}

	public Set<VTag> getTags() {
		return tags;
	}

	public void setTags(Set<VTag> tags) {
		this.tags = tags;
	}

	public void addTag(VTag tag) {
		if (tags == null) tags = new HashSet<VTag>();
		tags.add(tag);
	}

	public Set<String> getEnNameSet() {
		return enNames;
	}

	public void setEnNames(String s) {
		if (enNames == null) enNames = new HashSet<String>();
		String[] arr = s.split("::");
		Collections.addAll(enNames, arr);
	}

	public String getEnNames() {
		if (enNames == null)
			return null;
		StringBuilder b = new StringBuilder();
		Iterator<String> i = enNames.iterator();
		while (i.hasNext()) {
			b.append(i.next()).append("::");
		}
		return b.toString().substring(0, b.toString().length() - 2);
	}

	public void addEnName(String name) {
		if (enNames == null)
			enNames = new HashSet<String>();
		enNames.add(name);
	}

	public Set<String> getVnNameSet() {
		return vnNames;
	}

	public void setVnNames(String s) {
		if (vnNames == null)
			vnNames = new HashSet<String>();
		String[] arr = s.split("::");
		Collections.addAll(vnNames, arr);
	}

	public String getVnNames() {
		if (vnNames == null)
			return null;
		StringBuilder b = new StringBuilder();
		Iterator<String> i = vnNames.iterator();
		while (i.hasNext()) {
			b.append(i.next()).append("::");
		}
		return b.toString().substring(0, b.toString().length() - 2);
	}

	public void addVnName(String name) {
		if (vnNames == null)
			vnNames = new HashSet<String>();
		vnNames.add(name);
	}

	public String getCreateDate() {
		return createDate != null ? DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(createDate) : null;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate != null ? DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(modifyDate) : null;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String path) {
		this.avatar = path;
	}
}
