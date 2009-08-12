package org.atomify.model.publishing;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.atomify.model.AtomCommonBuilder;
import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.AtomMediaType;
import org.jbasics.parser.annotations.Content;
import org.jbasics.pattern.builder.Builder;

public class AtomPubAcceptBuilder extends AtomCommonBuilder<AtomPubAcceptBuilder> implements Builder<AtomPubAccept> {
	public final static Map<String, AtomPubAccept> STANDARD_ACCEPTS;

	static {
		Map<String, AtomPubAccept> temp = new HashMap<String, AtomPubAccept>();
		temp.put("", new AtomPubAccept(""));
		temp.put(AtomConstants.ATOM_ENTRY_MEDIA_TYPE.toString(), new AtomPubAccept(AtomConstants.ATOM_ENTRY_MEDIA_TYPE
				.toString()));
		STANDARD_ACCEPTS = Collections.unmodifiableMap(temp);
	}

	private String acceptMediaRange;

	private AtomPubAcceptBuilder() {
		// empty to dissallow instantiation
	}

	public static AtomPubAcceptBuilder newInstance() {
		return new AtomPubAcceptBuilder();
	}

	public AtomPubAccept build() {
		AtomPubAccept temp = null;
		if (!hasCommonAttributes()) {
			temp = STANDARD_ACCEPTS.get(this.acceptMediaRange);
		}
		if (temp == null) {
			temp = new AtomPubAccept(this.acceptMediaRange);
			attachCommonAttributes(temp);
		}
		return temp;
	}

	@Override
	public void reset() {
		super.reset();
		this.acceptMediaRange = null;
	}

	@Content(mixed = false)
	public AtomPubAcceptBuilder setAcceptMediaRange(String acceptMediaRange) {
		this.acceptMediaRange = acceptMediaRange;
		return this;
	}

	public AtomPubAcceptBuilder setAcceptMediaRange(AtomMediaType acceptMediaRange) {
		return setAcceptMediaRange(AtomContractConstraint.notNull("acceptMediaRange", acceptMediaRange).toString());
	}

}
