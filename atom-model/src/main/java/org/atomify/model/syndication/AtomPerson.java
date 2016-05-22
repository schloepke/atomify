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
package org.atomify.model.syndication;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.AtomCommonAttributes;
import org.atomify.model.extension.AtomExtension;
import org.jbasics.checker.ContractCheck;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Atom person construct. Holds a person for atom.
 * 
 * @author Stephan Schloepke
 */
public class AtomPerson extends AtomCommonAttributes {

	/**
	 * <b>Required:</b> atom:name element.
	 */
	private final String name;
	/**
	 * <b>Optional:</b> atom:uri element.
	 * <p>
	 * TODO: Needs to be an IRI not an URI.
	 * </p>
	 */
	private final URI uri;
	/**
	 * <b>Optional:</b> atom:email element.
	 */
	private final String email;
	/**
	 * <b>Optional:</b> any number of atom extension elements.
	 */
	private final List<AtomExtension> extensions;

	public static AtomPersonBuilder newBuilder() {
		return AtomPersonBuilder.newInstance();
	}

	/**
	 * Returns an AtomPerson for the given name without email and URI. The name MUST NOT be null and
	 * the trimmed content must not result in an empty string.
	 * 
	 * @param name The name of the person (MUST NOT be null or empty string)
	 * @return The newly created or shared instance of the person.
	 */
	public static AtomPerson valueOf(String name) {
		return new AtomPerson(ContractCheck.mustNotBeNullOrTrimmedEmpty(name, "name"), null, null, null);
	}

	/**
	 * Creates an {@link AtomPerson} instance for given user principal. The supplied principal must
	 * not be null and the name must not be null or empty.
	 * 
	 * @param principal The principal (MUST NOT be null and the name of the principal MUST NOT be
	 *            null or empty)
	 * @return The newly created or shared instance of the person.
	 */
	public static AtomPerson valueOf(Principal principal) {
		return valueOf(ContractCheck.mustNotBeNull(principal, "principal").getName());
	}

	/**
	 * Create a person instance for the given name.
	 * 
	 * @param name The name of the atom person.
	 */
	protected AtomPerson(String name, String email, URI uri, List<AtomExtension> extensions) {
		this.name = AtomContractConstraint.notNull("name", name);
		// TODO: We need to match the email if it is not null
		this.email = email;
		this.uri = uri;
		if (extensions == null || extensions.isEmpty()) {
			this.extensions = Collections.emptyList();
		} else {
			this.extensions = Collections.unmodifiableList(new ArrayList<AtomExtension>(extensions));
		}
	}

	/**
	 * Returns the name of the person.
	 * 
	 * @return The name of the person.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the URI of this person.
	 * 
	 * @return The URI of the person.
	 */
	public URI getUri() {
		return this.uri;
	}

	/**
	 * Returns the eMail of the person.
	 * 
	 * @return The eMail of the person.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Returns the lazy initialized list of extensions.
	 * 
	 * @return The lazy initialized list of extensions.
	 */
	public List<AtomExtension> getExtensions() {
		return this.extensions;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
		result = prime * result + ((this.extensions == null) ? 0 : this.extensions.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.uri == null) ? 0 : this.uri.hashCode());
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
		if (!(obj instanceof AtomPerson)) {
			return false;
		}
		AtomPerson other = (AtomPerson) obj;
		if (this.email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!this.email.equals(other.email)) {
			return false;
		}
		if (this.extensions == null) {
			if (other.extensions != null) {
				return false;
			}
		} else if (!this.extensions.equals(other.extensions)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.uri == null) {
			if (other.uri != null) {
				return false;
			}
		} else if (!this.uri.equals(other.uri)) {
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
		return new StringBuilder().append("AtomPerson [email=").append(this.email).append(", extensions=").append(this.extensions).append(", name=")
				.append(this.name).append(", uri=").append(this.uri).append(", ").append(super.toString()).append("]").toString();
	}

	// FIXME: Write a much better way of serialization

	@SuppressWarnings("all")
	public void serialize(QName name, ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		String namespace = name.getNamespaceURI();
		String local = name.getLocalPart();
		String qName = (name.getPrefix() != null && name.getPrefix().length() > 0 ? name.getPrefix() + ":" : "") + local;
		handler.startElement(namespace, local, qName, attributes);
		serializeElement("name", this.name, handler, attributes);
		if (this.email != null) {
			serializeElement("email", this.email, handler, attributes);
		}
		if (this.uri != null) {
			serializeElement("uri", this.uri.toASCIIString(), handler, attributes);
		}
		for (AtomExtension extension : this.extensions) {
			extension.serialize(handler, attributes);
		}
		handler.endElement(namespace, local, qName);
	}

	private void serializeElement(String name, String content, ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes.clear();
		handler.startElement(AtomConstants.ATOM_NS_URI, name, AtomConstants.ATOM_NS_PREFIX + ":" + name, attributes);
		char[] temp = content.toCharArray();
		handler.characters(temp, 0, temp.length);
		handler.endElement(AtomConstants.ATOM_NS_URI, name, AtomConstants.ATOM_NS_PREFIX + ":" + name);
	}

}
