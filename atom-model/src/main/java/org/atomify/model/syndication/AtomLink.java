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

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonAttributes;
import org.atomify.model.common.AtomLanguage;
import org.atomify.model.extension.AtomForeignMarkup;
import org.jbasics.net.mediatype.MediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Represents an atom link element.
 * 
 * @author Stephan Schloepke
 */
public class AtomLink extends AtomCommonAttributes {
	/**
	 * <b>Required:</b> href attribute.
	 * <p>
	 * TODO: must be an IRI reference.
	 * </p>
	 */
	private final URI href; // REQUIRED
	/**
	 * <b>Optional:</b> relation(rel) attribute. If not present it is default <em>alternate</em>
	 * <p>
	 * TODO: it must be either an IRI or an atom NC name. Need to think about how to wrap this
	 * correctly.
	 * </p>
	 */
	private final URI rel; // AtomNCName or AtomURI
	/**
	 * <b>Optional:</b> type attribute holding the media type of the destination.
	 */
	private final MediaType type;
	/**
	 * <b>Optional:</b> hreflang attribute holding the language of the destination.
	 */
	private final AtomLanguage hreflang;
	/**
	 * <b>Optional:</b> title attribute. The title is language sensitive.
	 */
	private final String title;
	/**
	 * <b>Optional:</b> length attribute.
	 */
	private final Integer length;
	/**
	 * <b>Optional:</b> any mixed content holding text|element. Any direct element must not be of
	 * the atom name space.
	 * <p>
	 * TODO: Do we want to aktually make this a Node of DOM?
	 * </p>
	 */
	private final List<AtomForeignMarkup> undefinedContent;

	public static AtomLinkBuilder newBuilder() {
		return AtomLinkBuilder.newInstance();
	}

	/**
	 * Creates a link for the given href and relation and type.
	 * 
	 * @param href The href of the link (must not be null).
	 * @param rel The relation of the link.
	 * @param type The media type of the link.
	 */
	public AtomLink(final String title, final URI href, final URI rel, final MediaType type, final AtomLanguage hrefLang, final Integer length,
			final List<AtomForeignMarkup> undefinedContent) {
		this.title = title;
		this.href = AtomContractConstraint.notNull("href", href);
		this.rel = rel;
		this.type = type;
		this.hreflang = hrefLang;
		this.length = length;
		if (undefinedContent == null || undefinedContent.isEmpty()) {
			this.undefinedContent = Collections.emptyList();
		} else {
			this.undefinedContent = Collections.unmodifiableList(new ArrayList<AtomForeignMarkup>(undefinedContent));
		}
	}

	/**
	 * Returns the link href URI.
	 * 
	 * @return the href
	 */
	public URI getHref() {
		return this.href;
	}

	/**
	 * Returns the link relation.
	 * 
	 * @return the rel
	 */
	public URI getRel() {
		return this.rel;
	}

	/**
	 * Returns the media type of the link destination.
	 * 
	 * @return the type
	 */
	public MediaType getType() {
		return this.type;
	}

	/**
	 * Returns the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Returns the length.
	 * 
	 * @return the lenght
	 */
	public Integer getLength() {
		return this.length;
	}

	public AtomLanguage getHreflang() {
		return this.hreflang;
	}

	/**
	 * Returns the undefined content list.
	 * 
	 * @return the undefinedContent
	 */
	public List<AtomForeignMarkup> getUndefinedContent() {
		return this.undefinedContent;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.href == null) ? 0 : this.href.hashCode());
		result = prime * result + ((this.hreflang == null) ? 0 : this.hreflang.hashCode());
		result = prime * result + ((this.length == null) ? 0 : this.length.hashCode());
		result = prime * result + ((this.rel == null) ? 0 : this.rel.hashCode());
		result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
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
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof AtomLink)) {
			return false;
		}
		AtomLink other = (AtomLink) obj;
		if (this.href == null) {
			if (other.href != null) {
				return false;
			}
		} else if (!this.href.equals(other.href)) {
			return false;
		}
		if (this.hreflang == null) {
			if (other.hreflang != null) {
				return false;
			}
		} else if (!this.hreflang.equals(other.hreflang)) {
			return false;
		}
		if (this.length == null) {
			if (other.length != null) {
				return false;
			}
		} else if (!this.length.equals(other.length)) {
			return false;
		}
		if (this.rel == null) {
			if (other.rel != null) {
				return false;
			}
		} else if (!this.rel.equals(other.rel)) {
			return false;
		}
		if (this.title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!this.title.equals(other.title)) {
			return false;
		}
		if (this.type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!this.type.equals(other.type)) {
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
		return new StringBuilder().append("AtomLink [title=").append(this.title).append(", href=").append(this.href).append(", rel=")
				.append(this.rel).append(", type=").append(this.type).append(", hreflang=").append(this.hreflang).append(", length=").append(
						this.length).append(", undefinedContent=").append(this.undefinedContent).append(", ").append(super.toString()).append("]")
				.toString();
	}

	// FIXME: The following code is serialization which needs to be reimplemented

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(handler, attributes);
		addAttribute(attributes, HREF_QNAME, this.href.toASCIIString());
		if (this.rel != null) {
			addAttribute(attributes, REL_QNAME, this.rel.toASCIIString());
		}
		if (this.type != null) {
			addAttribute(attributes, TYPE_QNAME, this.type.toString());
		}
		if (this.length != null) {
			addAttribute(attributes, LENGTH_QNAME, this.length.toString());
		}
		if (this.hreflang != null) {
			addAttribute(attributes, HREFLANG_QNAME, this.hreflang.getLanguage());
		}
		if (this.title != null) {
			addAttribute(attributes, TITLE_QNAME, this.title);
		}
		handler.startElement(AtomConstants.ATOM_NS_URI, "link", AtomConstants.ATOM_NS_PREFIX + ":link", attributes);
		for (AtomForeignMarkup temp : this.undefinedContent) {
			temp.serialize(handler, attributes);
		}
		handler.endElement(AtomConstants.ATOM_NS_URI, "link", AtomConstants.ATOM_NS_PREFIX + ":link");
	}

	private static final QName HREF_QNAME = new QName("href");
	private static final QName REL_QNAME = new QName("rel");
	private static final QName TYPE_QNAME = new QName("type");
	private static final QName HREFLANG_QNAME = new QName("hreflang");
	private static final QName TITLE_QNAME = new QName("title");
	private static final QName LENGTH_QNAME = new QName("lenght");

}
