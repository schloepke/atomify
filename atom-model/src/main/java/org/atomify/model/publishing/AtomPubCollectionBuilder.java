package org.atomify.model.publishing;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.atomify.model.AtomCommonBuilder;
import org.atomify.model.AtomConstants;
import org.atomify.model.syndication.AtomText;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Attribute;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;

public class AtomPubCollectionBuilder extends AtomCommonBuilder<AtomPubWorkspaceBuilder> implements
		Builder<AtomPubCollection> {

	/**
	 * <b>Required:</b> the title element.
	 */
	private AtomText title;
	/**
	 * <b>Required:</b> The href of the collection
	 */
	private URI href;
	/**
	 * <b>Optional:</b> The optional accept fields. If empty the default accept of atom:entry is in
	 * place.
	 */
	private List<AtomPubAccept> accepts;
	/**
	 * <b>Optional:</b> The categories
	 */
	private List<AtomPubCategories> categories;
	/**
	 * <b>Optional:</b> extension elements (all non AtomPub namespace and not atom:title)
	 */
	private List<AtomPubExtension> extensions;

	public static AtomPubCollectionBuilder newInstance() {
		return new AtomPubCollectionBuilder();
	}

	private AtomPubCollectionBuilder() {
		// dissallow instantiation
	}

	public AtomPubCollection build() {
		AtomPubCollection temp = new AtomPubCollection(this.title, this.href, this.accepts, this.categories,
				this.extensions);
		attachCommonAttributes(temp);
		return temp;
	}

	@Attribute(name = "href", namespace = "", required = false)
	public AtomPubCollectionBuilder setHref(URI href) {
		this.href = href;
		return this;
	}

	@Element(name = "categories", namespace = AtomConstants.ATOM_PUB_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomPubCollectionBuilder addCategories(AtomPubCategories categories) {
		if (this.categories == null) {
			this.categories = new ArrayList<AtomPubCategories>();
		}
		this.categories.add(categories);
		return this;
	}

	@Element(name = "accept", namespace = AtomConstants.ATOM_PUB_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomPubCollectionBuilder addAccept(AtomPubAccept accept) {
		if (this.accepts == null) {
			this.accepts = new ArrayList<AtomPubAccept>();
		}
		this.accepts.add(accept);
		return this;
	}

	@Element(name = "title", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1)
	public AtomPubCollectionBuilder setTitle(AtomText title) {
		this.title = title;
		return this;
	}

	@AnyElement
	public AtomPubCollectionBuilder addExtension(AtomPubExtension extension) {
		if (this.extensions == null) {
			this.extensions = new ArrayList<AtomPubExtension>();
		}
		this.extensions.add(extension);
		return this;
	}

}
