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
package org.atomify.model;

import org.atomify.model.publishing.AtomPubCategories;
import org.atomify.model.publishing.AtomPubService;
import org.jbasics.parser.annotations.ElementImplementor;
import org.jbasics.parser.annotations.ElementImplementors;

/**
 * Listenbeschreibung
 * <p>
 * Detailierte Beschreibung
 * </p>
 * 
 * @author stephan
 */
@ElementImplementors( {
		@ElementImplementor(builderClass = AtomPubService.class, namespace = AtomConstants.ATOM_PUB_NS_URI, localName = "service"),
		@ElementImplementor(builderClass = AtomPubCategories.class, namespace = AtomConstants.ATOM_PUB_NS_URI, localName = "categories"), })
public interface AtomDocument {

	public abstract AtomMediaType getMediaType();

}
