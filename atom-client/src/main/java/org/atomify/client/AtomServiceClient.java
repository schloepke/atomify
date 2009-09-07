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

import org.atomify.model.publishing.AtomPubCollection;
import org.atomify.model.publishing.AtomPubService;
import org.jbasics.checker.ContractCheck;
import org.jbasics.net.mediatype.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class AtomServiceClient extends RefreshableResourceClient<AtomPubService> {

	public AtomServiceClient(URI serviceUri) {
		this(serviceUri, null, null, new DefaultClientConfig());
	}

	public AtomServiceClient(URI serviceUri, String username, String password) {
		this(serviceUri, username, password, new DefaultClientConfig());
	}

	public AtomServiceClient(URI serviceUri, ClientConfig clientConfig) {
		this(serviceUri, null, null, clientConfig);
	}

	public AtomServiceClient(URI serviceUri, String username, String password, ClientConfig clientConfig) {
		this(Client.create(ContractCheck.mustNotBeNull(clientConfig, "clientConfig")), serviceUri, username, password);
	}

	@SuppressWarnings("unchecked")
	public AtomServiceClient(Client client, URI serviceUri, String username, String password) {
		super(ContractCheck.mustNotBeNull(client, "client"), ContractCheck.mustNotBeNull(serviceUri, "serviceUri"), username, password, AtomPubService.MEDIA_TYPE);
	}
	
	public WebResource getCollectionResource(String workspaceTitle, String collectionTitle, MediaType... mediaTypes) {
		AtomPubCollection temp = entity().findCollection(workspaceTitle, collectionTitle, mediaTypes);
		return temp != null ? resource().uri(temp.getHref()) : null; 
	}

	public AtomFeedClient getCollection(String workspaceTitle, String collectionTitle, MediaType... mediaTypes) {
		AtomPubCollection temp = entity().findCollection(workspaceTitle, collectionTitle, mediaTypes);
		if (temp != null) {
			return new AtomFeedClient(resource().uri(temp.getHref()));
		}
		return null;
	}

	@Override
	protected AtomPubService handleResponse(ClientResponse response) {
		return response.getEntity(AtomPubService.class);
	}

}
