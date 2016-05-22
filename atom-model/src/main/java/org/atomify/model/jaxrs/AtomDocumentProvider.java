/*
 * Copyright (c) 2009-2016 Stephan Schloepke
 *
 * Stephan Schloepke: http://www.schloepke.de/
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.xml.transform.stream.StreamResult;

import org.atomify.model.AtomDocument;
import org.atomify.model.AtomDocumentParser;
import org.atomify.model.AtomDocumentSerializer;
import org.xml.sax.SAXException;

public class AtomDocumentProvider implements MessageBodyReader<AtomDocument>, MessageBodyWriter<AtomDocument> {

	// Reader Area

	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return AtomDocument.class.isAssignableFrom(type);
	}

	public AtomDocument readFrom(Class<AtomDocument> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
		try {
			return new AtomDocumentParser().parse(entityStream);
		} catch (SAXException e) {
			throw new RuntimeException("Could not parse AtomDocument: "+e.getMessage(), e);
		}
	}

	// Writer Area

	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return AtomDocument.class.isAssignableFrom(type);
	}

	public long getSize(AtomDocument t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	public void writeTo(AtomDocument t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
		Charset outputCharset = getCharset(mediaType);
		new AtomDocumentSerializer(outputCharset.name()).serialize(t, new StreamResult(entityStream));
	}

	private final Charset getCharset(MediaType m) {
		String name = (m == null) ? null : m.getParameters().get("charset");
		return (name == null) ? DEFAULT_CHARSET : Charset.forName(name);
	}

	private final static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

}
