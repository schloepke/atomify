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

import org.atomify.model.AtomCommonAttributes;
import org.atomify.model.AtomContractConstraint;

/**
 * Represents an atom logo.
 * 
 * @author Stephan Schloepke
 */
public class AtomLogo extends AtomCommonAttributes {
	/**
	 * <b>Required:</b> URI content.
	 * <p>
	 * TODO: must be an iri reference.
	 * </p>
	 */
	private URI logoIRI;

	/**
	 * Creates an atom logo with the given logo IRI (must not be null).
	 * 
	 * @param logoIRI The logo IRI (must not be null).
	 */
	public AtomLogo(final URI logoIRI) {
		setLogoIRI(logoIRI);
	}

	/**
	 * Set the logo IRI (must not be null).
	 * 
	 * @param logoIRI The logo IRI (must not be null).
	 */
	public void setLogoIRI(final URI logoIRI) {
		this.logoIRI = AtomContractConstraint.notNull("logoIRI", logoIRI);
	}

	/**
	 * Returns the logo IRI.
	 * 
	 * @return the logoIRI
	 */
	public URI getLogoIRI() {
		return this.logoIRI;
	}

}
