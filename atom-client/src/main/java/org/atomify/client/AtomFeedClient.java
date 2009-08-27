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

import java.net.URI;
import java.util.List;

import org.atomify.model.AtomContractConstraint;
import org.atomify.model.AtomRelations;
import org.atomify.model.syndication.AtomEntry;
import org.atomify.model.syndication.AtomFeed;
import org.atomify.model.syndication.AtomLink;
import org.jbasics.checker.ContractCheck;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class AtomFeedClient extends RefreshableResourceClient<AtomFeed> {

	public AtomFeedClient(WebResource feedResource) {
		super(feedResource, AtomFeed.MEDIA_TYPE);
	}

	public AtomLink getFirstLink(URI relation) {
		for (AtomLink link : entity().getLinks()) {
			if (AtomContractConstraint.notNull("relation", relation).equals(link.getRel())) {
				return link;
			}
		}
		return null;
	}

	public boolean hasNext() {
		return getFirstLink(AtomRelations.NEXT) != null;
	}

	public boolean hasPrevious() {
		return getFirstLink(AtomRelations.PREVIOUS) != null;
	}

	public boolean hasFirst() {
		return getFirstLink(AtomRelations.FIRST) != null;
	}

	public boolean hasLast() {
		return getFirstLink(AtomRelations.LAST) != null;
	}

	public AtomFeedClient next() {
		return queryFeedLink(AtomRelations.NEXT);
	}

	public AtomFeedClient previous() {
		return queryFeedLink(AtomRelations.PREVIOUS);
	}

	public AtomFeedClient first() {
		return queryFeedLink(AtomRelations.FIRST);
	}

	public AtomFeedClient last() {
		return queryFeedLink(AtomRelations.LAST);
	}

	public AtomEntryClient create(AtomEntry newEntry) {
		ClientResponse response = resource().type(AtomEntry.MEDIA_TYPE_STRING).post(ClientResponse.class, ContractCheck.mustNotBeNull(newEntry, "newEntry"));
		AtomEntry createdEntity = null;
		if (response.getStatus() == 201) {
			if (response.hasEntity()) {
				createdEntity = response.getEntity(AtomEntry.class);
			}
			return new AtomEntryClient(resource().uri(response.getLocation()), createdEntity);
		}
		throw new UniformInterfaceException("Cannot create entry in collection " + this.resource().getURI() + " (Status " + response.getStatus()
				+ " - " + response.getResponseStatus() + ")", response);
	}

	public AtomEntryClient getEntry(int index) {
		List<AtomEntry> entries = entity().getEntries();
		if (index < entries.size()) {
			AtomEntry entry = entries.get(index);
			AtomLink selfLink = null;
			AtomLink alternateLink = null;
			for (AtomLink link : entry.getLinks()) {
				if (link.getType() == null || AtomEntry.MEDIA_TYPE.equals(link.getType())) {
					if (AtomRelations.SELF.equals(link.getRel())) {
						selfLink = link;
					} else if (AtomRelations.ALTERNATE.equals(link.getRel())) {
						alternateLink = link;
					}
				}
			}
			if (selfLink != null) {
				return new AtomEntryClient(resource().uri(selfLink.getHref()), entry);
			} else if (alternateLink != null) {
				return new AtomEntryClient(resource().uri(alternateLink.getHref()), entry);
			}
			// FIXME: Well how should we handle not to know where the entry URI is?
			return new AtomEntryClient(resource(), entry);
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	protected AtomFeed handleResponse(ClientResponse response) {
		return response.getEntity(AtomFeed.class);
	}

	private AtomFeedClient queryFeedLink(URI relation) {
		AtomLink temp = getFirstLink(relation);
		if (temp != null && (temp.getType() == null || temp.getType().equals(AtomFeed.MEDIA_TYPE))) {
			return new AtomFeedClient(resource().uri(temp.getHref()));
		}
		throw new RuntimeException("No link with media type application/atom+xml;type=feed with rel " + relation + " available");
	}
}
