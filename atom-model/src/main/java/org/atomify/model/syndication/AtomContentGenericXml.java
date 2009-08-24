package org.atomify.model.syndication;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.atomify.model.extension.AtomForeignMarkup;
import org.jbasics.net.mediatype.MediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class AtomContentGenericXml extends AtomContentXml {
	private AtomForeignMarkup xmlContent;

	public AtomContentGenericXml(MediaType mediaType, AtomForeignMarkup xmlContent) {
		super(mediaType);
		this.xmlContent = AtomContractConstraint.notNull("xmlContent", xmlContent);
	}

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		addAttribute(attributes, TYPE_QNAME, getMediaType().toString());
		String namespace = AtomConstants.ATOM_NS_URI;
		String local = "content";
		String qName = AtomConstants.ATOM_NS_PREFIX + ":" + local;
		handler.startElement(namespace, local, qName, attributes);
		this.xmlContent.serialize(handler, attributes);
		handler.endElement(namespace, local, qName);
	}

}
