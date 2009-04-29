/**
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

import org.atomify.model.AtomDocument;
import org.atomify.model.AtomMediaType;
import org.atomify.model.syndication.AtomCommonAttributes;
import org.atomify.model.syndication.AtomExtension;

/**
 * Listenbeschreibung
 * <p>
 * Detailierte Beschreibung
 * </p>
 * 
 * @author stephan
 */
public class AtomPubService extends AtomCommonAttributes implements AtomDocument {
	public static final AtomMediaType MEDIA_TYPE = new AtomMediaType("application", "atomsvc+xml");

	private List<AtomPubWorkspace> workspaces;
	
	/**
	 * TODO: We need to actually make this an AtomPubExtension maybe derived
	 * from AtomExtenion or a common base type
	 */
	private List<AtomExtension> extensions;

	public AtomMediaType getMediaType() {
		return MEDIA_TYPE;
	}

	/**
	 * @return the workspaces
	 */
	public List<AtomPubWorkspace> getWorkspaces() {
		if (this.workspaces == null) {
			this.workspaces = new ArrayList<AtomPubWorkspace>();
		}
		return this.workspaces;
	}

	/**
	 * @return the foreignMarkup
	 */
	public List<AtomExtension> getExtensions() {
		if (this.extensions == null) {
			this.extensions = new ArrayList<AtomExtension>();
		}
		return this.extensions;
	}

	public void addWorkspace(AtomPubWorkspace workspace) {
		getWorkspaces().add(workspace);
	}
	
	public static AtomPubServiceBuilder newBuilder() {
		return AtomPubServiceBuilder.newInstance();
	}
}
