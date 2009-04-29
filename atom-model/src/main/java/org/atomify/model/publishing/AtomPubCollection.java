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
package org.atomify.model.publishing;

import java.net.URI;
import java.util.List;

import org.atomify.model.syndication.AtomCommonAttributes;
import org.atomify.model.syndication.AtomExtension;
import org.atomify.model.syndication.AtomText;

/**
 * Listenbeschreibung
 * <p>
 * Detailierte Beschreibung
 * </p>
 * 
 * @author stephan
 */
public class AtomPubCollection extends AtomCommonAttributes {
	/**
	 * <b>Optional:</b> the external href URI attribute. If set non of the other are alowed to be set.
	 */
	private URI href;
	/**
	 * <b>Required:</b> the title element.
	 */
	private AtomText title;
	private List<AtomPubAccept> accepts;
	private List<AtomPubCategories> categories;
	/**
	 * <b>Optional:</b> extension elements (all non AtomPub namespace and not atom:title)
	 * 
	 * @todo TODO: needs to be AtomPubExtension
	 */
	private List<AtomExtension> extensions;
}
