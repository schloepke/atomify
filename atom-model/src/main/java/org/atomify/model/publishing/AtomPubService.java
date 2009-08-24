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

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.AtomDocument;
import org.atomify.model.common.AtomCommonAttributes;
import org.atomify.model.extension.AtomExtension;
import org.jbasics.net.mediatype.MediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
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
	/**
	 * The media type as a string constant.
	 */
	public static final String MEDIA_TYPE_STRING = "application/atomsvc+xml";
	/**
	 * The media type as {@link AtomMediaType}.
	 */
	public static final MediaType MEDIA_TYPE = MediaType.valueOf(MEDIA_TYPE_STRING);

	private final List<AtomPubWorkspace> workspaces;
	private final List<AtomExtension> extensions;

	public static AtomPubServiceBuilder newBuilder() {
		return AtomPubServiceBuilder.newInstance();
	}

	protected AtomPubService(List<AtomPubWorkspace> workspaces, List<AtomExtension> extensions) {
		this.workspaces = Collections.unmodifiableList(new ArrayList<AtomPubWorkspace>(AtomContractConstraint
				.mustNotBeEmpty(workspaces, "workspaces")));
		if (extensions == null || extensions.isEmpty()) {
			this.extensions = Collections.emptyList();
		} else {
			this.extensions = Collections.unmodifiableList(new ArrayList<AtomExtension>(extensions));
		}
	}

	public MediaType getMediaType() {
		return MEDIA_TYPE;
	}

	public List<AtomPubWorkspace> getWorkspaces() {
		return this.workspaces;
	}

	public List<AtomExtension> getExtensions() {
		return this.extensions;
	}

	public Iterator<AtomPubWorkspace> iterator() {
		return this.workspaces.iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.extensions == null) ? 0 : this.extensions.hashCode());
		result = prime * result + ((this.workspaces == null) ? 0 : this.workspaces.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof AtomPubService)) {
			return false;
		}
		AtomPubService other = (AtomPubService) obj;
		if (this.extensions == null) {
			if (other.extensions != null) {
				return false;
			}
		} else if (!this.extensions.equals(other.extensions)) {
			return false;
		}
		if (this.workspaces == null) {
			if (other.workspaces != null) {
				return false;
			}
		} else if (!this.workspaces.equals(other.workspaces)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder().append("AtomPubService [workspaces=").append(this.workspaces).append(", extensions=").append(this.extensions)
				.append(", ").append(super.toString()).append("]").toString();
	}

	// --- From here all is serialization. We Still need to think about a good way to do so.

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		handler.startPrefixMapping(AtomConstants.ATOM_NS_PREFIX, AtomConstants.ATOM_NS_URI);
		handler.startPrefixMapping(AtomConstants.ATOM_PUB_NS_PREFIX, AtomConstants.ATOM_PUB_NS_URI);
		attributes = initCommonAttributes(attributes);
		handler.startElement(AtomConstants.ATOM_PUB_NS_URI, "service", "app:service", attributes);
		for (AtomPubWorkspace workspace : this.workspaces) {
			workspace.serialize(handler, attributes);
		}
		for (AtomExtension extension : this.extensions) {
			extension.serialize(handler, attributes);
		}
		handler.endElement(AtomConstants.ATOM_PUB_NS_URI, "service", "app:service");
		handler.endPrefixMapping(AtomConstants.ATOM_NS_PREFIX);
		handler.endPrefixMapping(AtomConstants.ATOM_PUB_NS_PREFIX);
	}

}
