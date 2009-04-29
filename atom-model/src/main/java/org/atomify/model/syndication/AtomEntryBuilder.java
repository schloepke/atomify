/**
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
import java.util.List;

import org.jbasics.pattern.builder.Builder;

public class AtomEntryBuilder extends AtomCommonBuilder<AtomEntryBuilder>
		implements Builder<AtomEntry> {
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
	 * <b>Optional:</b> atom:author elements (required if the feed does not have
	 * an author or the entry document is alone without a source author).
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

	public static AtomEntryBuilder newInstance() {
		return new AtomEntryBuilder();
	}

	public AtomEntry build() {
		AtomEntry result = new AtomEntry(this.id, this.title, this.updated);
		attachCommonAttributes(result);
		result.setContent(this.content);
		if (this.published != null) {
			result.setPublished(this.published);
		}
		if (this.summary != null) {
			result.setSummary(this.summary);
		}
		if (this.rights != null) {
			result.setRights(this.rights);
		}
		if (this.source != null) {
			// TODO: We need to deeply clone the source in order to make sure
			// the source can be modified in the entry
			// also important is to make sure the xml:base resolving works in
			// the new entry
			result.setSource(this.source);
		}
		if (this.authors != null && this.authors.size() > 0) {
			result.getAuthors().addAll(this.authors);
		}
		if (this.categories != null && this.categories.size() > 0) {
			result.getCategories().addAll(this.categories);
		}
		if (this.contributors != null && this.contributors.size() > 0) {
			result.getContributors().addAll(this.contributors);
		}
		if (this.links != null && this.links.size() > 0) {
			result.getLinks().addAll(this.links);
		}
		return result;
	}

	public AtomEntryBuilder setId(AtomId id) {
		this.id = id;
		return this;
	}

	public AtomEntryBuilder setTitle(AtomText title) {
		this.title = title;
		return this;
	}

	public AtomEntryBuilder setUpdated(AtomDate updated) {
		this.updated = updated;
		return this;
	}

	public AtomEntryBuilder setPublished(AtomDate published) {
		this.published = published;
		return this;
	}

	public AtomEntryBuilder setSummary(AtomText summary) {
		this.summary = summary;
		return this;
	}

	public AtomEntryBuilder setRights(AtomText rights) {
		this.rights = rights;
		return this;
	}

	public AtomEntryBuilder setSource(AtomSource source) {
		this.source = source;
		return this;
	}

	public AtomEntryBuilder setContent(AtomContent content) {
		this.content = content;
		return this;
	}

	public AtomEntryBuilder addAuthor(AtomPerson author) {
		if (this.authors == null) {
			this.authors = new ArrayList<AtomPerson>();
		}
		this.authors.add(author);
		return this;
	}

	public AtomEntryBuilder addCategory(AtomCategory category) {
		if (this.categories == null) {
			this.categories = new ArrayList<AtomCategory>();
		}
		this.categories.add(category);
		return this;
	}

	public AtomEntryBuilder addContributor(AtomPerson contributor) {
		if (this.contributors == null) {
			this.contributors = new ArrayList<AtomPerson>();
		}
		this.contributors.add(contributor);
		return this;
	}

	public AtomEntryBuilder addLink(AtomLink link) {
		if (this.links == null) {
			this.links = new ArrayList<AtomLink>();
		}
		this.links.add(link);
		return this;
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
	}

	private AtomEntryBuilder() {
		// To avoid instantiation.
	}

}
