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

import javax.xml.namespace.QName;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonAttributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Describes a atom generator field.
 * 
 * @author Stephan Schloepke
 */
public class AtomGenerator extends AtomCommonAttributes {
	/**
	 * <b>Required:</b> The human readable name of the generator as text content.
	 */
	private final String description;
	/**
	 * <b>Optional:</b> uri attribute.
	 * <p>
	 * TODO: Must be an IRI reference.
	 * </p>
	 */
	private final URI uri;
	/**
	 * <b>Optional:</b> version attribute.
	 */
	private final String version;

	public static AtomGeneratorBuilder newBuilder() {
		return AtomGeneratorBuilder.newInstance();
	}

	/**
	 * Creates a generator element with the specified text as value.
	 * 
	 * @param value The text as value.
	 */
	public AtomGenerator(final String description, final String version, final URI uri) {
		this.description = AtomContractConstraint.notNull("description", description);
		this.version = version;
		this.uri = uri;
	}

	/**
	 * Returns the URI of the generator.
	 * 
	 * @return the uri
	 */
	public URI getUri() {
		return this.uri;
	}

	/**
	 * Returns the version of the generator.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * Returns the description of the generator.
	 * 
	 * @return the value
	 */
	public String getDescription() {
		return this.description;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
		result = prime * result + ((this.uri == null) ? 0 : this.uri.hashCode());
		result = prime * result + ((this.version == null) ? 0 : this.version.hashCode());
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
		if (!(obj instanceof AtomGenerator)) {
			return false;
		}
		AtomGenerator other = (AtomGenerator) obj;
		if (this.description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!this.description.equals(other.description)) {
			return false;
		}
		if (this.uri == null) {
			if (other.uri != null) {
				return false;
			}
		} else if (!this.uri.equals(other.uri)) {
			return false;
		}
		if (this.version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!this.version.equals(other.version)) {
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
		return new StringBuilder().append("AtomGenerator [description=").append(this.description).append(", uri=").append(this.uri).append(
				", version=").append(this.version).append(", ").append(super.toString()).append("]").toString();
	}

	// FIXME: Write a much better way of serialization

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		if (this.uri != null) {
			addAttribute(attributes, URI_QNAME, this.uri.toASCIIString());
		}
		if (this.version != null) {
			addAttribute(attributes, VERSION_QNAME, this.version);
		}
		String namespace = AtomConstants.ATOM_NS_URI;
		String local = "generator";
		String qName = AtomConstants.ATOM_NS_PREFIX + ":" + local;
		handler.startElement(namespace, local, qName, attributes);
		char[] data = this.description.toCharArray();
		handler.characters(data, 0, data.length);
		handler.endElement(namespace, local, qName);
	}

	private final static QName URI_QNAME = new QName("uri");
	private final static QName VERSION_QNAME = new QName("version");

}
