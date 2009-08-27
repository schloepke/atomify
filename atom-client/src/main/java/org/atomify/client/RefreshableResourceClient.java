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

import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicReference;

import org.atomify.model.AtomContractConstraint;
import org.jbasics.arrays.ArrayConstants;
import org.jbasics.net.mediatype.MediaTypeRange;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public abstract class RefreshableResourceClient<T> {
	private final WebResource resource;
	private final String[] accepts;
	private final AtomicReference<T> entityReference;

	public RefreshableResourceClient(WebResource resource, String... accepts) {
		this.resource = AtomContractConstraint.notNull("resource", resource);
		this.entityReference = new AtomicReference<T>();
		if (accepts == null) {
			this.accepts = ArrayConstants.zeroLegnthArray();
		} else {
			this.accepts = accepts;
		}
	}

	public RefreshableResourceClient(WebResource resource, MediaTypeRange... accepts) {
		this.resource = AtomContractConstraint.notNull("resource", resource);
		this.entityReference = new AtomicReference<T>();
		if (accepts == null) {
			this.accepts = ArrayConstants.zeroLegnthArray();
		} else {
			this.accepts = new String[accepts.length];
			for (int i = 0; i < accepts.length; i++) {
				this.accepts[i] = accepts[i].toString();
			}
		}
	}

	public T entity() {
		T result = this.entityReference.get();
		if (result == null) {
			return refresh();
		}
		return result;
	}

	public T refresh() {
		T current = this.entityReference.get();
		ClientResponse response = this.resource.accept(this.accepts).get(ClientResponse.class);
		T refreshed = handleResponse(response);
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
		return refreshed;
	}

	public WebResource resource() {
		return this.resource;
	}

	protected T replaceEntity(T newEntity) {
		T current = this.entityReference.get();
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
		return current;
	}

	protected abstract T handleResponse(ClientResponse response);

}
