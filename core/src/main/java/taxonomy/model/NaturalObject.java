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
package taxonomy.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import taxonomy.annotation.ManyToOne;
import taxonomy.annotation.OneToMany;
import taxonomy.annotation.OneToOne;
import taxonomy.annotation.Table;

/**
 * @author <a href="mailto:haint@exoplatform.com">Nguyen Thanh Hai</a>
 * 
 * @datOct 4, 2011
 */
@XmlRootElement
@Table("[NaturalObject]")
public class NaturalObject extends Model<NaturalObject> {

	private static final long serialVersionUID = 5736627780873122909L;
	
	public final static SimpleDateFormat DATE_FORMATER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private Kingdom king;

	private Set<Family<?>> families;

	private Genus genus;

	private Species species;

	private Set<Index> indecies;

	private Set<Tag> tags;

	private Set<String> enNames;

	private Set<String> vnNames;

	private Date createDate;

	private Date modifyDate;

	private String references;

	private String desc;

	private String avatar;

	@OneToOne("KINGDOM_ID")
	public Kingdom getKingdom() {
		return king;
	}

	@OneToOne("KINGDOM_ID")
	public void setKingdom(Kingdom king) {
		this.king = king;
	}

	@ManyToOne(field = "FAMILY_IDS", model = Family.class)
	public Iterator<Family<?>> getFamilies() {
		if (families == null) families = new HashSet<Family<?>>();
		return families.iterator();
	}
	
	@OneToMany(field = "FAMILY_IDS", model = Family.class)
	public void setFamilies(Set<Family<?>> families) {
		this.families = families;
	}

	public void addFamily(Family<?> family) {
		if (families == null)
			families = new HashSet<Family<?>>();
		families.add(family);
	}

	@OneToOne("GENUS_ID")
	public Genus getGenus() {
		return genus;
	}

	@OneToOne("GENUS_ID")
	public void setGenus(Genus genus) {
		this.genus = genus;
	}

	@OneToOne("SPECIES_ID")
	public Species getSpecies() {
		return species;
	}

	@OneToOne("SPECIES_ID")
	public void setSpecies(Species sp) {
		this.species = sp;
	}

	@OneToMany(field = "INDEX_IDS", model = Index.class)
	public Iterator<Index> getIndecies() {
		if (indecies == null)
			return null;
		return indecies.iterator();
	}

	@ManyToOne(field = "INDEX_IDS", model = Index.class)
	public void setIndecies(Set<Index> indecies) {
		this.indecies = indecies;
	}

	public void addIndex(Index index) {
		if (indecies == null)
			indecies = new HashSet<Index>();
		indecies.add(index);
	}

	@OneToMany(field = "TAG_IDS", model = Tag.class)
	public Iterator<Tag> getTags() {
		if (tags == null)
			return null;
		return tags.iterator();
	}

	@ManyToOne(field = "TAG_IDS", model = Tag.class)
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public void addTag(Tag tag) {
		if (tags == null)
			tags = new HashSet<Tag>();
		tags.add(tag);
	}

	public Iterator<String> getEnNameIterator() {
		if (enNames == null)
			enNames = new HashSet<String>();
		return enNames.iterator();
	}

	@OneToOne("EN_NAMES")
	public void setEnNames(String s) {
		if (enNames == null)
			enNames = new HashSet<String>();
		String[] arr = s.split("::");
		Collections.addAll(enNames, arr);
	}

	@OneToOne("EN_NAMES")
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

	public Iterator<String> getVnNameIterator() {
		if (vnNames == null)
			vnNames = new HashSet<String>();
		return vnNames.iterator();
	}

	@OneToOne("VN_NAMES")
	public void setVnNames(String s) {
		if (vnNames == null)
			vnNames = new HashSet<String>();
		String[] arr = s.split("::");
		Collections.addAll(vnNames, arr);
	}

	@OneToOne("VN_NAMES")
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

	@OneToOne("CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	@OneToOne("CREATE_DATE")
	public void setCreateDate(String createDate) throws ParseException {
		if(createDate == null) return;
		this.createDate = DATE_FORMATER.parse(createDate);
	}

	@OneToOne("MODIFIY_DATE")
	public Date getModifyDate() {
		return modifyDate;
	}

	@OneToOne("MODIFIY_DATE")
	public void setModifyDate(String modifyDate) throws ParseException {
		if(modifyDate == null) return;
		this.modifyDate = DATE_FORMATER.parse(modifyDate);
	}

	@OneToOne("REFERENCE")
	public String getReferences() {
		return references;
	}

	@OneToOne("REFERENCE")
	public void setReferences(String references) {
		this.references = references;
	}

	@OneToOne("DES")
	public String getDesc() {
		return desc;
	}

	@OneToOne("DES")
	public void setDesc(String desc) {
		this.desc = desc;
	}

	@OneToOne("AVATAR")
	public String getAvatar() {
		return avatar;
	}

	@OneToOne("AVATAR")
	public void setAvatar(String path) {
		this.avatar = path;
	}
}
