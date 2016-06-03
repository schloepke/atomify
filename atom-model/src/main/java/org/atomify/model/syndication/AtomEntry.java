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

import org.atomify.model.common.AtomExtendable;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import org.jbasics.net.mediatype.MediaType;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.AtomDocument;
import org.atomify.model.common.AtomCommonAttributes;
import org.atomify.model.extension.AtomExtension;

/**
 * Represents an atom entry element.
 * 
 * @author Stephan Schloepke
 */
public class AtomEntry extends AtomExtendable implements AtomDocument {
	/**
	 * The media type as a string constant.
	 */
	public static final String MEDIA_TYPE_STRING = "application/atom+xml;type=entry";
	/**
	 * The media type for an atom syndication entry.
	 */
	public static final MediaType MEDIA_TYPE = MediaType.valueOf(AtomEntry.MEDIA_TYPE_STRING);

	/**
	 * <b>Required:</b> atom:id element.
	 */
	private final AtomId id;
	/**
	 * <b>Required:</b> atom:title element.
	 */
	private final AtomText title;
	/**
	 * <b>Required:</b> atom:updated element.
	 */
	private final AtomDate updated;
	/**
	 * <b>Optional:</b> atom:published element.
	 */
	private final AtomDate published;
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
	private List<AtomPerson> authors = Collections.emptyList();
	/**
	 * <b>Optional:</b> atom:category elements.
	 */
	private List<AtomCategory> categories = Collections.emptyList();
	/**
	 * <b>Optional:</b> atom:contributor elements.
	 */
	private List<AtomPerson> contributors = Collections.emptyList();
	/**
	 * <b>Optional:</b> atom:link elements.
	 */
	private List<AtomLink> links = Collections.emptyList();
	/**
	 * <b>Optional/Required depending on other states:</b> atom:content element.
	 */
	private AtomContent content;
	/**
	 * <b>Optional:</b> atom:source element.
	 */
	private AtomSource source;

	public static AtomEntryBuilder newBuilder() {
		return AtomEntryBuilder.newInstance();
	}

	public MediaType getMediaType() {
		return AtomEntry.MEDIA_TYPE;
	}

