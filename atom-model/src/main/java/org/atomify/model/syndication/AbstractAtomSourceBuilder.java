/*
 * Copyright (c) 2009 Stephan Schloepke and innoQ Deutschland GmbH
 *
 * Stephan Schloepke: http://www.schloepke.de/
 * innoQ Deutschland GmbH: http://www.innoq.com/
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
import java.util.Collection;
import java.util.List;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonBuilder;
import org.atomify.model.extension.AtomExtension;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Element;

public abstract class AbstractAtomSourceBuilder<T extends AbstractAtomSourceBuilder<?>> extends AtomCommonBuilder<T> {
	/**
	 * <b>Optional:</b> atom:id element.
	 */
	protected AtomId id;
	/**
	 * <b>Optional:</b> atom:title element.
	 */
	protected AtomText title;
	/**
	 * <b>Optional:</b> atom:subtitle element.
	 */
	protected AtomText subtitle;
	/**
	 * <b>Optional:</b> atom:updated element.
	 */
	protected AtomDate updated;
	/**
	 * <b>Optional:</b> atom:author elements.
	 */
	protected List<AtomPerson> authors;
	/**
	 * <b>Optional:</b> atom:category elements.
	 */
	protected List<AtomCategory> categories;
	/**
	 * <b>Optional:</b> atom:contributor elements.
	 */
	protected List<AtomPerson> contributors;
	/**
	 * <b>Optional:</b> atom:generator element.
	 */
	protected AtomGenerator generator;
	/**
	 * <b>Optional:</b> atom:icon element.
	 */
	protected AtomIcon icon;
	/**
	 * <b>Optional:</b> atom:link elements.
	 */
	protected List<AtomLink> links;
	/**
	 * <b>Optional:</b> atom:logo element.
	 */
	protected AtomLogo logo;
	/**
	 * <b>Optional:</b> atom:rights element.
	 */
	protected AtomText rights;
	/**
	 * <b>Optional:</b> atom extensions.
	 */
	protected List<AtomExtension> extensions;

	@Override
	public void reset() {
		super.reset();
		this.id = null;
		this.updated = null;
		this.title = null;
		this.subtitle = null;
		this.icon = null;
		this.logo = null;
		this.rights = null;
		this.generator = null;
		if (this.authors != null) {
			this.authors.clear();
		}
		if (this.contributors != null) {
			this.contributors.clear();
		}
		if (this.categories != null) {
			this.categories.clear();
		}
		if (this.extensions != null) {
			this.extensions.clear();
		}
		if (this.links != null) {
			this.links.clear();
		}
	}

	@Element(name = "id", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	@SuppressWarnings("unchecked")
	public T setId(AtomId id) {
		this.id = id;
		return (T) this;
	}

	@Element(name = "title", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	@SuppressWarnings("unchecked")
	public T setTitle(AtomText title) {
		this.title = title;
		return (T) this;
	}

	@Element(name = "updated", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	@SuppressWarnings("unchecked")
	public T setUpdated(AtomDate updated) {
		this.updated = updated;
		return (T) this;
	}

	@Element(name = "subtitle", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = 1)
	@SuppressWarnings("unchecked")
	public T setSubtitle(AtomText subtitle) {
		this.subtitle = subtitle;
		return (T) this;
	}

	@Element(name = "generator", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = 1)
	@SuppressWarnings("unchecked")
	public T setGenerator(AtomGenerator generator) {
		this.generator = generator;
		return (T) this;
	}

	@Element(name = "rights", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = 1)
	@SuppressWarnings("unchecked")
	public T setRights(AtomText rights) {
		this.rights = rights;
		return (T) this;
	}

	@Element(name = "icon", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = 1)
	@SuppressWarnings("unchecked")
	public T setIcon(AtomIcon icon) {
		this.icon = icon;
		return (T) this;
	}

	@Element(name = "logo", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = 1)
	@SuppressWarnings("unchecked")
	public T setLogo(AtomLogo logo) {
		this.logo = logo;
		return (T) this;
	}

	@Element(name = "category", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	@SuppressWarnings("unchecked")
	public T addCategory(AtomCategory category) {
		if (this.categories == null) {
			this.categories = new ArrayList<AtomCategory>();
		}
		this.categories.add(AtomContractConstraint.notNull("category", category));
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T addCategories(Collection<AtomCategory> categories) {
		if (this.categories == null) {
			this.categories = new ArrayList<AtomCategory>();
		}
		this.categories.addAll(AtomContractConstraint.notNull("categories", categories));
		return (T) this;
	}

	@Element(name = "author", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	@SuppressWarnings("unchecked")
	public T addAuthor(AtomPerson author) {
		if (this.authors == null) {
			this.authors = new ArrayList<AtomPerson>();
		}
		this.authors.add(AtomContractConstraint.notNull("author", author));
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T addAuthors(Collection<AtomPerson> authors) {
		if (this.authors == null) {
			this.authors = new ArrayList<AtomPerson>();
		}
		this.authors.addAll(AtomContractConstraint.notNull("authors", authors));
		return (T) this;
	}

	@Element(name = "contributor", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	@SuppressWarnings("unchecked")
	public T addContributor(AtomPerson contributor) {
		if (this.contributors == null) {
			this.contributors = new ArrayList<AtomPerson>();
		}
		this.contributors.add(AtomContractConstraint.notNull("contributor", contributor));
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T addContributors(Collection<AtomPerson> contributors) {
		if (this.contributors == null) {
			this.contributors = new ArrayList<AtomPerson>();
		}
		this.contributors.addAll(AtomContractConstraint.notNull("contributors", contributors));
		return (T) this;
	}

	@Element(name = "link", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	@SuppressWarnings("unchecked")
	public T addLink(AtomLink link) {
		if (this.links == null) {
			this.links = new ArrayList<AtomLink>();
		}
		this.links.add(AtomContractConstraint.notNull("link", link));
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T addLinks(Collection<AtomLink> links) {
		if (this.links == null) {
			this.links = new ArrayList<AtomLink>();
		}
		this.links.addAll(AtomContractConstraint.notNull("links", links));
		return (T) this;
	}

	@AnyElement
	@SuppressWarnings("unchecked")
	public T addExtension(AtomExtension extension) {
		if (this.extensions == null) {
			this.extensions = new ArrayList<AtomExtension>();
		}
		this.extensions.add(AtomContractConstraint.notNull("extension", extension));
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T addExtensions(Collection<AtomExtension> extensions) {
		if (this.extensions == null) {
			this.extensions = new ArrayList<AtomExtension>();
		}
		this.extensions.addAll(AtomContractConstraint.notNull("extensions", extensions));
		return (T) this;
	}

}
