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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.jbasics.checker.ContractCheck;
import org.jbasics.net.mediatype.MediaType;
import org.jbasics.pattern.delegation.Delegate;
import org.jbasics.xml.XmlStylesheetLinks;
import org.jbasics.xml.types.XmlStylesheetProcessInstruction;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class AtomStylesheetAttachedDocument implements Delegate<AtomDocument>, AtomDocument, XmlStylesheetLinks {
	private final AtomDocument document;
	private final Collection<XmlStylesheetProcessInstruction> stylesheets;

	public AtomStylesheetAttachedDocument(AtomDocument document, XmlStylesheetProcessInstruction... stylesheets) {
		this.document = ContractCheck.mustNotBeNull(document, "document");
		if (stylesheets != null && stylesheets.length > 0) {
			this.stylesheets = Arrays.asList(stylesheets);
		} else {
			this.stylesheets = Collections.emptyList();
		}
	}

	public AtomDocument delegate() {
		return this.document;
	}

	public MediaType getMediaType() {
		return this.document.getMediaType();
	}

	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		this.document.serialize(handler, attributes);
	}

	public Collection<XmlStylesheetProcessInstruction> getStylesheetLinks() {
		return this.stylesheets;
	}

}
