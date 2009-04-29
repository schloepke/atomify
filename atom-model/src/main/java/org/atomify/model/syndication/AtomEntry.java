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

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.AtomDocument;
import org.atomify.model.AtomMediaType;

/**
 * Represents an atom entry element.
 * 
 * @author Stephan Schloepke
 */
public class AtomEntry extends AtomCommonAttributes implements AtomDocument {
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
	/**
	 * <b>Optional:</b> optional atom extensions.
	 */
	private List<AtomExtension> extensions;

	public AtomMediaType getMediaType() {
		return AtomConstants.ATOM_ENTRY_MEDIA_TYPE;
	}

	/**
	 * Creates an atom entry for the given id, title and the updated date.
	 * 
	 * @param id
	 *            The id of the entry (must not be null).
	 * @param title
	 *            The title of the entry (must not be null).
	 * @param updated
	 *            The updated date (must not be null).
	 */
	public AtomEntry(final AtomId id, final AtomText title,
			final AtomDate updated) {
		setId(id);
		setTitle(title);
		setUpdated(updated);
	}

	/**
	 * Returns the ID of the atom entry.
	 * 
	 * @return The ID of the entry.
	 */
	public AtomId getId() {
		return this.id;
	}

	/**
	 * Set the ID of the atom entry.
	 * 
	 * @param id
	 *            The ID of the entry (must not be null).
	 */
	public void setId(final AtomId id) {
		this.id = AtomContractConstraint.notNull("id", id);
	}

	/**
	 * Returns the title of this entry.
	 * 
	 * @return The title of the entry.
	 */
	public AtomText getTitle() {
		return this.title;
	}

	/**
	 * Set the title of the entry (must not be null).
	 * 
	 * @param title
	 *            The title to set (must not be null).
	 */
	public void setTitle(final AtomText title) {
		this.title = AtomContractConstraint.notNull("title", title);
	}

	/**
	 * Returns the updated date of the entry.
	 * 
	 * @return The updated date of the entry.
	 */
	public AtomDate getUpdated() {
		return this.updated;
	}

	/**
	 * Sets the updated date of the entry (must not be null).
	 * 
	 * @param updated
	 *            The updated date of the entry (must not be null).
	 */
	public void setUpdated(final AtomDate updated) {
		this.updated = AtomContractConstraint.notNull("updated", updated);
	}

	/**
	 * Returns the published date.
	 * 
	 * @return The published date.
	 */
	public AtomDate getPublished() {
		return this.published;
	}

	/**
	 * Sets the published date.
	 * 
	 * @param published
	 *            The published date to set
	 */
	public void setPublished(final AtomDate published) {
		this.published = published;
	}

	/**
	 * Returns the summary of the entry (in some cases this is required).
	 * 
	 * @return The summary of the entry.
	 */
	public AtomText getSummary() {
		return this.summary;
	}

	/**
	 * Sets the summary of the entry.
	 * 
	 * @param summary
	 *            The summary of the entry.
	 */
	public void setSummary(final AtomText summary) {
		this.summary = summary;
	}

	/**
	 * Returns the rights of this entry.
	 * 
	 * @return The rights of this entry.
	 */
	public AtomText getRights() {
		return this.rights;
	}

	/**
	 * Set the rights of this entry.
	 * 
	 * @param rights
	 *            The rights of this entry.
	 */
	public void setRights(final AtomText rights) {
		this.rights = rights;
	}

	/**
	 * Returns the lazy initialized list of authors.
	 * 
	 * @return The lazy initialized list of authors.
	 */
	public List<AtomPerson> getAuthors() {
		if (this.authors == null) {
			this.authors = new ArrayList<AtomPerson>();
		}
		return this.authors;
	}

	/**
	 * Returns the lazy initialized categories.
	 * 
	 * @return The lazy initialized categories.
	 */
	public List<AtomCategory> getCategories() {
		if (this.categories == null) {
			this.categories = new ArrayList<AtomCategory>();
		}
		return this.categories;
	}

	/**
	 * Returns the lazy initialized contributors.
	 * 
	 * @return The lazy initialized contributors.
	 */
	public List<AtomPerson> getContributors() {
		if (this.contributors == null) {
			this.contributors = new ArrayList<AtomPerson>();
		}
		return this.contributors;
	}

	/**
	 * Returns the lazy initialized list of links.
	 * 
	 * @return The lazy initialized list of links.
	 */
	public List<AtomLink> getLinks() {
		if (this.links == null) {
			this.links = new ArrayList<AtomLink>();
		}
		return this.links;
	}

	/**
	 * Returns the content of the entry.
	 * 
	 * @return The content of the entry.
	 */
	public AtomContent getContent() {
		return this.content;
	}

	/**
	 * Sets the content of the entry.
	 * 
	 * @param content
	 *            The content of the entry.
	 */
	public void setContent(final AtomContent content) {
		this.content = content;
	}

	/**
	 * Returns the source of the entry.
	 * 
	 * @return The source of the entry.
	 */
	public AtomSource getSource() {
		return this.source;
	}

	/**
	 * Sets the source of the atom entry.
	 * 
	 * @param source
	 *            The source of the entry.
	 */
	public void setSource(final AtomSource source) {
		this.source = source;
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

	/**
	 * Returns a new builder to build an entry.
	 * 
	 * @return The entry builder to build the entry.
	 */
	public static AtomEntryBuilder newBuilder() {
		return AtomEntryBuilder.newInstance();
	}

}
