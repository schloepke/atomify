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
package org.atomify.model.publishing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonAttributes;
import org.atomify.model.extension.AtomExtension;
import org.atomify.model.syndication.AtomPlainText;
import org.atomify.model.syndication.AtomText;
import org.jbasics.net.mediatype.MediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Listenbeschreibung
 * <p>
 * Detailierte Beschreibung
 * </p>
 * 
 * @author stephan
 */
public class AtomPubWorkspace extends AtomCommonAttributes implements Iterable<AtomPubCollection> {
	private final AtomText title;
	private final List<AtomPubCollection> collections;
	private final List<AtomExtension> extensions;

	public static AtomPubWorkspaceBuilder newBuilder() {
		return AtomPubWorkspaceBuilder.newInstance();
	}

	protected AtomPubWorkspace(AtomText title, List<AtomPubCollection> collections, List<AtomExtension> extensions) {
		this.title = AtomContractConstraint.notNull("title", title);
		if (collections != null && collections.size() > 0) {
			this.collections = Collections.unmodifiableList(new ArrayList<AtomPubCollection>(collections));
		} else {
			this.collections = Collections.emptyList();
		}
		if (extensions == null || extensions.isEmpty()) {
			this.extensions = Collections.emptyList();
		} else {
			for (AtomExtension extension : extensions) {
				if (AtomConstants.ATOM_TITLE_QNAME.equals(extension.getQualifiedName())) {
					throw new IllegalArgumentException("Atom Publishing Workspace cannot have an atom:title extension");
				}
			}
			this.extensions = Collections.unmodifiableList(new ArrayList<AtomExtension>(extensions));
		}
	}

	/**
	 * @return the title
	 */
	public AtomText getTitle() {
		return this.title;
	}

	/**
	 * @return the collections
	 */
	public List<AtomPubCollection> getCollections() {
		return this.collections;
	}

	/**
	 * @return the extensions
	 */
	public List<AtomExtension> getExtensions() {
		return this.extensions;
	}

	public Iterator<AtomPubCollection> iterator() {
		return this.collections.iterator();
	}

	public AtomPubCollection findCollection(String title, MediaType... mediaTypes) {
		AtomContractConstraint.notNull("title", title);
		for (AtomPubCollection collection : this) {
			AtomText temp = collection.getTitle();
			// FIXME: This is not nice. First we have to KNOW the real type
			// and second we need to cast it so we can get the content. This is not a
			// good solution since changes would affect this area.
			if (temp.isTextType()) {
				if (title.equalsIgnoreCase(((AtomPlainText) temp).getValue())) {
					if (mediaTypes != null && mediaTypes.length > 0) {
						if (collection.isMediaTypeAccepted(mediaTypes)) {
							return collection;
						}
					} else {
						return collection;
					}
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.collections == null) ? 0 : this.collections.hashCode());
		result = prime * result + ((this.extensions == null) ? 0 : this.extensions.hashCode());
		result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
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
		if (!(obj instanceof AtomPubWorkspace)) {
			return false;
		}
		AtomPubWorkspace other = (AtomPubWorkspace) obj;
		if (this.collections == null) {
			if (other.collections != null) {
				return false;
			}
		} else if (!this.collections.equals(other.collections)) {
			return false;
		}
		if (this.extensions == null) {
			if (other.extensions != null) {
				return false;
			}
		} else if (!this.extensions.equals(other.extensions)) {
			return false;
		}
		if (this.title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!this.title.equals(other.title)) {
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
		return new StringBuilder().append("AtomPubWorkspace [title=").append(this.title).append(", collections=").append(this.collections).append(
				", extensions=").append(this.extensions).append(", ").append(super.toString()).append("]").toString();
	}

	// --- FIXME: From here all is serialization. We Still need to think about a good way to do so.

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(handler, attributes);
		handler.startElement(AtomConstants.ATOM_PUB_NS_URI, "workspace", "app:workspace", attributes);
		this.title.serialize(TITLE_QNAME, handler, attributes);
		for (AtomPubCollection collection : this.collections) {
			collection.serialize(handler, attributes);
		}
		for (AtomExtension extension : this.extensions) {
			extension.serialize(handler, attributes);
		}
		handler.endElement(AtomConstants.ATOM_PUB_NS_URI, "workspace", "app:workspace");
	}

	private static final QName TITLE_QNAME = new QName(AtomConstants.ATOM_NS_URI, "title", AtomConstants.ATOM_NS_PREFIX);

}
