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
package org.atomify.model.publishing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.atomify.model.AtomCommonAttributes;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.AtomDocument;
import org.atomify.model.AtomMediaType;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Represents the Atom Publishing Service Document.
 * <p>
 * Every {@link AtomDocument} is immutable. This means you can only modify it or create it with a
 * builder. Usually there is a static method {@link #newBuilder()} to create a builder for the type
 * of document or sub document to create. All builders use the fluent interface to quickly build the
 * document structure.
 * </p>
 * <p>
 * The parsing of the documents are done by using a SAX {@link DefaultHandler} directly invoking the
 * builder and it's methods. The builder checks if the content is correct. In order to parse a
 * document it must comply 100% to the standard. No exceptions are allowed. However since it is
 * sometimes possible that you need to parse malformed atom documents you may want to put a
 * transformer ahead of it to transform the malformed data to be a valid atom document.
 * </p>
 * 
 * @author Stephan Schloepke
 */
public class AtomPubService extends AtomCommonAttributes implements AtomDocument, Iterable<AtomPubWorkspace> {
	public static final AtomMediaType MEDIA_TYPE = new AtomMediaType("application", "atomsvc+xml");

	private final List<AtomPubWorkspace> workspaces;
	private final List<AtomPubExtension> extensions;

	public static AtomPubServiceBuilder newBuilder() {
		return AtomPubServiceBuilder.newInstance();
	}

	protected AtomPubService(List<AtomPubWorkspace> workspaces, List<AtomPubExtension> extensions) {
		this.workspaces = Collections.unmodifiableList(new ArrayList<AtomPubWorkspace>(AtomContractConstraint
				.mustNotBeEmpty(workspaces, "workspaces")));
		if (extensions == null || extensions.isEmpty()) {
			this.extensions = Collections.emptyList();
		} else {
			this.extensions = Collections.unmodifiableList(new ArrayList<AtomPubExtension>(extensions));
		}
	}

	public AtomMediaType getMediaType() {
		return MEDIA_TYPE;
	}

	public List<AtomPubWorkspace> getWorkspaces() {
		return this.workspaces;
	}

	public List<AtomPubExtension> getExtensions() {
		return this.extensions;
	}

	public Iterator<AtomPubWorkspace> iterator() {
		return this.workspaces.iterator();
	}
}
