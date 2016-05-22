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
import org.jbasics.parser.annotations.Content;
import org.jbasics.pattern.builder.Builder;

public class AtomLogoBuilder extends AtomCommonBuilder<AtomLogoBuilder> implements Builder<AtomLogo> {
	/**
	 * <b>Required:</b> IRI content.
	 * <p>
	 * TODO: must be an IRI reference.
	 * </p>
	 */
	private URI uri;

	public static AtomLogoBuilder newInstance() {
		return new AtomLogoBuilder();
	}

	private AtomLogoBuilder() {
		// disallow instantiation
	}

	public AtomLogo build() {
		return attachCommonAttributes(new AtomLogo(this.uri));
	}

	@Override
	public void reset() {
		super.reset();
		this.uri = null;
	}

	public AtomLogoBuilder setUri(URI uri) {
		this.uri = uri;
		return this;
	}

	@Content
	public AtomLogoBuilder setUri(String uri) {
		return setUri(uri == null ? null : URI.create(uri));
	}

}
