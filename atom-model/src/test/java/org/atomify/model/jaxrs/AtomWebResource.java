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
package org.atomify.model.jaxrs;

import java.util.Date;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.atomify.model.publishing.AtomPubCollection;
import org.atomify.model.publishing.AtomPubService;
import org.atomify.model.publishing.AtomPubWorkspace;
import org.atomify.model.syndication.AtomDate;
import org.atomify.model.syndication.AtomFeed;
import org.atomify.model.syndication.AtomId;
import org.atomify.model.syndication.AtomText;

@Path("atom")
public class AtomWebResource {

	@GET
	@Produces("application/atomsvc+xml")
	public AtomPubService getAtomServiceDocument(@Context UriInfo uriInfo) {
		try {
			return AtomPubService.newBuilder()
				.addWorkspace(AtomPubWorkspace.newBuilder()
					.setTitle(AtomText.newBuilder().setTextContent("Test").build())
					.addCollection(AtomPubCollection.newBuilder()
							.setTitle(AtomText.newBuilder().setTextContent("Collection number one").build())
							.setHref(uriInfo.getAbsolutePathBuilder().path(AtomWebResource.class.getMethod("getAtomFeedDocument", UriInfo.class)).build())
					.build())
				.build())
			.build();
		} catch(NoSuchMethodException e) {
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GET
	@Path("feed")
	@Produces("application/atom+xml;type=feed")
	public AtomFeed getAtomFeedDocument(@Context UriInfo uriInfo) {
		return AtomFeed.newBuilder()
			.setId(AtomId.valueOf(UUID.randomUUID()))
			.setTitle(AtomText.newBuilder().setTextContent("Some feed").build())
			.setUpdated(AtomDate.valueOf(new Date()))
			.build();
	}

}
