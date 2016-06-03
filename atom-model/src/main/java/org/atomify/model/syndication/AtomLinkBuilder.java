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
import org.atomify.model.common.AtomLanguage;
import org.atomify.model.extension.AtomForeignComment;
import org.atomify.model.extension.AtomForeignMarkup;
import org.atomify.model.extension.AtomForeignTextContent;
import org.jbasics.net.mediatype.MediaType;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Attribute;
import org.jbasics.parser.annotations.Comment;
import org.jbasics.parser.annotations.Content;
import org.jbasics.pattern.builder.Builder;

public class AtomLinkBuilder extends AtomCommonBuilder<AtomLinkBuilder> implements Builder<AtomLink> {
	private URI href; // REQUIRED
	private URI rel; // AtomNCName or AtomURI
	private MediaType type;
	private AtomLanguage hreflang;
	private String title;
	private Integer length;
	private List<AtomForeignMarkup> undefinedContent;

	public static AtomLinkBuilder newInstance() {
		return new AtomLinkBuilder();
	}

	private AtomLinkBuilder() {
		// disallow instantiation
	}

	public AtomLink build() {
		return attachParentBuilder(new AtomLink(this.title, this.href, this.rel, this.type, this.hreflang,
				this.length, this.undefinedContent));
	}

	@Override
	public void reset() {
		super.reset();
		this.href = null;
		this.rel = null;
		this.type = null;
		this.hreflang = null;
		this.title = null;
		this.length = null;
		if (this.undefinedContent != null) {
			this.undefinedContent.clear();
		}
	}

	@Attribute(name = "href", required = true)
	public AtomLinkBuilder setHref(URI href) {
		this.href = href;
		return this;
	}

	@Attribute(name = "rel")
	public AtomLinkBuilder setRel(URI rel) {
		this.rel = rel;
		return this;
	}

	@Attribute(name = "type")
	public AtomLinkBuilder setType(MediaType type) {
		this.type = type;
		return this;
	}

	@Attribute(name = "hreflang")
	public AtomLinkBuilder setHreflang(AtomLanguage hreflang) {
		this.hreflang = hreflang;
		return this;
	}

	@Attribute(name = "title")
	public AtomLinkBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	@Attribute(name = "length")
	public AtomLinkBuilder setLength(Integer length) {
		this.length = length;
		return this;
	}

	@Content
	public AtomLinkBuilder appendUndefinedContent(String text) {
		return appendUndefinedContent(new AtomForeignTextContent(AtomContractConstraint.notNull("text", text)));
	}

	@Comment
	public AtomLinkBuilder appendComment(String comment) {
		return appendUndefinedContent(new AtomForeignComment(AtomContractConstraint.notNull("comment", comment)));
	}

	@AnyElement
	public AtomLinkBuilder appendUndefinedContent(AtomForeignMarkup undefinedContent) {
		if (this.undefinedContent == null) {
			this.undefinedContent = new ArrayList<AtomForeignMarkup>();
		}
		this.undefinedContent.add(AtomContractConstraint.notNull("undefinedContent", undefinedContent));
		return this;
	}

}
