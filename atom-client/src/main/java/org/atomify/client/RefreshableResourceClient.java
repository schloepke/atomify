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
import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;

import org.atomify.model.AtomContractConstraint;
import org.jbasics.arrays.ArrayConstants;
import org.jbasics.checker.ContractCheck;
import org.jbasics.net.http.HttpHeaderCreator;
import org.jbasics.net.mediatype.MediaTypeRange;
import org.jbasics.types.tuples.Pair;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;

public abstract class RefreshableResourceClient<T> {
	private final WebResource resource;
	private final String[] accepts;
	private final AtomicReference<Pair<ClientResponse, T>> entityReference;

	public RefreshableResourceClient(URI uri, String username, String password, ClientConfig clientConfig, MediaTypeRange... accepts) {
		this(Client.create(clientConfig != null ? clientConfig : new DefaultClientConfig()), uri, username, password, accepts);
	}

	@SuppressWarnings("unchecked")
	public RefreshableResourceClient(Client client, URI serviceUri, String username, String password, MediaTypeRange... accepts) {
		this.resource = ContractCheck.mustNotBeNull(client, "client").resource(ContractCheck.mustNotBeNull(serviceUri, "serviceUri"));
		this.accepts = convertMediaTypesRanges(accepts);
		this.entityReference = new AtomicReference<Pair<ClientResponse, T>>();
		if (username != null) {
			Pair<String, String> auth = HttpHeaderCreator.createBasicAuthorization(username, password);
			this.resource.addFilter(new AdditionalHeaderClientFilter(auth));
		}
		this.resource.addFilter(new GZIPContentEncodingFilter(Boolean.getBoolean("org.atomify.client.compressRequest")));
	}

	public RefreshableResourceClient(WebResource resource, String... accepts) {
		this.resource = AtomContractConstraint.notNull("resource", resource);
		this.entityReference = new AtomicReference<Pair<ClientResponse, T>>();
		if (accepts == null) {
			this.accepts = ArrayConstants.zeroLegnthArray();
		} else {
			this.accepts = accepts;
		}
	}

	public RefreshableResourceClient(WebResource resource, MediaTypeRange... accepts) {
		this.resource = AtomContractConstraint.notNull("resource", resource);
		this.entityReference = new AtomicReference<Pair<ClientResponse, T>>();
		this.accepts = convertMediaTypesRanges(accepts);
	}

	private String[] convertMediaTypesRanges(MediaTypeRange... accepts) {
		if (accepts == null) {
			return ArrayConstants.zeroLegnthArray();
		} else {
			String[] temp = new String[accepts.length];
			for (int i = 0; i < accepts.length; i++) {
				temp[i] = accepts[i].toString();
			}
			return temp;
		}
	}

	public T entity() {
		Pair<ClientResponse, T> result = this.entityReference.get();
		if (result == null) {
			return refresh();
		}
		return result.second();
	}

	public T refresh() {
		Pair<ClientResponse, T> current = this.entityReference.get();
		WebResource.Builder builder = this.resource.accept(this.accepts);
		if (current != null) {
			ClientResponse oldResponse = current.first();
			if (oldResponse.getLastModified() != null) {
				builder.header(HttpHeaders.IF_MODIFIED_SINCE, oldResponse.getLastModified());
			}
		}
		ClientResponse response = builder.get(ClientResponse.class);
		if (current != null && Status.NOT_MODIFIED.getStatusCode() == response.getStatus()) {
			return current.second();
		}
		if (Status.OK.getStatusCode() != response.getStatus()) {
			throw new UniformInterfaceException(response);
		}
		Pair<ClientResponse, T> refreshed = new Pair<ClientResponse, T>(response, handleResponse(response));
		if (!this.entityReference.compareAndSet(current, refreshed)) {
			current = this.entityReference.get();
			if (!this.entityReference.compareAndSet(current, refreshed)) {
				if (current == null) {
					this.entityReference.set(refreshed);
				} else {
					throw new ConcurrentModificationException("Multithreaded modification detected and cannot be corrected");
				}
			}
		}
		return refreshed.second();
	}

	public WebResource resource() {
		return this.resource;
	}

	protected T replaceEntity(T entity) {
		Pair<ClientResponse, T> current = this.entityReference.get();
		Pair<ClientResponse, T> newEntity = new Pair<ClientResponse, T>(null, entity);
		if (!this.entityReference.compareAndSet(current, newEntity)) {
			current = this.entityReference.get();
			if (!this.entityReference.compareAndSet(current, newEntity)) {
				if (current == null) {
					this.entityReference.set(newEntity);
				} else {
					throw new ConcurrentModificationException("Multithreaded modification detected and cannot be corrected");
				}
			}
		}
		return current != null ? current.second() : null;
	}

	protected abstract T handleResponse(ClientResponse response);

}
