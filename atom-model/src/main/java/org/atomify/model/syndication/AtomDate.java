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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonAttributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Atom date construct.
 * 
 * @author Stephan Schloepke
 */
public class AtomDate extends AtomCommonAttributes {
	/**
	 * <b>Required:</b> the xsd:date content.
	 */
	private final XMLGregorianCalendar value;

	public static AtomDateBuilder newBuilder() {
		return AtomDateBuilder.newInstance();
	}

	/**
	 * Creates a date with the given date content.
	 * 
	 * @param date The date content.
	 */
	private AtomDate(final XMLGregorianCalendar date) {
		this.value = AtomContractConstraint.notNull("date", date);
	}

	/**
	 * Returns the date value.
	 * 
	 * @return The date value
	 */
	public XMLGregorianCalendar getValue() {
		return this.value;
	}

	/**
	 * Returns the date value as a {@link Date} instance.
	 * 
	 * @return The date value as date instance.
	 */
	public Date toDate() {
		return this.value.toGregorianCalendar().getTime();
	}

	public static AtomDate valueOf(String dateValue) {
		try {
			return new AtomDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(AtomContractConstraint.notNull("dateValue", dateValue)));
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Creates a date construct with the given java date.
	 * 
	 * @param date The java date.
	 */
	public static AtomDate valueOf(final Date date) {
		try {
			GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
			cal.setTime(date);
			return new AtomDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof AtomDate)) {
			return false;
		}
		AtomDate other = (AtomDate) obj;
		if (this.value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!this.value.equals(other.value)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder().append("AtomDate [value=").append(this.value).append(", ").append(super.toString()).append("]").toString();
	}

	// FIXME: Write a much better way of serialization

	@SuppressWarnings("all")
	public void serialize(QName name, ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		String namespace = name.getNamespaceURI();
		String local = name.getLocalPart();
		String qName = (name.getPrefix() != null && name.getPrefix().length() > 0 ? name.getPrefix() + ":" : "") + local;
		handler.startElement(namespace, local, qName, attributes);
		char[] data = this.value.toXMLFormat().toCharArray();
		handler.characters(data, 0, data.length);
		handler.endElement(namespace, local, qName);
	}

}
