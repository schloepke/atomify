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

import javax.ws.rs.core.Response.Status;

import org.atomify.model.syndication.AtomEntry;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AtomEntryClient extends RefreshableResourceClient<AtomEntry> {

	public AtomEntryClient(URI resourceURI, String username, String password) {
		super(resourceURI, username, password, null, AtomEntry.MEDIA_TYPE);
	}

	public AtomEntryClient(WebResource resource) {
		this(resource, null);
	}

	public AtomEntryClient(WebResource resource, AtomEntry currentEntry) {
		super(resource, AtomEntry.MEDIA_TYPE);
		if (currentEntry != null) {
			replaceEntity(currentEntry);
		}
	}

	@Override
	protected AtomEntry handleResponse(ClientResponse response) {
		return response.getEntity(AtomEntry.class);
	}

	public boolean delete() {
		ClientResponse response = resource().delete(ClientResponse.class);
		if (response.getStatus() == Status.OK.getStatusCode()) {
			return true;
		}
		return false;
	}

}
