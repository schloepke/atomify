package org.atomify.model.syndication;

import org.atomify.model.AtomContractConstraint;
import org.jbasics.net.mediatype.MediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public abstract class AtomContentXml extends AtomContent {
	private final MediaType mediaType;

	public AtomContentXml(MediaType mediaType) {
		this.mediaType = AtomContractConstraint.notNull("mediaType", mediaType);
	}

	public MediaType getMediaType() {
		return this.mediaType;
	}

	@Override
	public boolean isXML() {
		return true;
	}

	@Override
	public boolean isMediaType(MediaType type) {
		return this.mediaType.isMediaTypeMatching(type);
	}

	// FIXME: Write a much better way of serialization

	public abstract void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException;

}
