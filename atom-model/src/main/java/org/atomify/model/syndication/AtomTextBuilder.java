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
package org.atomify.model.syndication;

import org.atomify.model.AtomCommonBuilder;
import org.atomify.model.syndication.AtomText.Type;
import org.jbasics.parser.annotations.Attribute;
import org.jbasics.parser.annotations.Content;
import org.jbasics.pattern.builder.Builder;

public class AtomTextBuilder extends AtomCommonBuilder<AtomTextBuilder> implements Builder<AtomText> {
	private AtomText.Type type;
	private String content;

	public static AtomTextBuilder newInstance() {
		return new AtomTextBuilder();
	}

	private AtomTextBuilder() {
		// disallow instantiation
	}

	public AtomText build() {
		if (this.type == Type.xhtml) {
			throw new UnsupportedOperationException("XHtml type is not yet supported");
		}
		AtomText temp = new AtomPlainText(this.type == Type.html, this.content);
		attachCommonAttributes(temp);
		return temp;
	}

	@Override
	public void reset() {
		super.reset();
		this.type = null;
		this.content = null;
	}

	@Attribute(namespace = "", name = "type", required = true)
	public AtomTextBuilder setType(AtomText.Type type) {
		this.type = type;
		return this;
	}

	@Content(mixed = false)
	public AtomTextBuilder setContent(String content) {
		this.content = content;
		return this;
	}

}
