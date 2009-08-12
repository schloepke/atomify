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
import java.util.Collections;
import java.util.List;

import org.atomify.model.AtomCommonAttributes;
import org.atomify.model.AtomContractConstraint;

/**
 * Atom source is an element holding all information a feed holds but the entries.
 * <p>
 * The feed and source element in atom are in most cases equal. The difference is only in the
 * requirement of certain elements to be present and that the source has no entries compared to the
 * feed. Since the source would be embedded into an entry given the information of the feed it comes
 * from it is sufficient to think that a feed is a source as well. Thats why this implementation
 * derived the feed from the source element.
 * </p>
 * 
 * @author stephan
 */
public class AtomSource extends AtomCommonAttributes {
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
	public AtomSource(final AtomId id, final AtomText title, final AtomDate updated) {
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
	 * Copy constructor usable for the case that a feed needs to be transported to a source only
	 * element in order to embed it into an entry.
	 * 
	 * @param source The source to copy the informations from.
	 * @todo TODO: This is currently working however we need to clone all elements or we have the
	 *       main problem that
	 */
	public AtomSource(final AtomSource source) {
		AtomContractConstraint.notNull("source", source);
		this.id = source.id;
		this.title = source.title;
		this.subtitle = source.subtitle;
		this.updated = source.updated;
		this.authors = source.authors;
		this.categories = source.categories;
		this.contributors = source.contributors;
		this.generator = source.generator;
		this.icon = source.icon;
		this.links = source.links;
		this.logo = source.logo;
		this.rights = source.rights;
		this.extensions = source.extensions;
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
		if (this.links == null) {
			this.links = new ArrayList<AtomLink>();
		}
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
		if (this.extensions == null) {
			this.extensions = new ArrayList<AtomExtension>();
		}
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

}
