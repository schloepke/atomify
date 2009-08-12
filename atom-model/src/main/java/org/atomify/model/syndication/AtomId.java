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
 * An atom identifier must be an IRI which is absolute.
 * <p>
 * The current implementation does only allow URIs instead of IRIs. However this is required to
 * change as soon as we have implemented the IRI since that is not available in Java yet. An URI is
 * a subset of an IRI more or less and any IRI can be converted to an URI. For now it is wise to
 * only select URI conform IDs until a fully implemented IRI is available.
 * </p>
 * 
 * @author Stephan Schloepke
 */
public class AtomId extends AtomCommonAttributes {
	/**
	 * <b>Required:</b> IRI referecnce. Must be absolute.
	 * <p>
	 * TODO: Must be an IRI reference.
	 * </p>
	 */
	private final URI id;

	public static AtomIdBuilder newBuilder() {
		return AtomIdBuilder.newInstance();
	}

	/**
	 * Creates an atom identifier for the given IRI. The IRI must not be null or relative.
	 * 
	 * @param idIRI The IRI to use as atom identifier.
	 */
	protected AtomId(final URI id) {
		this.id = AtomContractConstraint.notNull("id", id);
	}

	/**
	 * Returns the IRI of this atom identifier.
	 * 
	 * @return The atom identifier IRI.
	 */
	public URI getId() {
		return this.id;
	}

}
