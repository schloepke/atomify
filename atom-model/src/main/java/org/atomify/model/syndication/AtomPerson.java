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
package org.atomify.model.syndication;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.atomify.model.AtomCommonAttributes;
import org.atomify.model.AtomContractConstraint;

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

}
