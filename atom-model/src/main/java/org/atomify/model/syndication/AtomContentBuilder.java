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

import org.atomify.model.common.AtomCommonBuilder;
import org.atomify.model.common.XhtmlDivElement;
import org.atomify.model.extension.AtomForeignMarkup;
import org.jbasics.net.mediatype.MediaType;
import org.jbasics.net.mediatype.RFC3023XMLMediaTypes;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Attribute;
import org.jbasics.parser.annotations.Comment;
import org.jbasics.parser.annotations.Content;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;

public class AtomContentBuilder extends AtomCommonBuilder<AtomContentBuilder> implements Builder<AtomContent> {
	private static final MediaType ATOM_TEXT = new MediaType("text", null);
	private static final MediaType ATOM_XHTML = new MediaType("xhtml", null);
	private static final MediaType ATOM_HTML = new MediaType("html", null);
	private MediaType type;
	private URI source;
	private String content;
	private XhtmlDivElement xhtmlContent;
	private AtomForeignMarkup xmlContent;

	public static AtomContentBuilder newInstance() {
		return new AtomContentBuilder();
	}

	private AtomContentBuilder() {
		// disallow instantiation
	}

	public AtomContent build() {
		AtomContent result = null;
		if (this.source != null) {
			result = new AtomContentLink(this.source, this.type);
		} else if (this.type == null || ATOM_TEXT.equals(this.type) || "text".equals(this.type.getType())) {
			result = new AtomContentPlainText(false, this.content);
		} else if (ATOM_HTML.equals(this.type)) {
			result = new AtomContentPlainText(true, this.content);
		} else if (ATOM_XHTML.equals(this.type)) {
			result = new AtomContentXhtml(this.xhtmlContent);
		} else if (RFC3023XMLMediaTypes.isXmlMediaType(this.type)) {
			result = new AtomContentGenericXml(this.type, this.xmlContent);
		} else {
			// This is awkward and pretty much very memory intesiv. We need a more streaming way of
			// actually
			// reading the binary data. Possible by using a direct reader to transform it with a
			// custom parser
			result = new AtomContentBinary(this.type, this.content);
		}
		return attachCommonAttributes(result);
	}

	@Override
	public void reset() {
		super.reset();
	}

	@Attribute(name = "type", required = true)
	public AtomContentBuilder setType(MediaType type) {
		this.type = type;
		return this;
	}

	@Attribute(name = "src", required = false)
	public AtomContentBuilder setSource(URI source) {
		this.source = source;
		return this;
	}

	@Content(mixed = false)
	public AtomContentBuilder setContent(String content) {
		this.content = content;
		return this;
	}

	@Element(name = "div", namespace = "http://www.w3.org/1999/xhtml", minOccurs = 0, maxOccurs = 1)
	public AtomContentBuilder setXhtmlContent(XhtmlDivElement content) {
		this.xhtmlContent = content;
		if (this.type == null) {
			this.type = ATOM_XHTML;
		} else if (!ATOM_XHTML.equals(this.type)) {
			throw new IllegalStateException("Cannot add XHTML content to a type " + this.type);
		}
		return this;
	}

	@AnyElement
	public AtomContentBuilder setGenericXmlContent(AtomForeignMarkup xmlContent) {
		this.xmlContent = xmlContent;
		if (this.type == null) {
			throw new IllegalStateException("Before adding XML content you need to set the Media Type of the content");
		} else if (!RFC3023XMLMediaTypes.isXmlMediaType(this.type)) {
			throw new IllegalStateException("XML content can only be used with an xml media type (subtype +xml) but type is " + this.type);
		}
		return this;
	}

}
