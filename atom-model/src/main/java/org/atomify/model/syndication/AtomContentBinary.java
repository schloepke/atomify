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
import org.atomify.model.AtomContractConstraint;
import org.jbasics.codec.RFC3548Base64Codec;
import org.jbasics.net.mediatype.MediaType;
import org.jbasics.net.mediatype.RFC3023XMLMediaTypes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class AtomContentBinary extends AtomContent {
	private final MediaType mediaType;
	private final byte[] data;

	public AtomContentBinary(MediaType mediaType, String base64Content) {
		this(mediaType, RFC3548Base64Codec.INSTANCE.decode(AtomContractConstraint.mustNotBeEmptyString(base64Content, "base64Content")));
	}

	public AtomContentBinary(MediaType mediaType, byte[] data) {
		this.mediaType = AtomContractConstraint.notNull("mediaType", mediaType);
		if (RFC3023XMLMediaTypes.isXmlMediaType(mediaType) || "text".equals(mediaType.getType())) {
			throw new IllegalArgumentException("[AtomContractViolation] Binary content cannot be a text or xml media type");
		}
		this.data = AtomContractConstraint.notNull("data", data);

	}

	public MediaType getMediaType() {
		return this.mediaType;
	}

	public byte[] getData() {
		return this.data;
	}

	@Override
	public boolean isBinary() {
		return true;
	}

	@Override
	public boolean isMediaType(MediaType type) {
		return this.mediaType.isMediaTypeMatching(type);
	}

	// FIXME: Write a much better way of serialization

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(handler, attributes);
		addAttribute(attributes, TYPE_QNAME, this.mediaType.toString());
		String namespace = AtomConstants.ATOM_NS_URI;
		String local = "content";
		String qName = AtomConstants.ATOM_NS_PREFIX + ":" + local;
		handler.startElement(namespace, local, qName, attributes);
		// This is awkward since it really uses quite a bit of memory need to do some streaming like
		// splitting the array into
		// smaller chunks to encode (must be a multiple of one encode block
		char[] data = new StringBuilder().append(RFC3548Base64Codec.INSTANCE.encode(this.data)).toString().toCharArray();
		handler.characters(data, 0, data.length);
		handler.endElement(namespace, local, qName);
	}

}
