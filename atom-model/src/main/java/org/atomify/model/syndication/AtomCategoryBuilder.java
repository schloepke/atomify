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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.atomify.model.AtomCommonBuilder;
import org.jbasics.pattern.builder.Builder;

public class AtomCategoryBuilder extends AtomCommonBuilder<AtomCategoryBuilder>
		implements Builder<AtomCategory> {
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
	 * <b>Optional:</b> mixed content (text|element). Any direct element must
	 * not be of the atom name space.
	 */
	private List<Object> undefinedContent;

	public static AtomCategoryBuilder newInstance() {
		return new AtomCategoryBuilder();
	}

	public AtomCategory build() {
		AtomCategory result = new AtomCategory(this.term, this.scheme,
				this.label);
		attachCommonAttributes(result);
		if (this.undefinedContent != null && this.undefinedContent.size() > 0) {
			result.getUndefinedContent().addAll(this.undefinedContent);
		}
		return result;
	}

	private AtomCategoryBuilder() {
		// Avoid instantiation
	}

	public AtomCategoryBuilder setTerm(String term) {
		this.term = term;
		return this;
	}

	public AtomCategoryBuilder setScheme(URI scheme) {
		this.scheme = scheme;
		return this;
	}

	public AtomCategoryBuilder setLabel(String label) {
		this.label = label;
		return this;
	}

	public AtomCategoryBuilder addUndefinedContent(Object content) {
		if (this.undefinedContent == null) {
			this.undefinedContent = new ArrayList<Object>();
		}
		this.undefinedContent.add(content);
		return this;
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
}
