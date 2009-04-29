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

import javax.xml.namespace.QName;

/**
 * Atom common attributes construct.
 * <p>
 * Since the Atom Publishing Protocol specification also adds the xml:space element as common attribute it is added here also to the Atom Syndication
 * Format. The main problem is that the specifications vary in this point but that Atom allows the xml:space anyway as foreign undefined attribute.
 * </p>
 * 
 * @author stephan
 */
public abstract class AtomCommonAttributes {
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

	/**
	 * xml:space enum type.
	 * 
	 * @author Stephan Schloepke
	 */
	public static enum XmlSpace {
		/**
		 * Default behavior of XML space.
		 */
		DEFAULT,
		/**
		 * Preserve the spaces in the element.
		 */
		PRESERVED;
	}

	/**
	 * Set the xml:base value.
	 * 
	 * @param xmlBase the xmlBase to set
	 */
	public void setXmlBase(final URI xmlBase) {
		this.xmlBase = xmlBase;
	}

	/**
	 * Returns the xml:base element.
	 * 
	 * @return the xmlBase
	 */
	public URI getXmlBase() {
		return this.xmlBase;
	}

	/**
	 * Set xml:lang element.
	 * 
	 * @param xmlLang the xmlLang to set
	 */
	public void setXmlLang(final AtomLanguage xmlLang) {
		this.xmlLang = xmlLang;
	}

	/**
	 * Returns the xml:lang value.
	 * 
	 * @return the xmlLang
	 */
	public AtomLanguage getXmlLang() {
		return this.xmlLang;
	}

	/**
	 * Set the xml:space attribute.
	 * 
	 * @param xmlSpace the xmlSpace to set
	 */
	public void setXmlSpace(final XmlSpace xmlSpace) {
		this.xmlSpace = xmlSpace;
	}

	/**
	 * Returns the xml:space attribute.
	 * 
	 * @return the xmlSpace
	 */
	public XmlSpace getXmlSpace() {
		return this.xmlSpace;
	}

	/**
	 * Returns the undefined attributes (lazy initialized).
	 * 
	 * @return the undefinedAttributes
	 */
	public Map<QName, String> getUndefinedAttributes() {
		if (this.undefinedAttributes == null) {
			this.undefinedAttributes = new HashMap<QName, String>();
		}
		return this.undefinedAttributes;
	}

	public void putUndefinedAttribute(QName name, String value) {
		getUndefinedAttributes().put(name, value);
	}

}
