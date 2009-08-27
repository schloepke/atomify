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

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.AtomDocument;
import org.atomify.model.extension.AtomForeignMarkup;
import org.atomify.model.syndication.AtomCategory;
import org.jbasics.net.mediatype.MediaType;
import org.jbasics.xml.types.XmlBooleanYesNoType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Represents either the Categories Document or the link to a Categories Document
 * (appOutOfLineCategories).
 * 
 * @author Stephan Schloepke
 */
public class AtomPubCategories implements AtomDocument, Iterable<AtomCategory>, Serializable {
	/**
	 * The media type as a string constant.
	 */
	public static final String MEDIA_TYPE_STRING = "application/atomcat+xml";
	/**
	 * The media type as {@link AtomMediaType}.
	 */
	public static final MediaType MEDIA_TYPE = MediaType.valueOf(MEDIA_TYPE_STRING);

	/**
	 * Required Out Of Line Attribute
	 */
	private final URI href;

	/**
	 * Optional Inline Attribute: values are "yes" or "no".
	 */
	private final XmlBooleanYesNoType fixed;
	/**
	 * Optional Inline Attribute.
	 */
	private final URI scheme;
	/**
	 * Optional Inline Elements {@link AtomCategory}
	 */
	private final List<AtomCategory> categories;

	/**
	 * Optional Mixed (text | anyForeignElement)
	 */
	private final List<AtomForeignMarkup> undefinedContent;

	/**
	 * Creates a new builder to build {@link AtomPubCategories}.
	 * 
	 * @return The empty builder
	 */
	public static AtomPubCategoriesBuilder newBuilder() {
		return AtomPubCategoriesBuilder.newInstance();
	}

	/**
	 * Create an out of line Categories element (a link to a categories document).
	 * 
	 * @param href The link to the categories document (must not be null)
	 * @param undefinedContent The undefined content of the element or document (can be null or
	 *            empty)
	 */
	public AtomPubCategories(URI href, List<AtomForeignMarkup> undefinedContent) {
		this.href = AtomContractConstraint.notNull("href", href);
		this.fixed = null;
		this.scheme = null;
		this.categories = Collections.emptyList();
		if (undefinedContent == null || undefinedContent.isEmpty()) {
			this.undefinedContent = Collections.emptyList();
		} else {
			for (AtomForeignMarkup temp : undefinedContent) {
				if (temp.getQualifiedName() != null) {
					if (AtomConstants.ATOM_PUB_NS_URI.equals(temp.getQualifiedName().getNamespaceURI())) {
						throw new IllegalArgumentException("Categories undefiend content cannot have elements of the atom publishing namespace");
					}
				}
			}
			this.undefinedContent = Collections.unmodifiableList(new ArrayList<AtomForeignMarkup>(undefinedContent));
		}
	}

