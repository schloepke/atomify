/**
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * A complex and structured atom extension. Must not be of the atom name space. Must at least contain one attribute or any sub element.
 * 
 * @author Stephan Schloepke
 */
public class AtomStructuredExtension extends AtomExtension {
	/**
	 * <b>Optional:</b> The attributes of the extension element.
	 */
	private Map<QName, String> attributes;
	/**
	 * <b>Optional:</b> the mixed content.
	 */
	private List<Object> mixedContent;

	/**
	 * Creates a structure extension with the given name.
	 * 
	 * @param extensionName The name of the extension (must not be null).
	 */
	public AtomStructuredExtension(final QName extensionName) {
		super(extensionName);
	}

	/**
	 * Returns the lazy initialized attributes.
	 * 
	 * @return The attribute map (cannot be null).
	 */
	public Map<QName, String> getAttributes() {
		if (this.attributes == null) {
			this.attributes = new HashMap<QName, String>();
		}
		return this.attributes;
	}

	/**
	 * Returns the lazy initialized content of this structured extension.
	 * 
	 * @return The content list (cannot be null).
	 */
	public List<Object> getMixedContent() {
		if (this.mixedContent == null) {
			this.mixedContent = new ArrayList<Object>();
		}
		return this.mixedContent;
	}
}
