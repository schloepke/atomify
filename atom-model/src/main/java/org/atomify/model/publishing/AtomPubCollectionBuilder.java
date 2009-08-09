package org.atomify.model.publishing;

import org.atomify.model.AtomCommonBuilder;
import org.atomify.model.AtomConstants;
import org.atomify.model.syndication.AtomText;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;

public class AtomPubCollectionBuilder extends AtomCommonBuilder<AtomPubWorkspaceBuilder> implements
		Builder<AtomPubCollection> {

	private AtomText title;

	public AtomPubCollection build() {
		return new AtomPubCollection(this.title);
	}

	@Element(name = "title", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1)
	public AtomPubCollectionBuilder setTitle(AtomText title) {
		this.title = title;
		return this;
	}

}
