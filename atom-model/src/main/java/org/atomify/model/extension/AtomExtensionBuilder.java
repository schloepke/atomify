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
package org.atomify.model.extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.atomify.model.AtomContractConstraint;
import org.jbasics.parser.annotations.AnyAttribute;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Content;
import org.jbasics.parser.annotations.QualifiedName;
import org.jbasics.pattern.builder.Builder;

public class AtomExtensionBuilder implements Builder<AtomExtension> {
	private QName qualifiedName;
	private Map<QName, String> attributes;
	private List<AtomForeignMarkup> childrean;

	public static AtomExtensionBuilder newInstance() {
		return new AtomExtensionBuilder();
	}

	private AtomExtensionBuilder() {
		// disallow instantiation
	}

	public AtomExtension build() {
		if ((this.childrean == null || this.childrean.isEmpty() || (this.childrean.size() == 1 && this.childrean.get(0)
				.isSimpleContent()))
				&& (this.attributes == null || this.attributes.isEmpty())) {
			return new AtomSimpleExtension(this.qualifiedName,
					this.childrean == null || this.childrean.isEmpty() ? null : this.childrean.get(0).getSimpleContent());
		} else {
			return new AtomStructuredExtension(this.qualifiedName, this.attributes, this.childrean);
		}
	}

	public void reset() {
		this.qualifiedName = null;
		if (this.attributes != null) {
			this.attributes.clear();
		}
		if (this.childrean != null) {
			this.childrean.clear();
		}
	}

	public List<AtomForeignMarkup> getChildrean() {
		if (this.childrean == null) {
			this.childrean = new ArrayList<AtomForeignMarkup>();
		}
		return this.childrean;
	}

	public Map<QName, String> getAttributes() {
		if (this.attributes == null) {
			this.attributes = new HashMap<QName, String>();
		}
		return this.attributes;
	}

	@QualifiedName
	public AtomExtensionBuilder setQualifiedName(QName qualifiedName) {
		this.qualifiedName = qualifiedName;
		return this;
	}

	@AnyAttribute
	public AtomExtensionBuilder setAttribute(QName name, String value) {
		getAttributes().put(name, value);
		return this;
	}

	@Content
	public AtomExtensionBuilder appendText(String text) {
		getChildrean().add(new AtomForeignTextContent(AtomContractConstraint.mustNotBeEmptyString(text, "text")));
		return this;
	}

	@AnyElement
	public AtomExtensionBuilder appendAnyElement(AtomForeignMarkup element) {
		getChildrean().add(AtomContractConstraint.notNull("element", element));
		return this;
	}
}
