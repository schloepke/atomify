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
import java.util.List;

import org.atomify.model.AtomCommonBuilder;
import org.atomify.model.AtomConstants;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;

public class AtomFeedBuilder extends AtomCommonBuilder<AtomFeedBuilder> implements Builder<AtomFeed> {
	/**
	 * <b>Optional:</b> atom:id element.
	 */
	private AtomId id;
	/**
	 * <b>Optional:</b> atom:title element.
	 */
	private AtomText title;
	/**
	 * <b>Optional:</b> atom:subtitle element.
	 */
	private AtomText subtitle;
	/**
	 * <b>Optional:</b> atom:updated element.
	 */
	private AtomDate updated;
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

	public static AtomFeedBuilder newInstance() {
		return new AtomFeedBuilder();
	}

	private AtomFeedBuilder() {
		// disallow instantiation
	}

	public AtomFeed build() {
		AtomFeed temp = new AtomFeed(this.id, this.title, this.updated);
		temp.setCategories(this.categories);
		temp.setAuthors(this.authors);
		temp.setContributors(this.contributors);
		attachCommonAttributes(temp);
		return temp;
	}

	@Override
	public void reset() {
		super.reset();
	}

	@Element(name = "id", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	public AtomFeedBuilder setId(AtomId id) {
		this.id = id;
		return this;
	}

	@Element(name = "title", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	public AtomFeedBuilder setTitle(AtomText title) {
		this.title = title;
		return this;
	}

	@Element(name = "updated", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	public AtomFeedBuilder setUpdated(AtomDate updated) {
		this.updated = updated;
		return this;
	}

	@Element(name = "category", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomFeedBuilder addCategory(AtomCategory category) {
		if (this.categories == null) {
			this.categories = new ArrayList<AtomCategory>();
		}
		this.categories.add(category);
		return this;
	}

	@Element(name = "author", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomFeedBuilder addAuthor(AtomPerson author) {
		if (this.authors == null) {
			this.authors = new ArrayList<AtomPerson>();
		}
		this.authors.add(author);
		return this;
	}

	@Element(name = "contributor", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomFeedBuilder addContributor(AtomPerson contributor) {
		if (this.contributors == null) {
			this.contributors = new ArrayList<AtomPerson>();
		}
		this.contributors.add(contributor);
		return this;
	}

}
