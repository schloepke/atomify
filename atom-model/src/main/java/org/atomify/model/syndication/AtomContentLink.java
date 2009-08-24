package org.atomify.model.syndication;

import java.net.URI;

import javax.xml.namespace.QName;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.jbasics.net.mediatype.MediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class AtomContentLink extends AtomContent {
	private final URI source;
	private final MediaType mediaType;

	public AtomContentLink(URI source, MediaType mediatType) {
		this.source = AtomContractConstraint.notNull("source", source);
		this.mediaType = mediatType;
	}

	@Override
	public boolean isMediaType(MediaType type) {
		return this.mediaType != null ? this.mediaType.isMediaTypeMatching(type) : false;
	}

	public URI getSource() {
		return this.source;
	}

	public MediaType getMediaType() {
		return this.mediaType;
	}

	public String getType() {
		return this.mediaType == null ? null : this.mediaType.toString();
	}

	// FIXME: Write a much better way of serialization

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		addAttribute(attributes, SRC_QNAME, this.source.toString());
		if (this.mediaType != null) {
			addAttribute(attributes, TYPE_QNAME, this.mediaType.toString());
		}
		String namespace = AtomConstants.ATOM_NS_URI;
		String local = "content";
		String qName = AtomConstants.ATOM_NS_PREFIX + ":" + local;
		handler.startElement(namespace, local, qName, attributes);
		handler.endElement(namespace, local, qName);
	}

	protected final static QName SRC_QNAME = new QName("src");
	
}
