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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.atomify.model.AtomContractConstraint;

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

	/**
	 * Creates a date with the given date content.
	 * 
	 * @param date The date content.
	 */
	public AtomDate(final XMLGregorianCalendar date) {
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
			return new AtomDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(
					AtomContractConstraint.notNull("dateValue", dateValue)));
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

}
