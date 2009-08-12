package org.atomify.model.publishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.UndefinedElement;
import org.jbasics.parser.annotations.AnyAttribute;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Content;
import org.jbasics.parser.annotations.QualifiedName;
import org.jbasics.pattern.builder.Builder;

public class AtomPubExtensionBuilder implements Builder<AtomPubExtension> {
	private QName qualifiedName;
	private Map<QName, String> attributes;
	private List<Object> childrean;

	public static AtomPubExtensionBuilder newInstance() {
		return new AtomPubExtensionBuilder();
	}

	private AtomPubExtensionBuilder() {
		// disallow instantiation
	}

	public AtomPubExtension build() {
		if ((this.childrean == null || this.childrean.isEmpty()
				|| (this.childrean.size() == 1 && this.childrean.get(0) instanceof String)) && (this.attributes == null
				|| this.attributes.isEmpty())) {
			return new AtomPubSimpleExtension(this.qualifiedName,
					this.childrean == null || this.childrean.isEmpty() ? null : this.childrean.get(0).toString());
		} else {
			return new AtomPubStructuredExtension(this.qualifiedName, this.attributes, this.childrean);
		}
	}

	public void reset() {
		this.qualifiedName = null;
		if (this.attributes != null) {
			this.attributes.clear();
		}
		if (this.childrean != null) {
			this.childrean.clear();
		}
	}

	public List<Object> getChildrean() {
		if (this.childrean == null) {
			this.childrean = new ArrayList<Object>();
		}
		return this.childrean;
	}

	public Map<QName, String> getAttributes() {
		if (this.attributes == null) {
			this.attributes = new HashMap<QName, String>();
		}
		return this.attributes;
	}

	@QualifiedName
	public AtomPubExtensionBuilder setQualifiedName(QName qualifiedName) {
		this.qualifiedName = qualifiedName;
		return this;
	}

	@AnyAttribute
	public AtomPubExtensionBuilder setAttribute(QName name, String value) {
		getAttributes().put(name, value);
		return this;
	}

	@Content
	public AtomPubExtensionBuilder appendText(String text) {
		getChildrean().add(AtomContractConstraint.mustNotBeEmptyString(text, "text"));
		return this;
	}

	@AnyElement
	public AtomPubExtensionBuilder appendAnyElement(UndefinedElement element) {
		getChildrean().add(AtomContractConstraint.notNull("element", element));
		return this;
	}
}
