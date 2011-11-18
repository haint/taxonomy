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

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author <a href="mailto:haint@exoplatform.com">Nguyen Thanh Hai</a>
 *
 * @datOct 4, 2011
 */
public class NaturalObject implements IModel
{

   private static final long serialVersionUID = 5736627780873122909L;
   
   private int id;
   
   private Kingdom king;
   
   private Set<Family> families;
   
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

   public int getId()
   {
      return id;
   }
   
   public void setId(int id)
   {
      this.id = id;
   }
   
   public Kingdom getKingdom() 
   {
      return king;
   }
   
   public void setKingdom(Kingdom king)
   {
      this.king = king;
   }
   
   public Iterator<Family> getFamilyIterator()
   {
      if(families == null) families = new HashSet<Family>();
      return families.iterator();
   }
   
   public void addFamily(Family family)
   {
      if(families == null) families = new HashSet<Family>();
      families.add(family);
   }
   
   public Genus getGenus()
   {
      return genus;
   }
   
   public void setGenus(Genus genus)
   {
      this.genus = genus;
   }
   
   public Species getSpecies()
   {
      return species;
   }
   
   public void setSpecies(Species sp)
   {
      this.species = sp;
   }
   
   public Iterator<Index> getIndeciesIterator()
   {
      if(indecies == null) indecies = new HashSet<Index>();
      return indecies.iterator();
   }
   
   public void addIndex(Index index) 
   {
      if(indecies == null) indecies = new HashSet<Index>();
      indecies.add(index);
   }
   
   public Iterator<Tag> getTagIterator()
   {
      if(tags == null) tags = new HashSet<Tag>();
      return tags.iterator();
   }
   
   public void addTag(Tag tag)
   {
      if(tags == null) tags = new HashSet<Tag>();
      tags.add(tag);
   }
   
   public void addEnName(String name)
   {
      if(enNames == null) enNames = new HashSet<String>();
      enNames.add(name);
   }
   
   public Iterator<String> getEnNameIterator()
   {
      if(enNames == null) enNames = new HashSet<String>();
      return enNames.iterator();
   }
   
   public void addVnName(String name)
   {
      if(vnNames == null) vnNames = new HashSet<String>();
      vnNames.add(name);
   }
   
   public Iterator<String> getVnNameIterator()
   {
      if(vnNames == null) vnNames = new HashSet<String>();
      return vnNames.iterator();
   }

   public Date getCreateDate()
   {
      return createDate;
   }

   public void setCreateDate(Date createDate)
   {
      this.createDate = createDate;
   }

   public Date getModifyDate()
   {
      return modifyDate;
   }

   public void setModifyDate(Date modifyDate)
   {
      this.modifyDate = modifyDate;
   }
   
   public String getReferences()
   {
      return references;
   }
   
   public void setReferences(String references)
   {
      this.references = references;
   }

   public String getDesc()
   {
      return desc;
   }

   public void setDesc(String desc)
   {
      this.desc = desc;
   }
   
   public String getAvatar() 
   {
      return avatar;
   }
   
   public void setAvatar(String path)
   {
      this.avatar = path;
   }
}
