package org.atomify.model.syndication;

import java.net.URI;
import java.util.List;

import org.atomify.model.AtomCommonBuilder;
import org.atomify.model.AtomConstants;
import org.jbasics.parser.annotations.Element;
import org.jbasics.pattern.builder.Builder;

public class AtomPersonBuilder extends AtomCommonBuilder<AtomPersonBuilder> implements Builder<AtomPerson> {
	/**
	 * <b>Required:</b> atom:name element.
	 */
	private String name;
	/**
	 * <b>Optional:</b> atom:uri element.
	 * <p>
	 * TODO: Needs to be an IRI not an URI.
	 * </p>
	 */
	private URI uri;
	/**
	 * <b>Optional:</b> atom:email element.
	 */
	private String email;
	/**
	 * <b>Optional:</b> any number of atom extension elements.
	 */
	private List<AtomExtension> extensions;

	public static AtomPersonBuilder newInstance() {
		return new AtomPersonBuilder();
	}
	
	private AtomPersonBuilder() {
		// disallow instantiation
	}
	
	public AtomPerson build() {
		AtomPerson temp = new AtomPerson(this.name, this.email, this.uri, this.extensions);
		attachCommonAttributes(temp);
		return temp;
	}

	@Override
	public void reset() {
		super.reset();
		this.name = null;
		this.email = null;
		this.uri = null;
		if (this.extensions != null) {
			this.extensions.clear();
		}
	}

	@Element(name = "name", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	public AtomPersonBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	@Element(name = "email", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 0, maxOccurs = 1)
	public AtomPersonBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	
	@Element(name = "uri", namespace = AtomConstants.ATOM_NS_URI, minOccurs = 1, maxOccurs = 1)
	public AtomPersonBuilder setUri(URI uri) {
		this.uri = uri;
		return this;
	}

}
