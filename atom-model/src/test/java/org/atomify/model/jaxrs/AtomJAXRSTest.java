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

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;
import org.atomify.model.AtomDocumentSerializer;
import org.atomify.model.publishing.AtomPubCollection;
import org.atomify.model.publishing.AtomPubService;
import org.atomify.model.syndication.AtomFeed;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.xml.transform.stream.StreamResult;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("nls")
public class AtomJAXRSTest {
	private static SelectorThread selectorThread;

	@BeforeClass
	public static void startServer() throws Exception {
		final Map<String, String> initParams = new HashMap<String, String>();
		initParams.put("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
		initParams.put("com.sun.jersey.config.property.packages", "org.atomify.model.jaxrs");
		AtomJAXRSTest.selectorThread = GrizzlyWebContainerFactory.create(UriBuilder.fromUri("http://localhost/").port(8081).build(), initParams);
	}

	@AfterClass
	public static void stopServer() throws Exception {
		AtomJAXRSTest.selectorThread.stopEndpoint();
	}

	@Test
	public void testSomething() throws Exception {
		ClientConfig clientConfig = new DefaultClientConfig();
		Client client = Client.create(clientConfig);
		WebResource service = client.resource(UriBuilder.fromUri("http://localhost").port(8081).path("atom").build());

		AtomPubService atomService = service.get(AtomPubService.class);

		Assert.assertNotNull(atomService);

		new AtomDocumentSerializer().serialize(atomService, new StreamResult(System.out));

		AtomPubCollection atomColl = atomService.getWorkspaces().get(0).getCollections().get(0);

		AtomFeed atomFeed = client.resource(atomColl.getHref()).get(AtomFeed.class);
		Assert.assertNotNull(atomFeed);

		new AtomDocumentSerializer().serialize(atomFeed, new StreamResult(System.out));

	}

}
