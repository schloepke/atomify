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

import org.atomify.model.AtomCommonBuilder;
import org.atomify.model.AtomConstants;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;

public class AtomPubServiceBuilder extends AtomCommonBuilder<AtomPubServiceBuilder> implements Builder<AtomPubService> {
	private List<AtomPubWorkspace> workspaces;
	private List<AtomPubExtension> extensions;

	public static AtomPubServiceBuilder newInstance() {
		return new AtomPubServiceBuilder();
	}

	private AtomPubServiceBuilder() {
		// disallow instantiation
	}

	public AtomPubService build() {
		if (this.workspaces == null || this.workspaces.size() == 0) {
			throw new RuntimeException("AtomPubService building error: Must have at least one workspace");
		}
		AtomPubService result = new AtomPubService(this.workspaces, this.extensions);
		attachCommonAttributes(result);
		return result;
	}

	public void reset() {
		super.reset();
		if (this.workspaces != null) {
			this.workspaces.clear();
		}
		if (this.extensions != null) {
			this.extensions.clear();
		}
	}

	@Element(name = "workspace", namespace = AtomConstants.ATOM_PUB_NS_URI, minOccurs = 1, maxOccurs = Element.UNBOUND)
	public AtomPubServiceBuilder addWorkspace(AtomPubWorkspace workspace) {
		if (this.workspaces == null) {
			this.workspaces = new ArrayList<AtomPubWorkspace>();
		}
		this.workspaces.add(workspace);
		return this;
	}

	@AnyElement
	public AtomPubServiceBuilder addExtension(AtomPubExtension extension) {
		if (this.extensions == null) {
			this.extensions = new ArrayList<AtomPubExtension>();
		}
		this.extensions.add(extension);
		return this;
	}

}
