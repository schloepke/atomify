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
import org.jbasics.parser.annotations.Attribute;
import org.jbasics.parser.annotations.Content;
import org.jbasics.pattern.builder.Builder;

public class AtomGeneratorBuilder extends AtomCommonBuilder<AtomGeneratorBuilder> implements Builder<AtomGenerator> {
	/**
	 * <b>Required:</b> The human readable name of the generator as text content.
	 */
	private String description;
	/**
	 * <b>Optional:</b> uri attribute.
	 * <p>
	 * TODO: Must be an IRI reference.
	 * </p>
	 */
	private URI uri;
	/**
	 * <b>Optional:</b> version attribute.
	 */
	private String version;

	public static AtomGeneratorBuilder newInstance() {
		return new AtomGeneratorBuilder();
	}

	private AtomGeneratorBuilder() {
		// disallow instantiation
	}

	public AtomGenerator build() {
		return attachCommonAttributes(new AtomGenerator(this.description, this.version, this.uri));
	}

	@Content
	public AtomGeneratorBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	@Attribute(name = "version", required = false)
	public AtomGeneratorBuilder setVersion(String version) {
		this.version = version;
		return this;
	}

	@Attribute(name = "uri", required = false)
	public AtomGeneratorBuilder setUri(URI uri) {
		this.uri = uri;
		return this;
	}

}
