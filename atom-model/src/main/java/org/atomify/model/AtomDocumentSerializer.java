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

import java.net.URI;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import org.jbasics.xml.XmlStylesheetLinks;
import org.jbasics.xml.types.XmlStylesheetProcessInstruction;
import org.xml.sax.SAXException;

public class AtomDocumentSerializer {
	private final String encoding;

	public AtomDocumentSerializer() {
		this("UTF-8");
	}

	public AtomDocumentSerializer(String encoding) {
		this.encoding = AtomContractConstraint.mustNotBeEmptyString(encoding, "encoding");
	}

	public void serialize(AtomDocument document, Result result) {
		serialize(document, result, null);
	}

	protected void serialize(AtomDocument document, Result result, Templates templates) {
		AtomContractConstraint.notNull("document", document);
		AtomContractConstraint.notNull("result", result);
		try {
			SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();
			TransformerHandler handler = templates == null ? factory.newTransformerHandler() : factory.newTransformerHandler(templates);
			Transformer serializer = handler.getTransformer();
			serializer.setOutputProperty(OutputKeys.METHOD, "xml");
			serializer.setOutputProperty(OutputKeys.ENCODING, this.encoding);
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.VERSION, "1.0");
			serializer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			handler.setResult(result);
			if (document instanceof XmlStylesheetLinks) {
				for (XmlStylesheetProcessInstruction stylesheet : ((XmlStylesheetLinks) document).getStylesheetLinks()) {
					stylesheet.serialize(handler, null);
				}
			}
			handler.startDocument();
			document.serialize(handler, null);
			handler.endDocument();
		} catch (SAXException e) {
			RuntimeException er = new RuntimeException("[" + e.getClass().getSimpleName() + "] " + e.getMessage(), e);
			er.setStackTrace(e.getStackTrace());
			throw er;
		} catch (TransformerConfigurationException e) {
			RuntimeException er = new RuntimeException("[" + e.getClass().getSimpleName() + "] " + e.getMessage(), e);
			er.setStackTrace(e.getStackTrace());
			throw er;
		}
	}
}
