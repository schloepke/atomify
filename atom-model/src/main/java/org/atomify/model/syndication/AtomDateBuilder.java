package org.atomify.model.syndication;

import org.atomify.model.AtomCommonBuilder;
import org.jbasics.parser.annotations.Content;
import org.jbasics.pattern.builder.Builder;

public class AtomDateBuilder extends AtomCommonBuilder<AtomDateBuilder> implements Builder<AtomDate> {
	/**
	 * <b>Required:</b> IRI referecnce. Must be absolute.
	 * <p>
	 * TODO: Must be an IRI reference.
	 * </p>
	 */
	private String dateString;

	public static AtomDateBuilder newInstance() {
		return new AtomDateBuilder();
	}

	private AtomDateBuilder() {
		// disallow instantiation
	}

	public AtomDate build() {
		AtomDate temp = AtomDate.valueOf(this.dateString);
		attachCommonAttributes(temp);
		return temp;
	}

	@Override
	public void reset() {
		super.reset();
		this.dateString = null;
	}

	@Content
	public AtomDateBuilder setDate(String dateString) {
		this.dateString = dateString;
		return this;
	}

}
