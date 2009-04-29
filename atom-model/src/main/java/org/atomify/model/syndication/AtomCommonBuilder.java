/**
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
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.atomify.model.syndication.AtomCommonAttributes.XmlSpace;
import org.jbasics.parser.annotations.AnyAttribute;
import org.jbasics.parser.annotations.Attribute;

public abstract class AtomCommonBuilder<T extends AtomCommonBuilder<?>> {
	/**
	 * <b>Optional:</b> xml:base attribute.
	 */
	private URI xmlBase;
	/**
	 * <b>Optional:</b> xml:lang attribute.
	 */
	private AtomLanguage xmlLang;
	/**
	 * <b>Optional:</b> xml:space attribute.
	 */
	private XmlSpace xmlSpace;
	/**
	 * <b>Optional</b> any other attribute which is NOT local:*.
	 */
	private Map<QName, String> undefinedAttributes;

	@SuppressWarnings("unchecked")
	@Attribute(name = "base", namespace = XMLConstants.XML_NS_URI)
	public final T setXmlBase(URI xmlBase) {
		this.xmlBase = xmlBase;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	@Attribute(name = "base", namespace = XMLConstants.XML_NS_URI)
	public final T setXmlLang(AtomLanguage xmlLang) {
		this.xmlLang = xmlLang;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	@Attribute(name = "base", namespace = XMLConstants.XML_NS_URI)
	public final T setXmlSpace(XmlSpace xmlSpace) {
		this.xmlSpace = xmlSpace;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	@AnyAttribute
	public final T addUndefinedAttribute(QName name, String value) {
		if (this.undefinedAttributes == null) {
			this.undefinedAttributes = new HashMap<QName, String>();
		}
		this.undefinedAttributes.put(name, value);
		return (T) this;
	}

	protected final void attachCommonAttributes(AtomCommonAttributes instance) {
		instance.setXmlBase(this.xmlBase);
		instance.setXmlLang(this.xmlLang);
		instance.setXmlSpace(this.xmlSpace);
		if (this.undefinedAttributes != null
				&& this.undefinedAttributes.size() > 0) {
			instance.getUndefinedAttributes().putAll(this.undefinedAttributes);
		}
	}

	public void reset() {
		this.xmlBase = null;
		this.xmlLang = null;
		this.xmlSpace = null;
		if (this.undefinedAttributes != null) {
			this.undefinedAttributes.clear();
		}
	}

}
