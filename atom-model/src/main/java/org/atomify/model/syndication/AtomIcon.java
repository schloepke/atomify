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

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonAttributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Represents an atom icon.
 * 
 * @author Stephan Schloepke
 */
public class AtomIcon extends AtomCommonAttributes {
	/**
	 * <b>Required:</b> IRI content.
	 * <p>
	 * TODO: must be an IRI reference.
	 * </p>
	 */
	private final URI uri;

	public static AtomIconBuilder newBuilder() {
		return AtomIconBuilder.newInstance();
	}

	/**
	 * Creates an atom icon with the given icon IRI (Must not be null).
	 * 
	 * @param uri The icon IRI must not be null.
	 */
	public AtomIcon(final URI uri) {
		this.uri = AtomContractConstraint.notNull("uri", uri);
	}

	/**
	 * Returns the uri of the icon.
	 * 
	 * @return the uri
	 */
	public URI getUri() {
		return this.uri;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.uri == null) ? 0 : this.uri.hashCode());
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
		if (!(obj instanceof AtomIcon)) {
			return false;
		}
		AtomIcon other = (AtomIcon) obj;
		if (this.uri == null) {
			if (other.uri != null) {
				return false;
			}
		} else if (!this.uri.equals(other.uri)) {
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
		return new StringBuilder().append("AtomIcon [uri=").append(this.uri).append(", ").append(super.toString()).append("]").toString();
	}

	// FIXME: Write a much better way of serialization

	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		handler.startElement(AtomConstants.ATOM_NS_URI, "icon", AtomConstants.ATOM_NS_PREFIX + ":icon", attributes);
		char[] data = this.uri.toASCIIString().toCharArray();
		handler.characters(data, 0, data.length);
		handler.endElement(AtomConstants.ATOM_NS_URI, "icon", AtomConstants.ATOM_NS_PREFIX + ":icon");
	}

}
