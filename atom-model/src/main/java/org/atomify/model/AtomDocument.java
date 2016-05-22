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
package org.atomify.model;

import org.atomify.model.publishing.AtomPubCategories;
import org.atomify.model.publishing.AtomPubService;
import org.atomify.model.syndication.AtomEntry;
import org.atomify.model.syndication.AtomFeed;
import org.jbasics.net.mediatype.MediaType;
import org.jbasics.parser.annotations.ElementImplementor;
import org.jbasics.parser.annotations.ElementImplementors;
import org.jbasics.xml.XmlSerializable;

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
		@ElementImplementor(builderClass = AtomPubCategories.class, namespace = AtomConstants.ATOM_PUB_NS_URI, localName = "categories"),
		@ElementImplementor(builderClass = AtomFeed.class, namespace = AtomConstants.ATOM_NS_URI, localName = "feed"),
		@ElementImplementor(builderClass = AtomEntry.class, namespace = AtomConstants.ATOM_NS_URI, localName = "entry") })
public interface AtomDocument extends XmlSerializable {

	MediaType getMediaType();

}
