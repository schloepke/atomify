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

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.AtomDocument;
import org.atomify.model.AtomMediaType;

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
public class AtomFeed extends AtomSource implements AtomDocument {
	/**
	 * <b>Optional:</b> any number of entry elements.
	 */
	private List<AtomEntry> entries;

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
	public AtomFeed(final AtomId id, final AtomText title, final AtomDate updated) {
		super(AtomContractConstraint.notNull("id", id), AtomContractConstraint.notNull("title", title),
				AtomContractConstraint.notNull("updated", updated));
	}

	/**
	 * Returns the lazy initialized list of entries.
	 * 
	 * @return The lazy initialized list of entries.
	 */
	public List<AtomEntry> getEntries() {
		if (this.entries == null) {
			this.entries = new ArrayList<AtomEntry>();
		}
		return this.entries;
	}

	public AtomMediaType getMediaType() {
		return AtomConstants.ATOM_FEED_MEDIA_TYPE;
	}

}
