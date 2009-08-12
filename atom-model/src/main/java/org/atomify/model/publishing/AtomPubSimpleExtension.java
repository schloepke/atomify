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

import javax.xml.namespace.QName;

/**
 * A simple extension has no attributes and only a text content.
 * 
 * @author Stephan Schloepke
 */
public class AtomPubSimpleExtension extends AtomPubExtension {
	/**
	 * <b>Required:</b> text content of the simple extension.
	 */
	private final String value;

	/**
	 * Creates a simple extension with the name and content.
	 * 
	 * @param extensionName The name of the extension (must not be null).
	 * @param value The content of the extension.
	 */
	public AtomPubSimpleExtension(final QName extensionName, final String value) {
		super(extensionName);
		this.value = value;
	}

	/**
	 * Returns the text value of the simple extension.
	 * 
	 * @return The text value of the simple extension.
	 */
	public String getValue() {
		return this.value;
	}
	
	@Override
	public boolean isStructured() {
		return false;
	}

}
