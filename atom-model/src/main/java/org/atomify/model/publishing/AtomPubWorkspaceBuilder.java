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

import java.util.ArrayList;
import java.util.List;

import org.atomify.model.AtomCommonBuilder;
import org.atomify.model.AtomConstants;
import org.atomify.model.syndication.AtomExtension;
import org.atomify.model.syndication.AtomText;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;

public class AtomPubWorkspaceBuilder extends AtomCommonBuilder<AtomPubWorkspaceBuilder> implements
		Builder<AtomPubWorkspace> {
	private AtomText title;
	private List<AtomPubCollection> collections;
	private List<AtomExtension> extensions;

	public static AtomPubWorkspaceBuilder newInstance() {
		return new AtomPubWorkspaceBuilder();
	}

	private AtomPubWorkspaceBuilder() {
		// Empty to avoid instantiation
	}

	public AtomPubWorkspace build() {
		AtomPubWorkspace result = new AtomPubWorkspace(this.title);
		attachCommonAttributes(result);
		if (this.collections != null && this.collections.size() > 0) {
			result.getCollections().addAll(this.collections);
		}
		if (this.extensions != null && this.extensions.size() > 0) {
			result.getExtensions().addAll(this.extensions);
		}
		return result;
	}

	public void reset() {
		// TODO: Implement
		throw new UnsupportedOperationException();
	}

	@Element(name = "title", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1)
	public AtomPubWorkspaceBuilder setTitle(AtomText title) {
		this.title = title;
		return this;
	}

	@Element(name = "collection", namespace = AtomConstants.ATOM_PUB_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomPubWorkspaceBuilder addCollection(AtomPubCollection collection) {
		if (this.collections == null) {
			this.collections = new ArrayList<AtomPubCollection>();
		}
		this.collections.add(collection);
		return this;
	}

//	@AnyElement
	public AtomPubWorkspaceBuilder addExtension(AtomExtension extension) {
		if (this.extensions == null) {
			this.extensions = new ArrayList<AtomExtension>();
		}
		this.extensions.add(extension);

		return this;
	}

}
