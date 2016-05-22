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
import java.util.List;

import org.atomify.model.AtomConstants;
import org.atomify.model.common.AtomCommonBuilder;
import org.atomify.model.extension.AtomExtension;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;

public class AtomPersonBuilder extends AtomCommonBuilder<AtomPersonBuilder> implements Builder<AtomPerson> {
	/**
	 * <b>Required:</b> atom:name element.
	 */
	private String name;
	/**
	 * <b>Optional:</b> atom:uri element.
	 * <p>
	 * TODO: Needs to be an IRI not an URI.
	 * </p>
	 */
	private URI uri;
	/**
	 * <b>Optional:</b> atom:email element.
	 */
	private String email;
	/**
	 * <b>Optional:</b> any number of atom extension elements.
	 */
	private List<AtomExtension> extensions;

	public static AtomPersonBuilder newInstance() {
		return new AtomPersonBuilder();
	}
	
	private AtomPersonBuilder() {
		// disallow instantiation
	}
	
	public AtomPerson build() {
		AtomPerson temp = new AtomPerson(this.name, this.email, this.uri, this.extensions);
		attachCommonAttributes(temp);
		return temp;
	}

	@Override
	public void reset() {
		super.reset();
		this.name = null;
		this.email = null;
		this.uri = null;
		if (this.extensions != null) {
			this.extensions.clear();
		}
	}

	@Element(name = "name", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	public AtomPersonBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	@Element(name = "email", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = 1)
	public AtomPersonBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	
	@Element(name = "uri", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	public AtomPersonBuilder setUri(URI uri) {
		this.uri = uri;
		return this;
	}

}
