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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jbasics.parser.BuilderContentHandler;
import org.jbasics.parser.BuilderParserContext;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class AtomDocumentParser {
	private static final BuilderParserContext<AtomDocument> ATOM_DOCUMENT_BUILDER_PARSER_CTX = new BuilderParserContext<AtomDocument>(
			AtomDocument.class);

	private final XMLReader reader;
	private final BuilderContentHandler<AtomDocument> handler;
	private final AtomicBoolean parsingInProgress;

	public AtomDocumentParser() throws SAXException {
		this.reader = XMLReaderFactory.createXMLReader();
		this.handler = ATOM_DOCUMENT_BUILDER_PARSER_CTX.createContentHandler();
		this.reader.setContentHandler(this.handler);
		this.reader.setErrorHandler(this.handler);
		this.parsingInProgress = new AtomicBoolean(false);
	}

	public AtomDocument parse(InputSource source) {
		AtomContractConstraint.notNull("source", source);
		if (this.parsingInProgress.compareAndSet(false, true)) {
			try {
				this.reader.parse(source);
				return this.handler.getParsingResult();
			} catch (IOException e) {
				throw createRuntimeException(e);
			} catch (SAXException e) {
				throw createRuntimeException(e);
			} finally {
				this.parsingInProgress.set(false);
			}
		} else {
			throw new IllegalStateException("AtomDocumentParser is already parsing another source");
		}
	}

	public AtomDocument parse(Reader reader) {
		return parse(new InputSource(AtomContractConstraint.notNull("reader", reader)));
	}

	public AtomDocument parse(InputStream stream) {
		return parse(new InputSource(AtomContractConstraint.notNull("stream", stream)));
	}

	public AtomDocument parse(URI uri) {
		try {
			return parse(AtomContractConstraint.notNull("uri", uri).toURL());
		} catch (MalformedURLException e) {
			throw createRuntimeException(e);
		}
	}

	public AtomDocument parse(URL url) {
		InputStream in = null;
		try {
			in = AtomContractConstraint.notNull("url", url).openStream();
			return parse(new InputSource(in));
		} catch (IOException e) {
			throw createRuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// silently ignoring close problems
				}
			}
		}
	}

	public AtomDocument parse(File file) {
		FileReader f = null;
		try {
			f = new FileReader(AtomContractConstraint.notNull("file", file));
			return parse(f);
		} catch (FileNotFoundException e) {
			throw createRuntimeException(e);
		} finally {
			if (f != null) {
				try {
					f.close();
				} catch (IOException e) {
					// silently ignoring close problems
				}
			}
		}
	}

	private RuntimeException createRuntimeException(Throwable t) {
		assert t != null;
		RuntimeException result = new RuntimeException("[" + t.getClass().getSimpleName() + "] " + t.getMessage());
		result.setStackTrace(t.getStackTrace());
		return result;
	}

}
