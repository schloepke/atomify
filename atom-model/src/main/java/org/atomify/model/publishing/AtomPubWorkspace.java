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
import java.util.List;

import org.atomify.model.AtomContractConstraint;
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
public class AtomPubWorkspace extends AtomCommonAttributes {
	private AtomText title;
	private List<AtomPubCollection> collections;

	/**
	 * TODO: needs to be an AtomPubExtension excluding also atom:title.
	 */
	private List<AtomExtension> extensions;

	public AtomPubWorkspace(AtomText title) {
		setTitle(title);
	}

	/**
	 * @return the title
	 */
	public AtomText getTitle() {
		return this.title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(AtomText title) {
		this.title = AtomContractConstraint.notNull("title", title);
	}

	/**
	 * @return the collections
	 */
	public List<AtomPubCollection> getCollections() {
		if (this.collections == null) {
			this.collections = new ArrayList<AtomPubCollection>();
		}
		return this.collections;
	}

	/**
	 * @return the extensions
	 */
	public List<AtomExtension> getExtensions() {
		if (this.extensions == null) {
			this.extensions = new ArrayList<AtomExtension>();
		}
		return this.extensions;
	}
	
	public static AtomPubWorkspaceBuilder newBuilder() {
		return AtomPubWorkspaceBuilder.newInstance();
	}

}
