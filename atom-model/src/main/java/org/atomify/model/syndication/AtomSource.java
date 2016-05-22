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

import org.atomify.model.AtomConstants;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Atom source is an element holding all information a feed holds but the entries.
 * <p>
 * The feed and source element in atom are in most cases equal. The difference is only in the
 * requirement of certain elements to be present and that the source has no entries compared to the
 * feed. Since the source would be embedded into an entry given the information of the feed it comes
 * from it is sufficient to think that a feed is a source as well. Thats why this implementation
 * derived the feed from the source element.
 * </p>
 * 
 * @author stephan
 */
public class AtomSource extends AbstractAtomSource {

	public static AtomSourceBuilder newBuilder() {
		return AtomSourceBuilder.newInstance();
	}

	public AtomSource(AtomId id, AtomText title, AtomDate updated) {
		super(id, title, updated);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 31 * super.hashCode();
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
		if (!super.equals(obj) || !(obj instanceof AtomSource)) {
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
		return new StringBuilder().append("AtomSource [").append(super.toString()).append("]").toString();
	}

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		handler.startPrefixMapping(AtomConstants.ATOM_NS_PREFIX, AtomConstants.ATOM_NS_URI);
		attributes = initCommonAttributes(attributes);
		handler.startElement(AtomConstants.ATOM_NS_URI, "source", AtomConstants.ATOM_NS_PREFIX + ":source", attributes);
		super.serializeContent(handler, attributes);
		handler.endElement(AtomConstants.ATOM_NS_URI, "source", AtomConstants.ATOM_NS_PREFIX + ":source");
		handler.endPrefixMapping(AtomConstants.ATOM_NS_PREFIX);
	}

}
