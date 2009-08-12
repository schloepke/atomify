package org.atomify.model.publishing;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.atomify.model.AtomCommonBuilder;
import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.UndefinedElement;
import org.atomify.model.syndication.AtomCategory;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Attribute;
import org.jbasics.parser.annotations.Content;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;
import org.jbasics.xml.types.XmlBooleanYesNoType;

public class AtomPubCategoriesBuilder extends AtomCommonBuilder<AtomPubCategoriesBuilder> implements
		Builder<AtomPubCategories> {

	private URI href;
	private XmlBooleanYesNoType fixed;
	private URI scheme;
	private List<AtomCategory> categories;
	private List<Object> undefinedContent;

	public static AtomPubCategoriesBuilder newInstance() {
		return new AtomPubCategoriesBuilder();
	}

	private AtomPubCategoriesBuilder() {
		// To block instantiation
	}

	public AtomPubCategories build() {
		if (this.href != null) {
			return new AtomPubCategories(this.href, this.undefinedContent);
		} else {
			return new AtomPubCategories(this.fixed, this.scheme, this.categories, this.undefinedContent);
		}
	}

	@Attribute(name = "href", required = false)
	public AtomPubCategoriesBuilder setOutOfLineCategories(URI href) {
		this.href = href;
		return this;
	}

	public AtomPubCategoriesBuilder setFixed(boolean fixed) {
		this.href = null;
		this.fixed = fixed ? XmlBooleanYesNoType.YES : XmlBooleanYesNoType.NO;
		return this;
	}

	@Attribute(name = "fixed", required = false)
	public AtomPubCategoriesBuilder setFixed(XmlBooleanYesNoType fixed) {
		this.href = null;
		this.fixed = fixed;
		return this;
	}

	@Attribute(name = "scheme", required = false)
	public AtomPubCategoriesBuilder setScheme(URI scheme) {
		this.href = null;
		this.scheme = scheme;
		return this;
	}

	@Element(name = "category", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = Element.UNBOUND)
	public AtomPubCategoriesBuilder addCategory(AtomCategory category) {
		this.href = null;
		if (this.categories == null) {
			this.categories = new ArrayList<AtomCategory>();
		}
		this.categories.add(AtomContractConstraint.notNull("category", category));
		return this;
	}

	public AtomPubCategoriesBuilder addCategory(String term, URI scheme, String label) {
		return addCategory(AtomCategory.newBuilder().setTerm(term).setScheme(scheme).setLabel(label).build());
	}

	@Content
	public AtomPubCategoriesBuilder appendText(String text) {
		getUndefinedContent().add(AtomContractConstraint.mustNotBeEmptyString(text, "text"));
		return this;
	}

	@AnyElement
	public AtomPubCategoriesBuilder appendAnyElement(UndefinedElement element) {
		getUndefinedContent().add(AtomContractConstraint.notNull("element", element));
		return this;
	}

	public List<Object> getUndefinedContent() {
		if (this.undefinedContent == null) {
			this.undefinedContent = new ArrayList<Object>();
		}
		return this.undefinedContent;
	}

}
