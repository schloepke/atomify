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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.atomify.model.AtomConstants;
import org.atomify.model.common.AtomCommonBuilder;
import org.atomify.model.extension.AtomExtension;
import org.atomify.model.syndication.AtomText;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Attribute;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;

public class AtomPubCollectionBuilder extends AtomCommonBuilder<AtomPubWorkspaceBuilder> implements
		Builder<AtomPubCollection> {

	/**
	 * <b>Required:</b> the title element.
	 */
	private AtomText title;
	/**
	 * <b>Required:</b> The href of the collection
	 */
	private URI href;
	/**
	 * <b>Optional:</b> The optional accept fields. If empty the default accept of atom:entry is in
	 * place.
	 */
	private List<AtomPubAccept> accepts;
	/**
	 * <b>Optional:</b> The categories
	 */
	private List<AtomPubCategories> categories;
	/**
	 * <b>Optional:</b> extension elements (all non AtomPub namespace and not atom:title)
	 */
	private List<AtomExtension> extensions;

	public static AtomPubCollectionBuilder newInstance() {
		return new AtomPubCollectionBuilder();
	}

	private AtomPubCollectionBuilder() {
		// dissallow instantiation
	}

	public AtomPubCollection build() {
		AtomPubCollection temp = new AtomPubCollection(this.title, this.href, this.accepts, this.categories,
				this.extensions);
		attachCommonAttributes(temp);
		return temp;
	}

	@Attribute(name = "href", required = true)
	public AtomPubCollectionBuilder setHref(URI href) {
		this.href = href;
		return this;
	}

	@Element(name = "categories", namespace = AtomConstants.ATOM_PUB_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomPubCollectionBuilder addCategories(AtomPubCategories categories) {
		if (this.categories == null) {
			this.categories = new ArrayList<AtomPubCategories>();
		}
		this.categories.add(categories);
		return this;
	}

	@Element(name = "accept", namespace = AtomConstants.ATOM_PUB_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomPubCollectionBuilder addAccept(AtomPubAccept accept) {
		if (this.accepts == null) {
			this.accepts = new ArrayList<AtomPubAccept>();
		}
		this.accepts.add(accept);
		return this;
	}

	@Element(name = "title", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1)
	public AtomPubCollectionBuilder setTitle(AtomText title) {
		this.title = title;
		return this;
	}

	@AnyElement
	public AtomPubCollectionBuilder addExtension(AtomExtension extension) {
		if (this.extensions == null) {
			this.extensions = new ArrayList<AtomExtension>();
		}
		this.extensions.add(extension);
		return this;
	}

}
