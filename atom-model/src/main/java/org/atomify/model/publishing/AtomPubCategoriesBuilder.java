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
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonBuilder;
import org.atomify.model.extension.AtomForeignComment;
import org.atomify.model.extension.AtomForeignMarkup;
import org.atomify.model.extension.AtomForeignTextContent;
import org.atomify.model.syndication.AtomCategory;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Attribute;
import org.jbasics.parser.annotations.Comment;
import org.jbasics.parser.annotations.Content;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;
import org.jbasics.xml.types.XmlBooleanYesNoType;

public class AtomPubCategoriesBuilder extends AtomCommonBuilder<AtomPubCategoriesBuilder> implements Builder<AtomPubCategories> {

	private URI href;
	private XmlBooleanYesNoType fixed;
	private URI scheme;
	private List<AtomCategory> categories;
	private List<AtomForeignMarkup> undefinedContent;

	public static AtomPubCategoriesBuilder newInstance() {
		return new AtomPubCategoriesBuilder();
	}

	private AtomPubCategoriesBuilder() {
		// To block instantiation
	}

	public AtomPubCategories build() {
		if (this.href != null) {
			return new AtomPubCategories(this.href, this.undefinedContent);
		} else {
			return new AtomPubCategories(this.fixed, this.scheme, this.categories, this.undefinedContent);
		}
	}

	@Attribute(name = "href", required = false)
	public AtomPubCategoriesBuilder setOutOfLineCategories(URI href) {
		this.href = href;
		return this;
	}

	public AtomPubCategoriesBuilder setFixed(boolean fixed) {
		this.href = null;
		this.fixed = fixed ? XmlBooleanYesNoType.YES : XmlBooleanYesNoType.NO;
		return this;
	}

	@Attribute(name = "fixed", required = false)
	public AtomPubCategoriesBuilder setFixed(XmlBooleanYesNoType fixed) {
		this.href = null;
		this.fixed = fixed;
		return this;
	}

	@Attribute(name = "scheme", required = false)
	public AtomPubCategoriesBuilder setScheme(URI scheme) {
		this.href = null;
		this.scheme = scheme;
		return this;
	}

	@Element(name = "category", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomPubCategoriesBuilder addCategory(AtomCategory category) {
		this.href = null;
		if (this.categories == null) {
			this.categories = new ArrayList<AtomCategory>();
		}
		this.categories.add(AtomContractConstraint.notNull("category", category));
		return this;
	}

	public AtomPubCategoriesBuilder addCategory(String term, URI scheme, String label) {
		return addCategory(AtomCategory.newBuilder().setTerm(term).setScheme(scheme).setLabel(label).build());
	}

	@Content
	public AtomPubCategoriesBuilder appendText(String text) {
		// TODO: We need to figure out how we handle the extra content here. currently we just don't allow empty string
		// extra content since that would come between the elements where no empty strings are allowed.
		if (AtomContractConstraint.mustNotBeEmptyString(text, "text").trim().length() > 0) {
			getUndefinedContent().add(new AtomForeignTextContent(text));
		}
		return this;
	}

	@Comment
	public AtomPubCategoriesBuilder appendComment(String comment) {
		// Comments can come out of line and will appear in the wrong position since it is
		// always possible to add comments between atom elements. Since we have no guarantee
		// that the appearance of the document stays the same comments between the
		// atom elements will be printed after all atom elements.
		getUndefinedContent().add(new AtomForeignComment(AtomContractConstraint.notNull("comment", comment)));
		return this;
	}

	@AnyElement
	public AtomPubCategoriesBuilder appendAnyElement(AtomForeignMarkup element) {
		getUndefinedContent().add(AtomContractConstraint.notNull("element", element));
		return this;
	}

	public List<AtomForeignMarkup> getUndefinedContent() {
		if (this.undefinedContent == null) {
			this.undefinedContent = new ArrayList<AtomForeignMarkup>();
		}
		return this.undefinedContent;
	}

}
