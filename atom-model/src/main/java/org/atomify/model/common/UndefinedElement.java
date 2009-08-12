package org.atomify.model.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.atomify.model.AtomContractConstraint;

public class UndefinedElement implements Iterable<Object> {
	private final QName qualifiedName;
	private final Map<QName, String> attributes;
	private final List<Object> childrean;
	
	public static UndefinedElementBuilder newBuilder() {
		return UndefinedElementBuilder.newInstance();
	}

	public UndefinedElement(QName name, Map<QName, String> attributes, List<Object> childrean) {
		this.qualifiedName = AtomContractConstraint.notNull("name", name);
		if (attributes != null && attributes.size() > 0) {
			this.attributes = Collections.unmodifiableMap(new HashMap<QName, String>(attributes));
		} else {
			this.attributes = Collections.emptyMap();
		}
		if (childrean != null && childrean.size() > 0) {
			this.childrean = Collections.unmodifiableList(new ArrayList<Object>(childrean));
		} else {
			this.childrean = Collections.emptyList();
		}
	}

	public QName getQualifiedName() {
		return this.qualifiedName;
	}

	public Map<QName, String> getAttributes() {
		return this.attributes;
	}

	public String getAttributeValue(QName name) {
		return this.attributes.get(AtomContractConstraint.notNull("name", name));
	}

	public List<Object> getChildrean() {
		return this.childrean;
	}

	public Iterator<Object> iterator() {
		return this.childrean.iterator();
	}

}
