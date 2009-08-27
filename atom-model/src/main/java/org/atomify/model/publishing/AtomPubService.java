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
import org.atomify.model.syndication.AtomPlainText;
import org.atomify.model.syndication.AtomText;
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

	/**
	 * Find the first occurrence of the workspace with the given name.
	 * <p>
	 * Workspaces have a title but no identifier which is supposed to be unique. With other words it
	 * is quite hard to automatically select the right workspace. In some cases it is actually
	 * having three workspaces with the same name but with different collections in it.
	 * </p>
	 * <p>
	 * Under such circumstances it is hard to "select" a workspace you want to use other than
	 * actually iterating over all workspaces and inspect them by you specific criteria. So
	 * {@link #findWorkspace(String)} returns the first workspace where the name matches. The match
	 * is made with a trimmed name rather than a full select and the match is also case insensitive.
	 * If you need another matching in your case you have to iterate over the workspaces your own
	 * and select the one you need.
	 * </p>
	 * 
	 * @param title The workspace title to look for (It can be hard to find the right workspace when
	 *            using XHTML or HTML text constructs so this here is usually only working with
	 *            plain text. In the future I am going to extend it to be able to search for the
	 *            pure text content of a html or xhtml entity like "MyTitle" also finds "{@code
	 *            <b>MyTitle</b>}".
	 * @return The first workspace matching the title (compared with both strings trimmed and case
	 *         insensitive). If not found returns null.
	 */
	public AtomPubWorkspace findWorkspace(String title) {
		AtomContractConstraint.notNull("title", title);
		for (AtomPubWorkspace workspace : this) {
			AtomText temp = workspace.getTitle();
			// FIXME: This is not nice. First we have to KNOW the real type
			// and second we need to cast it so we can get the content. This is not a
			// good solution since changes would affect this area.
			if (temp.isTextType()) {
				if (title.equalsIgnoreCase(((AtomPlainText) temp).getValue())) {
					return workspace;
				}
			}
		}
		return null;
	}

	public AtomPubCollection findCollection(String workspaceTitle, String collectionTitle, MediaType... mediaTypes) {
		AtomContractConstraint.notNull("workspaceTitle", workspaceTitle);
		AtomContractConstraint.notNull("collectionTitle", collectionTitle);
		for (AtomPubWorkspace workspace : this) {
			AtomText temp = workspace.getTitle();
			// FIXME: This is not nice. First we have to KNOW the real type
			// and second we need to cast it so we can get the content. This is not a
			// good solution since changes would affect this area.
			if (temp.isTextType()) {
				if (workspaceTitle.equalsIgnoreCase(((AtomPlainText) temp).getValue())) {
					AtomPubCollection collection = workspace.findCollection(collectionTitle, mediaTypes);
					if (collection != null) {
						return collection;
					}
				}
			}
		}
		return null;
		
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
