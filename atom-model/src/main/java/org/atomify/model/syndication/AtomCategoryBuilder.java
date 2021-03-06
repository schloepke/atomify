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
import java.util.ArrayList;
import java.util.List;

import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonBuilder;
import org.atomify.model.extension.AtomForeignComment;
import org.atomify.model.extension.AtomForeignMarkup;
import org.atomify.model.extension.AtomForeignTextContent;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Attribute;
import org.jbasics.parser.annotations.Comment;
import org.jbasics.parser.annotations.Content;
import org.jbasics.pattern.builder.Builder;

public class AtomCategoryBuilder extends AtomCommonBuilder<AtomCategoryBuilder> implements Builder<AtomCategory> {
	/**
	 * <b>Required:</b> term attribute.
	 */
	private String term;
	/**
	 * <b>Optional:</b> scheme attribute.
	 * <p>
	 * TODO: must be an IRI reference instead of an URI reference.
	 * </p>
	 */
	private URI scheme;
	/**
	 * <b>Optional:</b> label attribute.
	 */
	private String label;
	/**
	 * <b>Optional:</b> mixed content (text|element). Any direct element must not be of the atom
	 * name space.
	 */
	private List<AtomForeignMarkup> undefinedContent;

	public static AtomCategoryBuilder newInstance() {
		return new AtomCategoryBuilder();
	}

	private AtomCategoryBuilder() {
		// Avoid instantiation
	}

	public AtomCategory build() {
		AtomCategory result = new AtomCategory(this.term, this.scheme, this.label, this.undefinedContent);
		attachParentBuilder(result);
		return result;
	}

	public void reset() {
		super.reset();
		this.label = null;
		this.scheme = null;
		this.term = null;
		if (this.undefinedContent != null) {
			this.undefinedContent.clear();
		}
	}

	@Attribute(name = "term", namespace = "", required = true)
	public AtomCategoryBuilder setTerm(String term) {
		this.term = term;
		return this;
	}

	@Attribute(name = "scheme", namespace = "", required = false)
	public AtomCategoryBuilder setScheme(URI scheme) {
		this.scheme = scheme;
		return this;
	}

	@Attribute(name = "label", namespace = "", required = false)
	public AtomCategoryBuilder setLabel(String label) {
		this.label = label;
		return this;
	}

	@Content
	public AtomCategoryBuilder appendUndefinedText(String text) {
		getUndefinedContent().add(new AtomForeignTextContent(AtomContractConstraint.mustNotBeEmptyString(text, "text")));
		return this;
	}

	@Comment
	public AtomCategoryBuilder appendComment(String comment) {
		getUndefinedContent().add(new AtomForeignComment(AtomContractConstraint.notNull("comment", comment)));
		return this;
	}

	@AnyElement
	public AtomCategoryBuilder appendUndefinedElement(AtomForeignMarkup element) {
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
