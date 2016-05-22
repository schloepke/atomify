/*
 * Copyright (c) 2009-2016 Stephan Schloepke
 *
 * Stephan Schloepke: http://www.schloepke.de/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.atomify.model.syndication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.atomify.model.AtomConstants;
import org.atomify.model.common.AtomCommonAttributes;
import org.atomify.model.extension.AtomExtension;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class AbstractAtomSource extends AtomCommonAttributes {
	/**
	 * <b>Optional:</b> atom:id element.
	 */
	private final AtomId id;
	/**
	 * <b>Optional:</b> atom:title element.
	 */
	private final AtomText title;
	/**
	 * <b>Optional:</b> atom:subtitle element.
	 */
	private AtomText subtitle;
	/**
	 * <b>Optional:</b> atom:updated element.
	 */
	private final AtomDate updated;
	/**
	 * <b>Optional:</b> atom:author elements.
	 */
	private List<AtomPerson> authors;
	/**
	 * <b>Optional:</b> atom:category elements.
	 */
	private List<AtomCategory> categories;
	/**
	 * <b>Optional:</b> atom:contributor elements.
	 */
	private List<AtomPerson> contributors;
	/**
	 * <b>Optional:</b> atom:generator element.
	 */
	private AtomGenerator generator;
	/**
	 * <b>Optional:</b> atom:icon element.
	 */
	private AtomIcon icon;
	/**
	 * <b>Optional:</b> atom:link elements.
	 */
	private List<AtomLink> links;
	/**
	 * <b>Optional:</b> atom:logo element.
	 */
	private AtomLogo logo;
	/**
	 * <b>Optional:</b> atom:rights element.
	 */
	private AtomText rights;
	/**
	 * <b>Optional:</b> atom extensions.
	 */
	private List<AtomExtension> extensions;

	/**
	 * Creates an empty source element.
	 */
	public AbstractAtomSource(final AtomId id, final AtomText title, final AtomDate updated) {
		this.id = id;
		this.title = title;
		this.updated = updated;
		this.categories = Collections.emptyList();
		this.authors = Collections.emptyList();
		this.contributors = Collections.emptyList();
		this.links = Collections.emptyList();
		this.extensions = Collections.emptyList();
	}

	/**
	 * Returns the id.
	 * 
	 * @return The id.
	 */
	public AtomId getId() {
		return this.id;
	}

	/**
	 * Returns the title.
	 * 
	 * @return The title.
	 */
	public AtomText getTitle() {
		return this.title;
	}

	/**
	 * Returns the sub title.
	 * 
	 * @return The sub title.
	 */
	public AtomText getSubtitle() {
		return this.subtitle;
	}

	/**
	 * Returns the update date.
	 * 
	 * @return The update date.
	 */
	public AtomDate getUpdated() {
		return this.updated;
	}

	/**
	 * Returns the lazy initialized collection of authors.
	 * 
	 * @return The collection of authors.
	 */
	public List<AtomPerson> getAuthors() {
		return this.authors;
	}

	/**
	 * Returns the lazy initialized categories.
	 * 
	 * @return the categories
	 */
	public List<AtomCategory> getCategories() {
		return this.categories;
	}

	/**
	 * Returns the lazy initialized contributors.
	 * 
	 * @return the contributors
	 */
	public List<AtomPerson> getContributors() {
		return this.contributors;
	}

	/**
	 * Returns the generator.
	 * 
	 * @return the generator
	 */
	public AtomGenerator getGenerator() {
		return this.generator;
	}

	/**
	 * Returns the icon.
	 * 
	 * @return the icon
	 */
	public AtomIcon getIcon() {
		return this.icon;
	}

	/**
	 * Returns the lazy initialized collection of links.
	 * 
	 * @return the links
	 */
	public List<AtomLink> getLinks() {
		return this.links;
	}

	/**
	 * Returns the logo.
	 * 
	 * @return the logo
	 */
	public AtomLogo getLogo() {
		return this.logo;
	}

	/**
	 * Returns the rights.
	 * 
	 * @return the rights
	 */
	public AtomText getRights() {
		return this.rights;
	}

	/**
	 * Returns the lazy initialized collection of extensions.
	 * 
	 * @return the extensions
	 */
	public List<AtomExtension> getExtensions() {
		return this.extensions;
	}

	// Protected interface area for the builder (we need to figure out a way to do this better)

	protected void setCategories(List<AtomCategory> categories) {
		if (categories == null || categories.isEmpty()) {
			this.categories = Collections.emptyList();
		} else {
			this.categories = Collections.unmodifiableList(new ArrayList<AtomCategory>(categories));
		}
	}

	protected void setAuthors(List<AtomPerson> authors) {
		if (authors == null || authors.isEmpty()) {
			this.authors = Collections.emptyList();
		} else {
			this.authors = Collections.unmodifiableList(new ArrayList<AtomPerson>(authors));
		}
	}

	protected void setContributors(List<AtomPerson> contributors) {
		if (contributors == null || contributors.isEmpty()) {
			this.contributors = Collections.emptyList();
		} else {
			this.contributors = Collections.unmodifiableList(new ArrayList<AtomPerson>(contributors));
		}
	}

	protected void setLinks(List<AtomLink> links) {
		if (links == null || links.isEmpty()) {
			this.links = Collections.emptyList();
		} else {
			this.links = Collections.unmodifiableList(new ArrayList<AtomLink>(links));
		}
	}

	protected void setExtensions(List<AtomExtension> extensions) {
		if (extensions == null || extensions.isEmpty()) {
			this.extensions = Collections.emptyList();
		} else {
			this.extensions = Collections.unmodifiableList(new ArrayList<AtomExtension>(extensions));
		}
	}

	protected void setSubtitle(final AtomText subtitle) {
		this.subtitle = subtitle;
	}

	protected void setRights(final AtomText rights) {
		this.rights = rights;
	}

	protected void setGenerator(final AtomGenerator generator) {
		this.generator = generator;
	}

	protected void setIcon(final AtomIcon icon) {
		this.icon = icon;
	}

	protected void setLogo(final AtomLogo logo) {
		this.logo = logo;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.authors == null) ? 0 : this.authors.hashCode());
		result = prime * result + ((this.categories == null) ? 0 : this.categories.hashCode());
		result = prime * result + ((this.contributors == null) ? 0 : this.contributors.hashCode());
		result = prime * result + ((this.extensions == null) ? 0 : this.extensions.hashCode());
		result = prime * result + ((this.generator == null) ? 0 : this.generator.hashCode());
		result = prime * result + ((this.icon == null) ? 0 : this.icon.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.links == null) ? 0 : this.links.hashCode());
		result = prime * result + ((this.logo == null) ? 0 : this.logo.hashCode());
		result = prime * result + ((this.rights == null) ? 0 : this.rights.hashCode());
		result = prime * result + ((this.subtitle == null) ? 0 : this.subtitle.hashCode());
		result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
		result = prime * result + ((this.updated == null) ? 0 : this.updated.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof AbstractAtomSource)) {
			return false;
		}
		AbstractAtomSource other = (AbstractAtomSource) obj;
		if (this.authors == null) {
			if (other.authors != null) {
				return false;
			}
		} else if (!this.authors.equals(other.authors)) {
			return false;
		}
		if (this.categories == null) {
			if (other.categories != null) {
				return false;
			}
		} else if (!this.categories.equals(other.categories)) {
			return false;
		}
		if (this.contributors == null) {
			if (other.contributors != null) {
				return false;
			}
		} else if (!this.contributors.equals(other.contributors)) {
			return false;
		}
		if (this.extensions == null) {
			if (other.extensions != null) {
				return false;
			}
		} else if (!this.extensions.equals(other.extensions)) {
			return false;
		}
		if (this.generator == null) {
			if (other.generator != null) {
				return false;
			}
		} else if (!this.generator.equals(other.generator)) {
			return false;
		}
		if (this.icon == null) {
			if (other.icon != null) {
				return false;
			}
		} else if (!this.icon.equals(other.icon)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.links == null) {
			if (other.links != null) {
				return false;
			}
		} else if (!this.links.equals(other.links)) {
			return false;
		}
		if (this.logo == null) {
			if (other.logo != null) {
				return false;
			}
		} else if (!this.logo.equals(other.logo)) {
			return false;
		}
		if (this.rights == null) {
			if (other.rights != null) {
				return false;
			}
		} else if (!this.rights.equals(other.rights)) {
			return false;
		}
		if (this.subtitle == null) {
			if (other.subtitle != null) {
				return false;
			}
		} else if (!this.subtitle.equals(other.subtitle)) {
			return false;
		}
		if (this.title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!this.title.equals(other.title)) {
			return false;
		}
		if (this.updated == null) {
			if (other.updated != null) {
				return false;
			}
		} else if (!this.updated.equals(other.updated)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder().append("authors=").append(this.authors).append(", categories=").append(this.categories).append(", contributors=")
				.append(this.contributors).append(", extensions=").append(this.extensions).append(", generator=").append(this.generator).append(
						", icon=").append(this.icon).append(", id=").append(this.id).append(", links=").append(this.links).append(", logo=").append(
						this.logo).append(", rights=").append(this.rights).append(", subtitle=").append(this.subtitle).append(", title=").append(
						this.title).append(", updated=").append(this.updated).append(", ").append(super.toString()).toString();
	}

	// --- FIXME: From here all is serialization. We Still need to think about a good way to do so.

	protected void serializeContent(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		if (this.id != null) {
			this.id.serialize(handler, attributes);
		}
		if (this.title != null) {
			this.title.serialize(TITLE_QNAME, handler, attributes);
		}
		if (this.subtitle != null) {
			this.subtitle.serialize(SUBTITLE_QNAME, handler, attributes);
		}
		if (this.updated != null) {
			this.updated.serialize(UPDATED_QNAME, handler, attributes);
		}
		for (AtomLink link : this.links) {
			link.serialize(handler, attributes);
		}
		for (AtomPerson author : this.authors) {
			author.serialize(AUTHOR_QNAME, handler, attributes);
		}
		for (AtomPerson contributor : this.contributors) {
			contributor.serialize(CONTRIBUTOR_QNAME, handler, attributes);
		}
		for (AtomCategory category : this.categories) {
			category.serialize(handler, attributes);
		}
		if (this.generator != null) {
			this.generator.serialize(handler, attributes);
		}
		if (this.logo != null) {
			this.logo.serialize(handler, attributes);
		}
		if (this.icon != null) {
			this.icon.serialize(handler, attributes);
		}
		if (this.rights != null) {
			this.rights.serialize(RIGHTS_QNAME, handler, attributes);
		}
		for (AtomExtension extension : this.extensions) {
			extension.serialize(handler, attributes);
		}
	}

	private static final QName TITLE_QNAME = new QName(AtomConstants.ATOM_NS_URI, "title", AtomConstants.ATOM_NS_PREFIX);
	private static final QName SUBTITLE_QNAME = new QName(AtomConstants.ATOM_NS_URI, "subtitle", AtomConstants.ATOM_NS_PREFIX);
	private static final QName AUTHOR_QNAME = new QName(AtomConstants.ATOM_NS_URI, "author", AtomConstants.ATOM_NS_PREFIX);
	private static final QName CONTRIBUTOR_QNAME = new QName(AtomConstants.ATOM_NS_URI, "contributor", AtomConstants.ATOM_NS_PREFIX);
	private static final QName UPDATED_QNAME = new QName(AtomConstants.ATOM_NS_URI, "updated", AtomConstants.ATOM_NS_PREFIX);
	private static final QName RIGHTS_QNAME = new QName(AtomConstants.ATOM_NS_URI, "rights", AtomConstants.ATOM_NS_PREFIX);

}
