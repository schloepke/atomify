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
package org.atomify.model.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.atomify.model.AtomContractConstraint;

public class UndefinedElement implements Iterable<Object> {
	private final QName qualifiedName;
	private final Map<QName, String> attributes;
	private final List<Object> childrean;
	
	public static UndefinedElementBuilder newBuilder() {
		return UndefinedElementBuilder.newInstance();
	}

	public UndefinedElement(QName name, Map<QName, String> attributes, List<Object> childrean) {
		this.qualifiedName = AtomContractConstraint.notNull("name", name);
		if (attributes != null && attributes.size() > 0) {
			this.attributes = Collections.unmodifiableMap(new HashMap<QName, String>(attributes));
		} else {
			this.attributes = Collections.emptyMap();
		}
		if (childrean != null && childrean.size() > 0) {
			this.childrean = Collections.unmodifiableList(new ArrayList<Object>(childrean));
		} else {
			this.childrean = Collections.emptyList();
		}
	}

	public QName getQualifiedName() {
		return this.qualifiedName;
	}

	public Map<QName, String> getAttributes() {
		return this.attributes;
	}

	public String getAttributeValue(QName name) {
		return this.attributes.get(AtomContractConstraint.notNull("name", name));
	}

	public List<Object> getChildrean() {
		return this.childrean;
	}

	public Iterator<Object> iterator() {
		return this.childrean.iterator();
	}

}
