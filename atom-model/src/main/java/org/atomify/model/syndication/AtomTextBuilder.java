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

import org.atomify.model.common.AtomCommonBuilder;
import org.atomify.model.common.XhtmlDivElement;
import org.atomify.model.syndication.AtomText.Type;
import org.jbasics.parser.annotations.Attribute;
import org.jbasics.parser.annotations.Content;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;

public class AtomTextBuilder extends AtomCommonBuilder<AtomTextBuilder> implements Builder<AtomText> {
	private AtomText.Type type;
	private String textOrHtmlContent;
	private XhtmlDivElement xhtmlContent;

	public static AtomTextBuilder newInstance() {
		return new AtomTextBuilder();
	}

	private AtomTextBuilder() {
		// disallow instantiation
	}

	public AtomText build() {
		AtomText temp = null;
		if (this.type == Type.XHTML) {
			temp = new AtomXHtmlText(this.xhtmlContent);
		} else {
			temp = new AtomPlainText(this.type == Type.HTML, this.textOrHtmlContent);
		}
		attachCommonAttributes(temp);
		return temp;
	}

	@Override
	public void reset() {
		super.reset();
		this.type = null;
		this.textOrHtmlContent = null;
	}

	@Attribute(namespace = "", name = "type", required = true)
	public AtomTextBuilder setType(AtomText.Type type) {
		this.type = type;
		return this;
	}

	@Content(mixed = false)
	public AtomTextBuilder setContent(String content) {
		if (this.type == Type.XHTML && content != null && content.trim().length() == 0) {
			return this;
		}
		if (this.type == Type.XHTML) {
			throw new IllegalStateException("Cannot add HTML or plain text content to " + this.type);
		}
		this.textOrHtmlContent = content;
		return this;
	}

	public AtomTextBuilder setHtmlContent(String content) {
		this.textOrHtmlContent = content;
		if (this.type == null) {
			this.type = Type.HTML;
		} else if (this.type != Type.HTML) {
			throw new IllegalStateException("Cannot add HTML content to a type " + this.type);
		}
		return this;
	}

	public AtomTextBuilder setTextContent(String content) {
		this.textOrHtmlContent = content;
		if (this.type != null && this.type != Type.TEXT) {
			throw new IllegalStateException("Cannot add plain text content to a type " + this.type);
		}
		return this;
	}

	@Element(name = "div", namespace = "http://www.w3.org/1999/xhtml", minOccurs = 0, maxOccurs = 1)
	public AtomTextBuilder setXhtmlContent(XhtmlDivElement content) {
		this.xhtmlContent = content;
		if (this.type == null) {
			this.type = Type.XHTML;
		} else if (this.type != Type.XHTML) {
			throw new IllegalStateException("Cannot add XHTML content to a type " + this.type);
		}
		return this;
	}

}
