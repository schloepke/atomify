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
package org.atomify.service.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.atomify.service.AtomPubServiceResource;

/**
 * Annotation to annotate a class or method to be provided as Atom Syndication Feed in the Atom
 * Publishing Service Document.
 * <p>
 * Atom Publishing Service Document lists collections which are Atom Syndication Feeds. Use this
 * annotation in conjunction with the {@link AtomPubServiceResource} to produce the correct
 * Atom Publishing Protocol Service Document.
 * </p>
 * 
 * @author Stephan Schloepke
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD })
public @interface AtomPubServiceEntry {

	/**
	 * Returns the workspace title in which the collection appears. Does not have a default.
	 */
	String workspace();

	/**
	 * Returns the collection title.
	 */
	String collection();

}
