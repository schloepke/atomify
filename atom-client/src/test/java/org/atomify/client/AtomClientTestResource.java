/*
 * Copyright (c) 2009 Stephan Schloepke and innoQ Deutschland GmbH
 *
 * Stephan Schloepke: http://www.schloepke.de/
 * innoQ Deutschland GmbH: http://www.innoq.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.atomify.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.atomify.model.AtomDocumentParser;
import org.atomify.model.publishing.AtomPubService;
import org.atomify.model.syndication.AtomEntry;
import org.atomify.model.syndication.AtomFeed;
import org.xml.sax.SAXException;

@Path("atom")
public class AtomClientTestResource {
	private static final AtomPubService SERVICE_DOCUMENT;
	private static final AtomFeed FEED_DOCUMENT;

	static {
		try {
			AtomDocumentParser parser = new AtomDocumentParser();
			SERVICE_DOCUMENT = (AtomPubService) parser.parse(AtomClientTestResource.class.getResource("service-document.xml"));
			FEED_DOCUMENT = (AtomFeed) parser.parse(AtomClientTestResource.class.getResource("feed-document.xml"));
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
	}

	@GET
	@Produces("application/atomsvc+xml")
	public AtomPubService getAtomServiceDocument(@Context UriInfo uriInfo) {
		return SERVICE_DOCUMENT;
	}

	@GET
	@Path("feed")
	@Produces("application/atom+xml;type=feed")
	public AtomFeed getAtomFeedDocument(@Context UriInfo uriInfo) {
		return FEED_DOCUMENT;
	}

	@GET
	@Path("feed/{entry}")
	@Produces("application/atom+xml;type=feed")
	public AtomEntry getAtomEntryDocument(@Context UriInfo uriInfo, @PathParam("entry") Integer entry) {
		return FEED_DOCUMENT.getEntries().get(entry.intValue() - 1);
	}

}
