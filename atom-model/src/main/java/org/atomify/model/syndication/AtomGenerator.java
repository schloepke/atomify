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

/**
 * Describes a atom generator field.
 * 
 * @author Stephan Schloepke
 */
public class AtomGenerator extends AtomCommonAttributes {
	/**
	 * <b>Optional:</b> uri attribute.
	 * <p>
	 * TODO: Must be an IRI reference.
	 * </p>
	 */
	private URI uri;
	/**
	 * <b>Optional:</b> version attribute.
	 */
	private String version;
	/**
	 * <b>Required:</b> The human readable name of the generator as text content.
	 */
	private String value;

	/**
	 * Creates a generator element with the specified text as value.
	 * 
	 * @param value The text as value.
	 */
	public AtomGenerator(final String value) {
		this.value = value;
	}

	/**
	 * Set the generators URI.
	 * 
	 * @param uri The URI of the generator.
	 */
	public void setUri(final URI uri) {
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
	 * Set the version of the generator.
	 * 
	 * @param version the version to set
	 */
	public void setVersion(final String version) {
		this.version = version;
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
	 * Sets the text value of the generator.
	 * 
	 * @param value the value to set
	 */
	public void setValue(final String value) {
		this.value = value;
	}

	/**
	 * Returns tha text value of the generator.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

}
