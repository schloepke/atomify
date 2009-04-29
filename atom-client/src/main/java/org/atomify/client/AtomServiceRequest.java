/**
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

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import org.atomify.client.http.AcceptParameter;
import org.atomify.client.http.RequestHandler;
import org.atomify.client.http.RequestHeaders;
import org.atomify.client.http.ResponseMeta;
import org.atomify.model.AtomConstants;
import org.atomify.model.publishing.AtomPubService;

/**
 * Listenbeschreibung
 * <p>
 * Detailierte Beschreibung
 * </p>
 * 
 * @author stephan
 */
public class AtomServiceRequest extends RequestHeaders implements RequestHandler {
	public static final MediaType ATOM_PUB_SERVICE_MEDIA_TYPE = MediaType.valueOf(AtomPubService.MEDIA_TYPE.toString());
	private AtomPubService serviceDocument;

	@SuppressWarnings("unchecked")
	public AtomServiceRequest() {
		super();
		setAcceptMediaTypes(new AcceptParameter<MediaType>(ATOM_PUB_SERVICE_MEDIA_TYPE));
	}

	public AtomPubService getServiceDocument() {
		return this.serviceDocument;
	}

	public void processInput(final ResponseMeta metaData, final InputStream in) throws IOException {
		if (ATOM_PUB_SERVICE_MEDIA_TYPE.equals(metaData.getMediaType())) {
			// TODO: Parse the service document
		} else {
			throw new UnsupportedOperationException("Cannot decode mime type "+metaData.getMediaType());
		}
	}
}
