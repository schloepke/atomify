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
import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.syndication.AtomText;

/**
 * Listenbeschreibung
 * <p>
 * Detailierte Beschreibung
 * </p>
 * 
 * @author stephan
 */
public class AtomPubWorkspace extends AtomCommonAttributes implements Iterable<AtomPubCollection> {
	private final AtomText title;
	private final List<AtomPubCollection> collections;
	private final List<AtomPubExtension> extensions;

	public static AtomPubWorkspaceBuilder newBuilder() {
		return AtomPubWorkspaceBuilder.newInstance();
	}

	protected AtomPubWorkspace(AtomText title, List<AtomPubCollection> collections, List<AtomPubExtension> extensions) {
		this.title = AtomContractConstraint.notNull("title", title);
		if (collections != null && collections.size() > 0) {
			this.collections = Collections.unmodifiableList(new ArrayList<AtomPubCollection>(collections));
		} else {
			this.collections = Collections.emptyList();
		}
		if (extensions == null || extensions.isEmpty()) {
			this.extensions = Collections.emptyList();
		} else {
			for(AtomPubExtension extension : extensions) {
				if (AtomConstants.ATOM_TITLE_QNAME.equals(extension.getExtensionName())) {
					throw new IllegalArgumentException("Atom Publishing Workspace cannot have an atom:title extension");
				}
			}
			this.extensions = Collections.unmodifiableList(new ArrayList<AtomPubExtension>(extensions));
		}
	}

	/**
	 * @return the title
	 */
	public AtomText getTitle() {
		return this.title;
	}

	/**
	 * @return the collections
	 */
	public List<AtomPubCollection> getCollections() {
		return this.collections;
	}

	/**
	 * @return the extensions
	 */
	public List<AtomPubExtension> getExtensions() {
		return this.extensions;
	}

	public Iterator<AtomPubCollection> iterator() {
		return this.collections.iterator();
	}

}
