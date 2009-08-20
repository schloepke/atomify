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

import java.util.Locale;
import java.util.regex.Pattern;

import org.atomify.model.AtomContractConstraint;

/**
 * The {@link AtomLanguage} is the representation for any xml:lang attribute in the atom protocols
 * and formats.
 * <p>
 * The xml:lang attribute is correctly defined by the XML specification. Currently I keep it in the
 * atom model as atom language. However it is most likely
 * </p>
 * <p>
 * The {@link AtomLanguage} is an immutable instance and does not offer creation with new. Instead
 * the {@link #valueOf(String)} method is used for creation offering the implementation to maybe
 * return an already instantiated instance rather than creating a new one.
 * </p>
 * 
 * @author Stephan Schloepke
 */
public class AtomLanguage {
	/**
	 * The pattern to check the language to match the correct string.
	 */
	public static final Pattern LANGUAGE_PATTERN = Pattern.compile("^([A-Za-z]{1,8}(-[A-Za-z0-9]{1,8})*)?$");

	private final String language;

	private AtomLanguage(String language) {
		this.language = AtomContractConstraint.mustMatchPattern(language, LANGUAGE_PATTERN, language);
	}

	/**
	 * Returns the language set.
	 * 
	 * @return the language
	 */
	public String getLanguage() {
		return this.language;
	}

	/**
	 * Creates an AtomLanguage.
	 * 
	 * @param language The language string
	 * @return The AtomLanguage
	 */
	public static AtomLanguage valueOf(String language) {
		return new AtomLanguage(language);
	}

	/**
	 * Creates an {@link AtomLanguage} for the given {@link Locale}.
	 * 
	 * @param language The local to create an {@link AtomLanguage} for (must not be null)
	 * @return The {@link AtomLanguage} representing the same language as the given {@link Locale}
	 */
	public static AtomLanguage valueOf(Locale language) {
		StringBuilder t = new StringBuilder(AtomContractConstraint.notNull("language", language).getLanguage());
		String temp = language.getCountry();
		if (temp != null && temp.length() > 0) {
			t.append("-").append(temp);
			temp = language.getVariant();
			if (temp != null && temp.length() > 0) {
				t.append("-").append(temp);
			}
		}
		return valueOf(t.toString());
	}

	/**
	 * Returns true if the given language string is a valid string for
	 * {@link AtomLanguage#valueOf(String)}.
	 * 
	 * @param language The language string to test
	 * @return True if the language string is a valid value for {@link AtomLanguage#valueOf(String)}
	 *         otherwise false.
	 */
	public static boolean isValidLanguage(String language) {
		return language != null && LANGUAGE_PATTERN.matcher(language).matches();
	}

	/**
	 * Returns the {@link Locale} created of this {@link AtomLanguage}.
	 * <p>
	 * The {@link Locale} is created only of up to the first three elements of the
	 * {@link AtomLanguage} string.
	 * </p>
	 * 
	 * @return The {@link Locale} created of this {@link AtomLanguage}.
	 */
	public Locale toLocale() {
		String[] elements = this.language.split("-");
		switch (elements.length) {
			case 1:
				return new Locale(elements[0]);
			case 2:
				return new Locale(elements[0], elements[1]);
			default:
				return new Locale(elements[0], elements[1], elements[2]);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.language;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.language.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		} else if (other != null && other instanceof AtomLanguage) {
			return this.language.equals(((AtomLanguage) other).language);
		} else {
			return false;
		}
	}

}
