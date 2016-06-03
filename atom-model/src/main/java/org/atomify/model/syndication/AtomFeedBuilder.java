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

import java.util.ArrayList;
import java.util.List;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;

public class AtomFeedBuilder extends AbstractAtomSourceBuilder<AtomFeedBuilder> implements Builder<AtomFeed> {
	/**
	 * Required at least one entry and entries must be the last in the list of elements.
	 */
	private List<AtomEntry> entries;

	public static AtomFeedBuilder newInstance() {
		return new AtomFeedBuilder();
	}

	private AtomFeedBuilder() {
		// disallow instantiation
	}

	public AtomFeed build() {
		AtomFeed temp = new AtomFeed(this.id, this.title, this.updated, this.entries);
		temp.setCategories(this.categories);
		temp.setAuthors(this.authors);
		temp.setContributors(this.contributors);
		temp.setLinks(this.links);
		temp.setSubtitle(this.subtitle);
		temp.setGenerator(this.generator);
		temp.setIcon(this.icon);
		temp.setLogo(this.logo);
		temp.setRights(this.rights);
		attachParentBuilder(temp);
		return temp;
	}

	@Override
	public void reset() {
		super.reset();
		if (this.entries != null) {
			this.entries.clear();
		}
	}

	@Element(name = "entry", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomFeedBuilder addEntry(AtomEntry entry) {
		getEntries().add(AtomContractConstraint.notNull("extension", entry));
		return this;
	}

	public List<AtomEntry> getEntries() {
		if (this.entries == null) {
			this.entries = new ArrayList<AtomEntry>();
		}
		return this.entries;
	}

}