	/**
	 * Creates an atom entry for the given id, title and the updated date.
	 * 
	 * @param id The id of the entry (must not be null).
	 * @param title The title of the entry (must not be null).
	 * @param updated The updated date (must not be null).
	 */
	public AtomEntry(final AtomId id, final AtomText title, final AtomDate updated, final AtomDate published) {
		this.id = AtomContractConstraint.notNull("id", id);
		this.title = AtomContractConstraint.notNull("title", title);
		this.updated = AtomContractConstraint.notNull("updated", updated);
		this.published = published;
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
	 * Returns the title of this entry.
	 * 
	 * @return The title of the entry.
	 */
	public AtomText getTitle() {
		return this.title;
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
	 * Returns the published date.
	 * 
	 * @return The published date.
	 */
	public AtomDate getPublished() {
		return this.published;
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
	 * Returns the rights of this entry.
	 * 
	 * @return The rights of this entry.
	 */
	public AtomText getRights() {
		return this.rights;
	}

	/**
	 * Returns the lazy initialized list of authors.
	 * 
	 * @return The lazy initialized list of authors.
	 */
	public List<AtomPerson> getAuthors() {
		return this.authors;
	}

	/**
	 * Returns the lazy initialized categories.
	 * 
	 * @return The lazy initialized categories.
	 */
	public List<AtomCategory> getCategories() {
		return this.categories;
	}

	/**
	 * Returns the lazy initialized contributors.
	 * 
	 * @return The lazy initialized contributors.
	 */
	public List<AtomPerson> getContributors() {
		return this.contributors;
	}

	/**
	 * Returns the lazy initialized list of links.
	 * 
	 * @return The lazy initialized list of links.
	 */
	public List<AtomLink> getLinks() {
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
	 * Returns the source of the entry.
	 * 
	 * @return The source of the entry.
	 */
	public AtomSource getSource() {
		return this.source;
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
		result = prime * result + ((this.content == null) ? 0 : this.content.hashCode());
		result = prime * result + ((this.contributors == null) ? 0 : this.contributors.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.links == null) ? 0 : this.links.hashCode());
		result = prime * result + ((this.published == null) ? 0 : this.published.hashCode());
		result = prime * result + ((this.rights == null) ? 0 : this.rights.hashCode());
		result = prime * result + ((this.source == null) ? 0 : this.source.hashCode());
		result = prime * result + ((this.summary == null) ? 0 : this.summary.hashCode());
		result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
		result = prime * result + ((this.updated == null) ? 0 : this.updated.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof AtomEntry)) {
			return false;
		}
		AtomEntry other = (AtomEntry) obj;
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
		if (this.content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!this.content.equals(other.content)) {
			return false;
		}
		if (this.contributors == null) {
			if (other.contributors != null) {
				return false;
			}
		} else if (!this.contributors.equals(other.contributors)) {
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
		if (this.published == null) {
			if (other.published != null) {
				return false;
			}
		} else if (!this.published.equals(other.published)) {
			return false;
		}
		if (this.rights == null) {
			if (other.rights != null) {
				return false;
			}
		} else if (!this.rights.equals(other.rights)) {
			return false;
		}
		if (this.source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!this.source.equals(other.source)) {
			return false;
		}
		if (this.summary == null) {
			if (other.summary != null) {
				return false;
			}
		} else if (!this.summary.equals(other.summary)) {
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
		String builder = "AtomEntry [authors=" + this.authors + ", categories=" + this.categories + ", content=" +
				this.content + ", contributors=" + this.contributors + ", id=" +
				this.id + ", links=" + this.links + ", published=" + this.published + ", rights=" +
				this.rights + ", source=" + this.source + ", summary=" + this.summary + ", title=" +
				this.title + ", updated=" + this.updated + "]";
		return builder;
	}

	// --- FIXME: From here all is serialization. We Still need to think about a good way to do so.

	@SuppressWarnings("all")
	public void serialize(final ContentHandler handler, AttributesImpl attributes) throws SAXException {
		handler.startPrefixMapping(AtomConstants.ATOM_NS_PREFIX, AtomConstants.ATOM_NS_URI);
		attributes = initCommonAttributes(handler, attributes);
		handler.startElement(AtomConstants.ATOM_NS_URI, "entry", AtomConstants.ATOM_NS_PREFIX + ":entry", attributes);
		this.id.serialize(handler, attributes);
		this.title.serialize(AtomEntry.TITLE_QNAME, handler, attributes);
		this.updated.serialize(AtomEntry.UPDATED_QNAME, handler, attributes);
		if (this.published != null) {
			this.published.serialize(AtomEntry.PUBLISHED_QNAME, handler, attributes);
		}
		for (AtomLink link : this.links) {
			link.serialize(handler, attributes);
		}
		for (AtomPerson author : this.authors) {
			author.serialize(AtomEntry.AUTHOR_QNAME, handler, attributes);
		}
		for (AtomPerson contributor : this.contributors) {
			contributor.serialize(AtomEntry.CONTRIBUTOR_QNAME, handler, attributes);
		}
		for (AtomCategory category : this.categories) {
			category.serialize(handler, attributes);
		}
		if (this.rights != null) {
			this.rights.serialize(AtomEntry.RIGHTS_QNAME, handler, attributes);
		}
		if (this.source != null) {
			this.source.serialize(handler, attributes);
		}
		serializeExtensions(handler, attributes);
		if (this.summary != null) {
			this.summary.serialize(AtomEntry.SUMMARY_QNAME, handler, attributes);
		}
		if (this.content != null) {
			this.content.serialize(handler, attributes);
		} else {
			// TODO: we need to make sure there is an alternate link and a summary available!
		}
		handler.endElement(AtomConstants.ATOM_NS_URI, "entry", AtomConstants.ATOM_NS_PREFIX + ":entry");
		handler.endPrefixMapping(AtomConstants.ATOM_NS_PREFIX);
	}

	private static final QName TITLE_QNAME = new QName(AtomConstants.ATOM_NS_URI, "title", AtomConstants.ATOM_NS_PREFIX);
	private static final QName AUTHOR_QNAME = new QName(AtomConstants.ATOM_NS_URI, "author", AtomConstants.ATOM_NS_PREFIX);
	private static final QName CONTRIBUTOR_QNAME = new QName(AtomConstants.ATOM_NS_URI, "contributor", AtomConstants.ATOM_NS_PREFIX);
	private static final QName SUMMARY_QNAME = new QName(AtomConstants.ATOM_NS_URI, "summary", AtomConstants.ATOM_NS_PREFIX);
	private static final QName UPDATED_QNAME = new QName(AtomConstants.ATOM_NS_URI, "updated", AtomConstants.ATOM_NS_PREFIX);
	private static final QName PUBLISHED_QNAME = new QName(AtomConstants.ATOM_NS_URI, "published", AtomConstants.ATOM_NS_PREFIX);
	private static final QName RIGHTS_QNAME = new QName(AtomConstants.ATOM_NS_URI, "rights", AtomConstants.ATOM_NS_PREFIX);

	// protected area for the builder

	/**
	 * Sets the summary of the entry.
	 * 
	 * @param summary The summary of the entry.
	 */
	protected void setSummary(final AtomText summary) {
		this.summary = summary;
	}

	/**
	 * Set the rights of this entry.
	 * 
	 * @param rights The rights of this entry.
	 */
	protected void setRights(final AtomText rights) {
		this.rights = rights;
	}

	/**
	 * Sets the content of the entry.
	 * 
	 * @param content The content of the entry.
	 */
	protected void setContent(final AtomContent content) {
		this.content = content;
	}

	/**
	 * Sets the source of the atom entry.
	 * 
	 * @param source The source of the entry.
	 */
	protected void setSource(final AtomSource source) {
		this.source = source;
	}

	/**
	 * Returns the lazy initialized list of links.
	 * 
	 * @return The lazy initialized list of links.
	 */
	protected void setLinks(final List<AtomLink> links) {
		if (links == null || links.isEmpty()) {
			this.links = Collections.emptyList();
		} else {
			this.links = Collections.unmodifiableList(new ArrayList<AtomLink>(links));
		}
	}

	/**
	 * Returns the lazy initialized contributors.
	 * 
	 * @return The lazy initialized contributors.
	 */
	protected void setContributors(final List<AtomPerson> contributors) {
		if (contributors == null || contributors.isEmpty()) {
			this.contributors = Collections.emptyList();
		} else {
			this.contributors = Collections.unmodifiableList(new ArrayList<AtomPerson>(contributors));
		}
	}

	/**
	 * Returns the lazy initialized categories.
	 * 
	 * @return The lazy initialized categories.
	 */
	protected void setCategories(final List<AtomCategory> categories) {
		if (categories == null || categories.isEmpty()) {
			this.categories = Collections.emptyList();
		} else {
			this.categories = Collections.unmodifiableList(new ArrayList<AtomCategory>(categories));
		}
	}

	/**
	 * Returns the lazy initialized list of authors.
	 * 
	 * @return The lazy initialized list of authors.
	 */
	protected void setAuthors(final List<AtomPerson> authors) {
		if (authors == null || authors.isEmpty()) {
			this.authors = Collections.emptyList();
		} else {
			this.authors = Collections.unmodifiableList(new ArrayList<AtomPerson>(authors));
		}
	}
}