	/**
	 * Creates an inline categories element (not linked to an external categories document).
	 * 
	 * @param fixed The value of the fixed element (can be null to use default)
	 * @param scheme The scheme (can be null)
	 * @param categories The categories (can be null or empty)
	 * @param undefinedContent The undefined content following the categories (can be null or empty)
	 */
	public AtomPubCategories(XmlBooleanYesNoType fixed, URI scheme, List<AtomCategory> categories, List<AtomForeignMarkup> undefinedContent) {
		this.href = null;
		this.fixed = fixed;
		this.scheme = scheme;
		if (categories == null || categories.isEmpty()) {
			this.categories = Collections.emptyList();
		} else {
			this.categories = Collections.unmodifiableList(new ArrayList<AtomCategory>(categories));
		}
		if (undefinedContent == null || undefinedContent.isEmpty()) {
			this.undefinedContent = Collections.emptyList();
		} else {
			for (AtomForeignMarkup temp : undefinedContent) {
				if (temp.getQualifiedName() != null) {
					if (AtomConstants.ATOM_PUB_NS_URI.equals(temp.getQualifiedName().getNamespaceURI())) {
						throw new IllegalArgumentException("Categories undefiend content cannot have elements of the atom publishing namespace");
					}
				}
			}
			this.undefinedContent = Collections.unmodifiableList(new ArrayList<AtomForeignMarkup>(undefinedContent));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.atomify.model.AtomDocument#getMediaType()
	 */
	public MediaType getMediaType() {
		return MEDIA_TYPE;
	}

	/**
	 * Returns true if the element or document is an inline category (meaning that the href is
	 * null).
	 * 
	 * @return True if the categories are defined inline rather than externally found at the href
	 *         position.
	 */
	public boolean isInlineCategories() {
		return this.href == null;
	}

	public boolean isOutOfLineCategories() {
		return this.href != null;
	}

	public URI getHref() {
		return this.href;
	}

	public XmlBooleanYesNoType getFixed() {
		return this.fixed;
	}

	public boolean isFixed() {
		return XmlBooleanYesNoType.YES == this.fixed;
	}

	public URI getScheme() {
		return this.scheme;
	}

	public List<AtomCategory> getCategories() {
		return this.categories;
	}

	public List<AtomForeignMarkup> getUndefinedContent() {
		return this.undefinedContent;
	}

	public Iterator<AtomCategory> iterator() {
		return this.categories.iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.categories == null) ? 0 : this.categories.hashCode());
		result = prime * result + ((this.fixed == null) ? 0 : this.fixed.hashCode());
		result = prime * result + ((this.href == null) ? 0 : this.href.hashCode());
		result = prime * result + ((this.scheme == null) ? 0 : this.scheme.hashCode());
		result = prime * result + ((this.undefinedContent == null) ? 0 : this.undefinedContent.hashCode());
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
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AtomPubCategories)) {
			return false;
		}
		AtomPubCategories other = (AtomPubCategories) obj;
		if (this.categories == null) {
			if (other.categories != null) {
				return false;
			}
		} else if (!this.categories.equals(other.categories)) {
			return false;
		}
		if (this.fixed == null) {
			if (other.fixed != null) {
				return false;
			}
		} else if (!this.fixed.equals(other.fixed)) {
			return false;
		}
		if (this.href == null) {
			if (other.href != null) {
				return false;
			}
		} else if (!this.href.equals(other.href)) {
			return false;
		}
		if (this.scheme == null) {
			if (other.scheme != null) {
				return false;
			}
		} else if (!this.scheme.equals(other.scheme)) {
			return false;
		}
		if (this.undefinedContent == null) {
			if (other.undefinedContent != null) {
				return false;
			}
		} else if (!this.undefinedContent.equals(other.undefinedContent)) {
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
		return new StringBuilder().append("AtomPubCategories [categories=").append(this.categories).append(", fixed=").append(this.fixed).append(
				", href=").append(this.href).append(", scheme=").append(this.scheme).append(", undefinedContent=").append(this.undefinedContent)
				.append("]").toString();
	}

	// FIXME: The following code is serialization which needs to be reimplemented

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		handler.startPrefixMapping(AtomConstants.ATOM_NS_PREFIX, AtomConstants.ATOM_NS_URI);
		handler.startPrefixMapping(AtomConstants.ATOM_PUB_NS_PREFIX, AtomConstants.ATOM_PUB_NS_URI);
		if (attributes == null) {
			attributes = new AttributesImpl();
		} else {
			attributes.clear();
		}
		if (this.href != null) {
			addAttribute(attributes, "href", this.href.toASCIIString());
		} else {
			if (this.scheme != null) {
				addAttribute(attributes, "scheme", this.scheme.toASCIIString());
			}
			if (this.fixed != null) {
				addAttribute(attributes, "fixed", this.fixed.toXmlString());
			}
		}
		handler.startElement(AtomConstants.ATOM_PUB_NS_URI, "categories", AtomConstants.ATOM_PUB_NS_PREFIX + ":categories", attributes);
		if (this.href == null) {
			for (AtomCategory category : this.categories) {
				category.serialize(handler, attributes);
			}
		}
		for (AtomForeignMarkup temp : this.undefinedContent) {
			temp.serialize(handler, attributes);
		}
		handler.endElement(AtomConstants.ATOM_PUB_NS_URI, "categories", AtomConstants.ATOM_PUB_NS_PREFIX + ":categories");
		handler.endPrefixMapping(AtomConstants.ATOM_NS_PREFIX);
		handler.endPrefixMapping(AtomConstants.ATOM_PUB_NS_PREFIX);
	}

	protected static void addAttribute(AttributesImpl attributes, String name, String value) throws SAXException {
		if (value != null) {
			attributes.addAttribute("", name, name, "CDATA", value);
		}
	}

}
