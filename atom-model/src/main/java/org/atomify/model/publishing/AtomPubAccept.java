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

import org.atomify.model.AtomConstants;
import org.atomify.model.common.AtomCommonAttributes;
import org.jbasics.pattern.builder.Builder;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * The atom publishing accept element which occurs in the atom publishing collection.
 * <p>
 * The accept element holds an accept media range. This indicates the media range which is accepted
 * on post of the collection. A media range which is an empty string indicates that the collection
 * is read only and does not accept any content. If no accept media type is present that means that
 * the default atom entry is accepted.
 * </p>
 * 
 * @author Stephan Schloepke
 */
public class AtomPubAccept extends AtomCommonAttributes {
	/**
	 * The media range. Can be the empty string.
	 */
	private final String acceptMediaRange;

	/**
	 * Creates a new {@link Builder} for the {@link AtomPubAccept} element.
	 * 
	 * @return A newly created {@link Builder} for {@link AtomPubAccept}.
	 */
	public static AtomPubAcceptBuilder newBuilder() {
		return AtomPubAcceptBuilder.newInstance();
	}

	/**
	 * Creates an accept media range element with the given media range as acceptance.
	 * 
	 * @param acceptMediaRange create the accept media range.
	 */
	public AtomPubAccept(String acceptMediaRange) {
		this.acceptMediaRange = acceptMediaRange == null ? "" : acceptMediaRange.trim();
	}

	/**
	 * Returns the media range accepted by this accept element.
	 * 
	 * @return The media range accepted (cannot be null but empty string to accept nothing).
	 */
	public String getAcceptMediaRange() {
		return this.acceptMediaRange;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.acceptMediaRange.hashCode();
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
		} else if (!super.equals(obj) || !(obj instanceof AtomPubAccept)) {
			return false;
		} else {
			return this.acceptMediaRange.equals(((AtomPubAccept) obj).acceptMediaRange);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder().append("AtomPubAccept [acceptMediaRange=").append(this.acceptMediaRange)
				.append(", ").append(super.toString()).append("]").toString();
	}

	// FIXME: Write a much better way of serialization

	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		handler.startElement(AtomConstants.ATOM_PUB_NS_URI, "accept", AtomConstants.ATOM_PUB_NS_PREFIX + ":accept",
				attributes);
		char[] data = this.acceptMediaRange.toCharArray();
		handler.characters(data, 0, data.length);
		handler.endElement(AtomConstants.ATOM_PUB_NS_URI, "accept", AtomConstants.ATOM_PUB_NS_PREFIX + ":accept");
	}

}
