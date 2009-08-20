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

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonAttributes;
import org.atomify.model.extension.AtomForeignMarkup;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Holds an Atom category.
 * 
 * @author Stephan Schloepke
 */
public class AtomCategory extends AtomCommonAttributes {
	/**
	 * <b>Required:</b> term attribute.
	 */
	private final String term;
	/**
	 * <b>Optional:</b> scheme attribute.
	 * <p>
	 * TODO: must be an IRI reference instead of an URI reference.
	 * </p>
	 */
	private final URI scheme;
	/**
	 * <b>Optional:</b> label attribute.
	 */
	private final String label;
	/**
	 * <b>Optional:</b> mixed content (text|element). Any direct element must not be of the atom
	 * name space.
	 */
	private final List<AtomForeignMarkup> undefinedContent;

	public static AtomCategoryBuilder newBuilder() {
		return AtomCategoryBuilder.newInstance();
	}

	/**
	 * Constructor for the given term.
	 * 
	 * @param term The term (must not be null).
	 */
	public AtomCategory(final String term) {
		this.term = AtomContractConstraint.notNull("term", term);
		this.scheme = null;
		this.label = null;
		this.undefinedContent = Collections.emptyList();
	}

	/**
	 * Constructor for the given term, scheme and label.
	 * 
	 * @param term The term (must not be null).
	 * @param scheme The scheme.
	 * @param label The label.
	 */
	public AtomCategory(final String term, final URI scheme, final String label, final List<AtomForeignMarkup> undefinedContent) {
		this.term = AtomContractConstraint.notNull("term", term);
		this.scheme = scheme;
		this.label = label;
		if (undefinedContent == null || undefinedContent.isEmpty()) {
			this.undefinedContent = Collections.emptyList();
		} else {
			this.undefinedContent = Collections.unmodifiableList(new ArrayList<AtomForeignMarkup>(undefinedContent));
		}
	}

	/**
	 * Returns the term.
	 * 
	 * @return the term
	 */
	public String getTerm() {
		return this.term;
	}

	/**
	 * Returns the scheme.
	 * 
	 * @return the scheme
	 */
	public URI getScheme() {
		return this.scheme;
	}

	/**
	 * Returns the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Returns the lazy initialized undefined content.
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
		result = prime * result + ((this.label == null) ? 0 : this.label.hashCode());
		result = prime * result + ((this.scheme == null) ? 0 : this.scheme.hashCode());
		result = prime * result + ((this.term == null) ? 0 : this.term.hashCode());
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
		if (!(obj instanceof AtomCategory)) {
			return false;
		}
		AtomCategory other = (AtomCategory) obj;
		if (this.label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!this.label.equals(other.label)) {
			return false;
		}
		if (this.scheme == null) {
			if (other.scheme != null) {
				return false;
			}
		} else if (!this.scheme.equals(other.scheme)) {
			return false;
		}
		if (this.term == null) {
			if (other.term != null) {
				return false;
			}
		} else if (!this.term.equals(other.term)) {
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
		return new StringBuilder().append("AtomCategory [label=").append(this.label).append(", scheme=").append(this.scheme).append(", term=")
				.append(this.term).append(", undefinedContent=").append(this.undefinedContent).append(", ").append(super.toString()).append("]")
				.toString();
	}

	// FIXME: The following code is serialization which needs to be reimplemented

	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		if (this.term != null) {
			addAttribute(attributes, TERM_QNAME, this.term);
		}
		if (this.label != null) {
			addAttribute(attributes, LABEL_QNAME, this.label);
		}
		if (this.scheme != null) {
			addAttribute(attributes, SCHEME_QNAME, this.scheme.toASCIIString());
		}
		handler.startElement(AtomConstants.ATOM_NS_URI, "category", AtomConstants.ATOM_NS_PREFIX + ":category", attributes);
		for (AtomForeignMarkup temp : this.undefinedContent) {
			temp.serialize(handler, attributes);
		}
		handler.endElement(AtomConstants.ATOM_NS_URI, "category", AtomConstants.ATOM_NS_PREFIX + ":category");
	}

	private static final QName TERM_QNAME = new QName("term");
	private static final QName LABEL_QNAME = new QName("label");
	private static final QName SCHEME_QNAME = new QName("scheme");
}
