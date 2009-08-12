package org.atomify.model.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.atomify.model.AtomContractConstraint;
import org.jbasics.parser.annotations.AnyAttribute;
import org.jbasics.parser.annotations.AnyElement;
import org.jbasics.parser.annotations.Content;
import org.jbasics.parser.annotations.QualifiedName;
import org.jbasics.pattern.builder.Builder;

public class UndefinedElementBuilder implements Builder<UndefinedElement> {
	private QName qualifiedName;
	private Map<QName, String> attributes;
	private List<Object> childrean;

	public static UndefinedElementBuilder newInstance() {
		return new UndefinedElementBuilder();
	}

	private UndefinedElementBuilder() {
		// disallow instantiation
	}

	public UndefinedElement build() {
		return new UndefinedElement(this.qualifiedName, this.attributes, this.childrean);
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
	public UndefinedElementBuilder setQualifiedName(QName qualifiedName) {
		this.qualifiedName = qualifiedName;
		return this;
	}

	@AnyAttribute
	public UndefinedElementBuilder setAttribute(QName name, String value) {
		getAttributes().put(name, value);
		return this;
	}

	@Content
	public UndefinedElementBuilder appendText(String text) {
		getChildrean().add(AtomContractConstraint.mustNotBeEmptyString(text, "text"));
		return this;
	}

	@AnyElement
	public UndefinedElementBuilder appendAnyElement(UndefinedElement element) {
		getChildrean().add(AtomContractConstraint.notNull("element", element));
		return this;
	}

}
