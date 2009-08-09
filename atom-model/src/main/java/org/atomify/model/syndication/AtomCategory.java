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
import java.util.List;

import org.atomify.model.AtomContractConstraint;


/**
 * Holds an Atom category.
 * 
 * @author Stephan Schloepke
 */
public class AtomCategory extends AtomCommonAttributes {
	/**
	 * <b>Required:</b> term attribute.
	 */
	private String term;
	/**
	 * <b>Optional:</b> scheme attribute.
	 * <p>
	 * TODO: must be an IRI reference instead of an URI reference.
	 * </p>
	 */
	private URI scheme;
	/**
	 * <b>Optional:</b> label attribute.
	 */
	private String label;
	/**
	 * <b>Optional:</b> mixed content (text|element). Any direct element must not be of the atom name space.
	 */
	private List<Object> undefinedContent;

	/**
	 * Constructor for the given term.
	 * 
	 * @param term The term (must not be null).
	 */
	public AtomCategory(final String term) {
		this.term = AtomContractConstraint.notNull("term", term);
	}

	/**
	 * Constructor for the given term, scheme and label.
	 * 
	 * @param term The term (must not be null).
	 * @param scheme The scheme.
	 * @param label The label.
	 */
	public AtomCategory(final String term, final URI scheme, final String label) {
		this.term = AtomContractConstraint.notNull("term", term);
		this.scheme = scheme;
		this.label = label;
	}

	/**
	 * Sets the term.
	 * 
	 * @param term the term to set
	 */
	public void setTerm(final String term) {
		this.term = AtomContractConstraint.notNull("term", term);
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
	 * Sets the scheme.
	 * 
	 * @param scheme the scheme to set
	 */
	public void setScheme(final URI scheme) {
		this.scheme = scheme;
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
	 * Sets the label.
	 * 
	 * @param label the label to set
	 */
	public void setLabel(final String label) {
		this.label = label;
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
		if (this.undefinedContent == null) {
			this.undefinedContent = new ArrayList<Object>();
		}
		return this.undefinedContent;
	}

}
