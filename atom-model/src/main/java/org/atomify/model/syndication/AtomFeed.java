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

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.AtomDocument;
import org.atomify.model.AtomMediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Represents an atom feed. The feed is derived from the atom:source since both cover mostly the
 * same content.
 * <p>
 * The following additional restrictions apply for a feed compared to a source:
 * <ul>
 * <li>ID (AtomID) is mandatory</li>
 * <li>Title (AtomText) is mandatory</li>
 * <li>Updated (AtomDate) is mandatory</li>
 * <li>Author (AtomPerson) is mandatory if not all entries have at least one author</li>
 * </ul>
 * </p>
 * 
 * @author stephan
 */
public class AtomFeed extends AbstractAtomSource implements AtomDocument {
	/**
	 * <b>Optional:</b> any number of entry elements.
	 */
	private final List<AtomEntry> entries;

	public static AtomFeedBuilder newBuilder() {
		return AtomFeedBuilder.newInstance();
	}

	/**
	 * Creates an atom: feed with the given id, title and the updated date.
	 * 
	 * @param id The id of the atom feed (Must not be null).
	 * @param title The title of the atom feed (Must not be null).
	 * @param updated The updated date (must not be null).
	 */
	public AtomFeed(final AtomId id, final AtomText title, final AtomDate updated, List<AtomEntry> entries) {
		super(AtomContractConstraint.notNull("id", id), AtomContractConstraint.notNull("title", title), AtomContractConstraint.notNull("updated",
				updated));
		if (entries == null || entries.isEmpty()) {
			this.entries = Collections.emptyList();
		} else {
			this.entries = Collections.unmodifiableList(new ArrayList<AtomEntry>(entries));
		}
	}

	/**
	 * Returns the lazy initialized list of entries.
	 * 
	 * @return The lazy initialized list of entries.
	 */
	public List<AtomEntry> getEntries() {
		return this.entries;
	}

	public AtomMediaType getMediaType() {
		return AtomConstants.ATOM_FEED_MEDIA_TYPE;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.entries == null) ? 0 : this.entries.hashCode());
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
		if (!(obj instanceof AtomFeed)) {
			return false;
		}
		AtomFeed other = (AtomFeed) obj;
		if (this.entries == null) {
			if (other.entries != null) {
				return false;
			}
		} else if (!this.entries.equals(other.entries)) {
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
		return new StringBuilder().append("AtomFeed [").append(super.toString()).append(", entries=").append(this.entries).append("]").toString();
	}

	// --- FIXME: From here all is serialization. We Still need to think about a good way to do so.

	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		handler.startPrefixMapping(AtomConstants.ATOM_NS_PREFIX, AtomConstants.ATOM_NS_URI);
		attributes = initCommonAttributes(attributes);
		handler.startElement(AtomConstants.ATOM_NS_URI, "feed", AtomConstants.ATOM_NS_PREFIX + ":feed", attributes);
		super.serializeContent(handler, attributes);
		for (AtomEntry entry : this.entries) {
			entry.serialize(handler, attributes);
		}
		handler.endElement(AtomConstants.ATOM_NS_URI, "feed", AtomConstants.ATOM_NS_PREFIX + ":feed");
		handler.endPrefixMapping(AtomConstants.ATOM_NS_PREFIX);
	}

}
