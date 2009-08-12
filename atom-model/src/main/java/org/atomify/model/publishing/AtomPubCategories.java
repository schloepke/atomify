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
import java.util.Iterator;
import java.util.List;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.AtomDocument;
import org.atomify.model.AtomMediaType;
import org.atomify.model.common.UndefinedElement;
import org.atomify.model.syndication.AtomCategory;
import org.jbasics.xml.types.XmlBooleanYesNoType;

/**
 * Represents either the Categories Document or the link to a Categories Document
 * (appOutOfLineCategories).
 * 
 * @author Stephan Schloepke
 */
public class AtomPubCategories implements AtomDocument, Iterable<AtomCategory> {

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
	private final List<Object> undefinedContent;

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
	public AtomPubCategories(URI href, List<Object> undefinedContent) {
		this.href = AtomContractConstraint.notNull("href", href);
		this.fixed = null;
		this.scheme = null;
		this.categories = Collections.emptyList();
		if (undefinedContent == null || undefinedContent.isEmpty()) {
			this.undefinedContent = Collections.emptyList();
		} else {
			for (Object temp : undefinedContent) {
				if (temp instanceof UndefinedElement) {
					if (AtomConstants.ATOM_PUB_NS_URI.equals(((UndefinedElement) temp).getQualifiedName()
							.getNamespaceURI())) {
						throw new IllegalArgumentException(
								"Categories undefiend content cannot have elements of the atom publishing namespace");
					}
				}
			}
			this.undefinedContent = Collections.unmodifiableList(new ArrayList<Object>(undefinedContent));
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
	public AtomPubCategories(XmlBooleanYesNoType fixed, URI scheme, List<AtomCategory> categories,
			List<Object> undefinedContent) {
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
			for (Object temp : undefinedContent) {
				if (temp instanceof UndefinedElement) {
					if (AtomConstants.ATOM_PUB_NS_URI
							.equals(((UndefinedElement) temp).getQualifiedName().getNamespaceURI())) {
						throw new IllegalArgumentException(
								"Categories undefiend content cannot have elements of the atom publishing namespace");
					}
				}
			}
			this.undefinedContent = Collections.unmodifiableList(new ArrayList<Object>(undefinedContent));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.atomify.model.AtomDocument#getMediaType()
	 */
	public AtomMediaType getMediaType() {
		return AtomConstants.ATOM_PUB_CATEGORIES_MEDIA_TYPE;
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
		return this.fixed != null && XmlBooleanYesNoType.YES.equals(this.fixed);
	}

	public URI getScheme() {
		return this.scheme;
	}

	public List<AtomCategory> getCategories() {
		return this.categories;
	}

	public List<Object> getUndefinedContent() {
		return this.undefinedContent;
	}

	public Iterator<AtomCategory> iterator() {
		return this.categories.iterator();
	}

	@Override
	public String toString() {
		StringBuilder temp = new StringBuilder("Categories ");
		if (this.href != null) {
			return temp.append("{link: ").append(this.href).append("}").toString();
		} else {
			temp.append(" {fixed: ").append(this.fixed).append(", scheme: ").append(this.scheme).append(
					", categories [");
			if (this.categories != null && this.categories.size() > 0) {
				for (AtomCategory category : this.categories) {
					temp.append(category).append(", ");
				}
				temp.setLength(temp.length() - 2);
			}
			return temp.append("]}").toString();
		}
	}

}
