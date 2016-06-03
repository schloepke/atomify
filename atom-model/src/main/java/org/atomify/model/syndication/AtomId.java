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
import java.util.UUID;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonAttributes;
import org.jbasics.checker.ContractCheck;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

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

	public static AtomId valueOf(String id) {
		return new AtomId(URI.create(ContractCheck.mustNotBeNull(id, "id")));
	}

	public static AtomId valueOf(URI id) {
		return new AtomId(id);
	}

	public static AtomId valueOf(UUID uuid) {
		return new AtomId(URI.create("urn:uuid:" + AtomContractConstraint.notNull("uuid", uuid)));
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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
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
		if (!(obj instanceof AtomId)) {
			return false;
		}
		AtomId other = (AtomId) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
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
		return new StringBuilder().append("AtomId [id=").append(this.id).append(", ").append(super.toString()).append("]").toString();
	}

	// FIXME: Write a much better way of serialization

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(handler, attributes);
		handler.startElement(AtomConstants.ATOM_NS_URI, "id", AtomConstants.ATOM_NS_PREFIX + ":id", attributes);
		char[] data = this.id.toASCIIString().toCharArray();
		handler.characters(data, 0, data.length);
		handler.endElement(AtomConstants.ATOM_NS_URI, "id", AtomConstants.ATOM_NS_PREFIX + ":id");
	}

}
