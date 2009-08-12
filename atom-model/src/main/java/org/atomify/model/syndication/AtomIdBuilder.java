package org.atomify.model.syndication;

import java.net.URI;

import org.atomify.model.AtomCommonBuilder;
import org.jbasics.parser.annotations.Content;
import org.jbasics.pattern.builder.Builder;

public class AtomIdBuilder extends AtomCommonBuilder<AtomIdBuilder> implements Builder<AtomId> {
	/**
	 * <b>Required:</b> IRI referecnce. Must be absolute.
	 * <p>
	 * TODO: Must be an IRI reference.
	 * </p>
	 */
	private URI id;

	public static AtomIdBuilder newInstance() {
		return new AtomIdBuilder();
	}

	private AtomIdBuilder() {
		// disallow instantiation
	}

	public AtomId build() {
		AtomId temp = new AtomId(this.id);
		attachCommonAttributes(temp);
		return temp;
	}

	@Override
	public void reset() {
		super.reset();
		this.id = null;
	}

	public AtomIdBuilder setId(URI id) {
		this.id = id;
		return this;
	}

	@Content
	public AtomIdBuilder setId(String id) {
		return setId(URI.create(id));
	}
	
}
