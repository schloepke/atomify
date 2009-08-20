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
package org.atomify.model.publishing;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonAttributes;
import org.atomify.model.extension.AtomExtension;
import org.atomify.model.syndication.AtomText;
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
public class AtomPubCollection extends AtomCommonAttributes {
	/**
	 * <b>Required:</b> the title element.
	 */
	private final AtomText title;
	/**
	 * <b>Required:</b> The href of the collection
	 */
	private final URI href;
	/**
	 * <b>Optional:</b> The optional accept fields. If empty the default accept of atom:entry is in
	 * place.
	 */
	private final List<AtomPubAccept> accepts;
	/**
	 * <b>Optional:</b> The categories
	 */
	private final List<AtomPubCategories> categories;
	/**
	 * <b>Optional:</b> extension elements (all non AtomPub namespace and not atom:title)
	 */
	private final List<AtomExtension> extensions;

	public static AtomPubCollectionBuilder newBuilder() {
		return AtomPubCollectionBuilder.newInstance();
	}

	protected AtomPubCollection(AtomText title, URI href, List<AtomPubAccept> accepts, List<AtomPubCategories> categories,
			List<AtomExtension> extensions) {
		this.title = AtomContractConstraint.notNull("title", title);
		this.href = AtomContractConstraint.notNull("href", href);
		if (accepts != null && accepts.size() > 0) {
			this.accepts = Collections.unmodifiableList(new ArrayList<AtomPubAccept>(accepts));
		} else {
			this.accepts = Collections.emptyList();
		}
		if (categories != null && categories.size() > 0) {
			this.categories = Collections.unmodifiableList(new ArrayList<AtomPubCategories>(categories));
		} else {
			this.categories = Collections.emptyList();
		}
		if (extensions == null || extensions.isEmpty()) {
			this.extensions = Collections.emptyList();
		} else {
			for (AtomExtension extension : extensions) {
				if (AtomConstants.ATOM_TITLE_QNAME.equals(extension.getQualifiedName())) {
					throw new IllegalArgumentException("Atom Publishing Collection cannot have an atom:title extension");
				}
			}
			this.extensions = Collections.unmodifiableList(new ArrayList<AtomExtension>(extensions));
		}
	}

	/**
	 * @return the href
	 */
	public URI getHref() {
		return this.href;
	}

	/**
	 * @return the title
	 */
	public AtomText getTitle() {
		return this.title;
	}

	/**
	 * @return the accepts
	 */
	public List<AtomPubAccept> getAccepts() {
		return this.accepts;
	}

	/**
	 * @return the categories
	 */
	public List<AtomPubCategories> getCategories() {
		return this.categories;
	}

	/**
	 * @return the extensions
	 */
	public List<AtomExtension> getExtensions() {
		return this.extensions;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.accepts == null) ? 0 : this.accepts.hashCode());
		result = prime * result + ((this.categories == null) ? 0 : this.categories.hashCode());
		result = prime * result + ((this.extensions == null) ? 0 : this.extensions.hashCode());
		result = prime * result + ((this.href == null) ? 0 : this.href.hashCode());
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
		if (!(obj instanceof AtomPubCollection)) {
			return false;
		}
		AtomPubCollection other = (AtomPubCollection) obj;
		if (this.accepts == null) {
			if (other.accepts != null) {
				return false;
			}
		} else if (!this.accepts.equals(other.accepts)) {
			return false;
		}
		if (this.categories == null) {
			if (other.categories != null) {
				return false;
			}
		} else if (!this.categories.equals(other.categories)) {
			return false;
		}
		if (this.extensions == null) {
			if (other.extensions != null) {
				return false;
			}
		} else if (!this.extensions.equals(other.extensions)) {
			return false;
		}
		if (this.href == null) {
			if (other.href != null) {
				return false;
			}
		} else if (!this.href.equals(other.href)) {
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
		return new StringBuilder().append("AtomPubCollection [title=").append(this.title).append(", href=").append(this.href).append(", accepts=")
				.append(this.accepts).append(", categories=").append(this.categories).append(", extensions=").append(this.extensions).append(", ")
				.append(super.toString()).append("]").toString();
	}

	// --- From here all is serialization. We Still need to think about a good way to do so.

	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		addAttribute(attributes, HREF_ATTRIBUTE_QNAME, this.href.toASCIIString());
		handler.startElement(AtomConstants.ATOM_PUB_NS_URI, "collection", "app:collection", attributes);
		this.title.serialize(TITLE_QNAME, handler, attributes);
		for (AtomPubAccept accept : this.accepts) {
			accept.serialize(handler, attributes);
		}
		for (AtomPubCategories categories : this.categories) {
			categories.serialize(handler, attributes);
		}
		for (AtomExtension extension : this.extensions) {
			extension.serialize(handler, attributes);
		}
		handler.endElement(AtomConstants.ATOM_PUB_NS_URI, "collection", "app:collection");
	}

	private static final QName TITLE_QNAME = new QName(AtomConstants.ATOM_NS_URI, "title", AtomConstants.ATOM_NS_PREFIX);
	private static final QName HREF_ATTRIBUTE_QNAME = new QName("href");
}
