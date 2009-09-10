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

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;
import javax.xml.transform.stream.StreamResult;

import org.atomify.model.AtomDocumentSerializer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

public class AtomClientRemoteTest {
	private static SelectorThread selectorThread;
	private AtomDocumentSerializer serializer = new AtomDocumentSerializer();

	@BeforeClass
	public static void startServer() throws Exception {
		final Map<String, String> initParams = new HashMap<String, String>();
		initParams.put("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
		initParams.put("com.sun.jersey.config.property.packages", AtomClientTestResource.class.getPackage().getName());
		selectorThread = GrizzlyWebContainerFactory.create(UriBuilder.fromUri("http://localhost/").port(8081).build(), initParams);
	}

	@AfterClass
	public static void stopServer() throws Exception {
		selectorThread.stopEndpoint();
	}
	
	@Test
	public void testSomething() throws Exception {
//		System.in.read();
		
		AtomServiceClient service = new AtomServiceClient(UriBuilder.fromUri("http://localhost").port(8081).path("atom").build());

		this.serializer.serialize(service.entity(), new StreamResult(System.out));
		
		assertNotNull(service);

		AtomFeedClient collection = service.getCollection("test workspace 1", "test feed");

		this.serializer.serialize(collection.entity(), new StreamResult(System.out));
		
		assertNotNull(collection);
		
		AtomEntryClient entry = collection.getEntry(0);
		
		assertNotNull(entry);
		
		this.serializer.serialize(entry.entity(), new StreamResult(System.out));
		
	}

}
