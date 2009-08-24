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

import java.io.Serializable;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.jbasics.xml.types.XmlSpaceType;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Atom common attributes construct.
 * <p>
 * Since the Atom Publishing Protocol specification also adds the xml:space element as common
 * attribute it is added here also to the Atom Syndication Format. The main problem is that the
 * specifications vary in this point but that Atom allows the xml:space anyway as foreign undefined
 * attribute.
 * </p>
 * 
 * @author stephan
 */
public abstract class AtomCommonAttributes implements Serializable {

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
	private XmlSpaceType xmlSpace;
	/**
	 * <b>Optional</b> any other attribute which is NOT local:*.
	 */
	private Map<QName, String> undefinedAttributes;

	/**
	 * Returns the xml:base element.
	 * 
	 * @return the xmlBase
	 */
	public URI getXmlBase() {
		return this.xmlBase;
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
	 * Returns the xml:space attribute.
	 * 
	 * @return the xmlSpace
	 */
	public XmlSpaceType getXmlSpace() {
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
		return Collections.unmodifiableMap(this.undefinedAttributes);
	}

	protected AtomCommonAttributes() {
		this.undefinedAttributes = Collections.emptyMap();
	}

	/**
	 * Set the xml:base value.
	 * 
	 * @param xmlBase the xmlBase to set
	 */
	protected void setXmlBase(final URI xmlBase) {
		this.xmlBase = xmlBase;
	}

	/**
	 * Set xml:lang element.
	 * 
	 * @param xmlLang the xmlLang to set
	 */
	protected void setXmlLang(final AtomLanguage xmlLang) {
		this.xmlLang = xmlLang;
	}

	/**
	 * Set the xml:space attribute.
	 * 
	 * @param xmlSpace the xmlSpace to set
	 */
	protected void setXmlSpace(final XmlSpaceType xmlSpace) {
		this.xmlSpace = xmlSpace;
	}

	/**
	 * Set an undefined attribute
	 * 
	 * @param name The name of the attribute
	 * @param value The value o the attribute
	 */
	protected void setUndefinedAttributes(Map<QName, String> attributes) {
		if (attributes == null || attributes.isEmpty()) {
			this.undefinedAttributes = Collections.emptyMap();
		} else {
			this.undefinedAttributes = Collections.unmodifiableMap(new HashMap<QName, String>(attributes));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.undefinedAttributes == null) ? 0 : this.undefinedAttributes.hashCode());
		result = prime * result + ((this.xmlBase == null) ? 0 : this.xmlBase.hashCode());
		result = prime * result + ((this.xmlLang == null) ? 0 : this.xmlLang.hashCode());
		result = prime * result + ((this.xmlSpace == null) ? 0 : this.xmlSpace.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof AtomCommonAttributes)) return false;
		AtomCommonAttributes other = (AtomCommonAttributes) obj;
		if (this.undefinedAttributes == null) {
			if (other.undefinedAttributes != null) return false;
		} else if (!this.undefinedAttributes.equals(other.undefinedAttributes)) return false;
		if (this.xmlBase == null) {
			if (other.xmlBase != null) return false;
		} else if (!this.xmlBase.equals(other.xmlBase)) return false;
		if (this.xmlLang == null) {
			if (other.xmlLang != null) return false;
		} else if (!this.xmlLang.equals(other.xmlLang)) return false;
		if (this.xmlSpace == null) {
			if (other.xmlSpace != null) return false;
		} else if (!this.xmlSpace.equals(other.xmlSpace)) return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AtomCommonAttributes [undefinedAttributes=").append(this.undefinedAttributes).append(
				", xmlBase=").append(this.xmlBase).append(", xmlLang=").append(this.xmlLang).append(", xmlSpace=")
				.append(this.xmlSpace).append("]");
		return builder.toString();
	}
	
	// --- From here all is serialization. We Still need to think about a good way to do so.

	protected AttributesImpl initCommonAttributes(AttributesImpl attributes)
			throws SAXException {
		if (attributes == null) {
			attributes = new AttributesImpl();
		} else {
			attributes.clear();
		}
		if (this.xmlBase != null) {
			addAttribute(attributes, XML_BASE, this.xmlBase.toASCIIString());
		}
		if (this.xmlLang != null) {
			addAttribute(attributes, XML_LANG, this.xmlLang.getLanguage());
		}
		if (this.xmlSpace != null) {
			addAttribute(attributes, XML_SPACE, this.xmlSpace.toXmlString());
		}
		for (Map.Entry<QName, String> attr : this.undefinedAttributes.entrySet()) {
			addAttribute(attributes, attr.getKey(), attr.getValue());
		}
		return attributes;
	}

	protected static void addAttribute(AttributesImpl attributes, QName name, String value) throws SAXException {
		if (value != null) {
			attributes.addAttribute(name.getNamespaceURI(), name.getLocalPart(), (name.getPrefix() != null && name.getPrefix().length() > 0 ? name
					.getPrefix()
					+ ":" : "")
					+ name.getLocalPart(), "CDATA", value.toString());
		}
	}

	private final static QName XML_BASE = new QName(XMLConstants.XML_NS_URI, "base", XMLConstants.XML_NS_PREFIX);
	private final static QName XML_LANG = new QName(XMLConstants.XML_NS_URI, "lang", XMLConstants.XML_NS_PREFIX);
	private final static QName XML_SPACE = new QName(XMLConstants.XML_NS_URI, "space", XMLConstants.XML_NS_PREFIX);

}
