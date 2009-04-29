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
package org.atomify.client.http;

/**
 * Listenbeschreibung
 * <p>
 * Detailierte Beschreibung
 * </p>
 *
 * @author stephan
 *
 */
public class MessageContractConstraint {

	/**
	 * Checks that the parameters are not null.
	 * 
	 * @param name The name of the parameter(s) to be added to the exception.
	 * @param parameters The parameters to check.
	 */
	public static void notNull(final String name, final Object... parameters) {
		if (parameters != null) {
			for (Object parameter : parameters) {
				if (parameter == null) {
					throw new IllegalArgumentException("[MessageContractConstraint failed] Null parameter(s): " + name);
				}
			}
		}
	}

	/**
	 * Checks that the given parameter is not null and returns the parameter or throws an {@link IllegalArgumentException}.
	 * 
	 * @param <T> The type of the parameter.
	 * @param name The name of the parameter to append to the exception.
	 * @param parameter The parameter value.
	 * @return The not null parameter
	 */
	public static <T> T notNull(final String name, final T parameter) {
		if (parameter == null) {
			throw new IllegalArgumentException("[MessageContractConstraint failed] Null parameter: " + name);
		}
		return parameter;
	}

	/**
	 * Checks that the string parameters are not null or empty.
	 * 
	 * @param name The name of the parameter(s) to be added to the exception.
	 * @param parameters The parameters to check.
	 */
	public static void notNullOrEmpty(final String name, final String... parameters) {
		if (parameters != null) {
			for (String parameter : parameters) {
				if (parameter == null || parameter.length() <= 0) {
					throw new IllegalArgumentException("[MessageContractConstraint failed] Null or empty parameter(s): " + name);
				}
			}
		}
	}

	/**
	 * Checks if the given Number is in the range suplied or if the number is null.
	 * 
	 * @param <T> The number type (like {@link Double}, {@link Integer} and so on).
	 * @param <C> The low and high value type as {@link Comparable} for the number type.
	 * @param name The name to be added to the exception.
	 * @param low The included low value to check.
	 * @param high The included high value to check.
	 * @param value The value to check.
	 * @return The value as given in the parameter (unchanged).
	 */
	public static <T extends Number, C extends Comparable<T>> T mustBeInRangeOrNull(final String name, final C low, final C high, final T value) {
		if (value == null) {
			return null;
		} else if (low != null && low.compareTo(value) > 0) {
			throw new IllegalArgumentException("[MessageContractConstraint failed] Value out of range: " + name);
		} else if (high != null && high.compareTo(value) < 0) {
			throw new IllegalArgumentException("[MessageContractConstraint failed] Value out of range: " + name);
		} else {
			return value;
		}
	}

}
