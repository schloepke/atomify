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

import org.atomify.model.AtomCommonAttributes;
import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.UndefinedElement;

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
	private final List<Object> undefinedContent;

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
	public AtomCategory(final String term, final URI scheme, final String label, final List<Object> undefinedContent) {
		this.term = AtomContractConstraint.notNull("term", term);
		this.scheme = scheme;
		this.label = label;
		if (undefinedContent == null || undefinedContent.isEmpty()) {
			this.undefinedContent = Collections.emptyList();
		} else {
			for (Object temp : undefinedContent) {
				if (temp instanceof UndefinedElement) {
					if (AtomConstants.ATOM_NS_URI
							.equals(((UndefinedElement) temp).getQualifiedName().getNamespaceURI())) {
						throw new IllegalArgumentException(
								"Category undefiend content cannot have elements of the atom namespace");
					}
				}
			}
			this.undefinedContent = Collections.unmodifiableList(new ArrayList<Object>(undefinedContent));
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
	public List<Object> getUndefinedContent() {
		return this.undefinedContent;
	}

	@Override
	public String toString() {
		return "Category {term: " + this.term + ", scheme: " + this.scheme + ", label" + this.label
				+ ", undefined content: " + (this.undefinedContent != null && this.undefinedContent.size() > 0) + "}";
	}
}
