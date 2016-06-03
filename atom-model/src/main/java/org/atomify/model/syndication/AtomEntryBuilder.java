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
import java.util.Collection;
import java.util.List;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonBuilder;
import org.atomify.model.common.AtomExtendable;
import org.atomify.model.common.AtomExtendableBuilder;
import org.atomify.model.extension.AtomExtension;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;

public class AtomEntryBuilder extends AtomExtendableBuilder<AtomEntryBuilder> implements Builder<AtomEntry> {
	/**
	 * <b>Required:</b> atom:id element.
	 */
	private AtomId id;
	/**
	 * <b>Required:</b> atom:title element.
	 */
	private AtomText title;
	/**
	 * <b>Required:</b> atom:updated element.
	 */
	private AtomDate updated;
	/**
	 * <b>Optional:</b> atom:published element.
	 */
	private AtomDate published;
	/**
	 * <b>Optional:</b> atom:summary element.
	 */
	private AtomText summary;
	/**
	 * <b>Optional:</b> atom:rights element.
	 */
	private AtomText rights;
	/**
	 * <b>Optional:</b> atom:author elements (required if the feed does not have an author or the
	 * entry document is alone without a source author).
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
	 * <b>Optional:</b> atom:link elements.
	 */
	private List<AtomLink> links;
	/**
	 * <b>Optional/Required depending on other states:</b> atom:content element.
	 */
	private AtomContent content;
	/**
	 * <b>Optional:</b> atom:source element.
	 */
	private AtomSource source;

	private List<AtomExtension> extensions;

	public static AtomEntryBuilder newInstance() {
		return new AtomEntryBuilder();
	}

	public static AtomEntryBuilder newInstance(AtomEntry entry) {
		AtomEntryBuilder builder = new AtomEntryBuilder();
		builder.setXmlBase(entry.getXmlBase());
		builder.setXmlLang(entry.getXmlLang());
		builder.setXmlSpace(entry.getXmlSpace());
		builder.getUndefinedAttributes().putAll(entry.getUndefinedAttributes());
		builder.setId(entry.getId());
		builder.setTitle(entry.getTitle());
		builder.setUpdated(entry.getUpdated());
		builder.setPublished(entry.getPublished());
		builder.setSummary(entry.getSummary());
		builder.setRights(entry.getRights());
		builder.setSource(entry.getSource());
		builder.setContent(entry.getContent());
		builder.addAuthors(entry.getAuthors());
		builder.addContributors(entry.getContributors());
		builder.addCategories(entry.getCategories());
		builder.addLinks(entry.getLinks());
		return builder;
	}

	private AtomEntryBuilder() {
		// disallow instantiation.
	}

	public AtomEntry build() {
		AtomEntry result = new AtomEntry(this.id, this.title, this.updated, this.published);
		result.setAuthors(this.authors);
		result.setCategories(this.categories);
		result.setContributors(this.contributors);
		result.setContent(this.content);
		result.setLinks(this.links);
		result.setRights(this.rights);
		result.setSource(this.source);
		result.setSummary(this.summary);
		attachParentBuilder(result);
		return result;
	}

	@Override
	public void reset() {
		super.reset();
		this.id = null;
		this.title = null;
		this.published = null;
		this.updated = null;
		this.content = null;
		this.rights = null;
		this.source = null;
		this.summary = null;
		if (this.authors != null) {
			this.authors.clear();
		}
		if (this.categories != null) {
			this.categories.clear();
		}
		if (this.contributors != null) {
			this.contributors.clear();
		}
		if (this.links != null) {
			this.links.clear();
		}
		if (this.extensions != null) {
			this.extensions.clear();
		}
	}

	@Element(name = "id", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	public AtomEntryBuilder setId(AtomId id) {
		this.id = id;
		return this;
	}

	@Element(name = "title", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	public AtomEntryBuilder setTitle(AtomText title) {
		this.title = title;
		return this;
	}

	@Element(name = "updated", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	public AtomEntryBuilder setUpdated(AtomDate updated) {
		this.updated = updated;
		return this;
	}

	@Element(name = "published", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = 1)
	public AtomEntryBuilder setPublished(AtomDate published) {
		this.published = published;
		return this;
	}

	@Element(name = "summary", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = 1)
	public AtomEntryBuilder setSummary(AtomText summary) {
		this.summary = summary;
		return this;
	}

	@Element(name = "rights", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = 1)
	public AtomEntryBuilder setRights(AtomText rights) {
		this.rights = rights;
		return this;
	}

	@Element(name = "source", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = 1)
	public AtomEntryBuilder setSource(AtomSource source) {
		this.source = source;
		return this;
	}

	@Element(name = "content", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = 1)
	public AtomEntryBuilder setContent(AtomContent content) {
		this.content = content;
		return this;
	}

	public List<AtomPerson> getAuthors() {
		if (this.authors == null) {
			this.authors = new ArrayList<AtomPerson>();
		}
		return this.authors;
	}

	@Element(name = "author", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomEntryBuilder addAuthor(AtomPerson author) {
		getAuthors().add(AtomContractConstraint.notNull("author", author));
		return this;
	}

	public AtomEntryBuilder addAuthors(Collection<AtomPerson> authors) {
		getAuthors().addAll(AtomContractConstraint.notNull("authors", authors));
		return this;
	}

	public List<AtomCategory> getCategories() {
		if (this.categories == null) {
			this.categories = new ArrayList<AtomCategory>();
		}
		return this.categories;
	}

	@Element(name = "category", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomEntryBuilder addCategory(AtomCategory category) {
		getCategories().add(AtomContractConstraint.notNull("category", category));
		return this;
	}

	public AtomEntryBuilder addCategories(Collection<AtomCategory> categories) {
		getCategories().addAll(AtomContractConstraint.notNull("categories", categories));
		return this;
	}

	public List<AtomPerson> getContributors() {
		if (this.contributors == null) {
			this.contributors = new ArrayList<AtomPerson>();
		}
		return this.contributors;
	}

	@Element(name = "contributor", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomEntryBuilder addContributor(AtomPerson contributor) {
		getContributors().add(AtomContractConstraint.notNull("contributor", contributor));
		return this;
	}

	public AtomEntryBuilder addContributors(Collection<AtomPerson> contributors) {
		getContributors().addAll(AtomContractConstraint.notNull("contributors", contributors));
		return this;
	}

	// FIXME: We need to rethink the whole link thing. Here
	// we actually want something like an AtomLinks object in
	// order to supply method for quick access to the links

	public List<AtomLink> getLinks() {
		if (this.links == null) {
			this.links = new ArrayList<AtomLink>();
		}
		return this.links;
	}

	@Element(name = "link", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomEntryBuilder addLink(AtomLink link) {
		getLinks().add(AtomContractConstraint.notNull("link", link));
		return this;
	}

	public AtomEntryBuilder addLinks(Collection<AtomLink> links) {
		getLinks().addAll(AtomContractConstraint.notNull("links", links));
		return this;
	}

}
