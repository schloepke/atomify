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

import org.atomify.model.AtomContractConstraint;
import org.jbasics.pattern.builder.Builder;

public class AtomSourceBuilder extends AbstractAtomSourceBuilder<AtomSourceBuilder> implements Builder<AtomSource> {

	public static AtomSourceBuilder newInstance() {
		return new AtomSourceBuilder();
	}

	private AtomSourceBuilder() {
		// disallow instantiation
	}

	public AtomSource build() {
		AtomSource temp = new AtomSource(this.id, this.title, this.updated);
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

	public AtomSourceBuilder addAndSetAllFromFeed(AtomFeed feed) {
		AtomContractConstraint.notNull("feed", feed);
		setXmlBase(feed.getXmlBase());
		setXmlLang(feed.getXmlLang());
		setXmlSpace(feed.getXmlSpace());
		setId(feed.getId());
		setTitle(feed.getTitle());
		setSubtitle(feed.getSubtitle());
		setUpdated(feed.getUpdated());
		setGenerator(feed.getGenerator());
		setIcon(feed.getIcon());
		setLogo(feed.getLogo());
		setRights(feed.getRights());
		addLinks(feed.getLinks());
		addAuthors(feed.getAuthors());
		addContributors(feed.getContributors());
		addCategories(feed.getCategories());
		addExtensions(feed.getExtensions());
		return this;
	}

}
