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

import java.util.Map;

import org.jbasics.pattern.factory.Factory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class JaxRSClientFactory implements Factory<Client> {
	public static final String CONNECT_TIMEOUT_PROPERTY = "org.atomify.client.connectTimeout";
	public static final String READ_TIMEOUT_PROPERTY = "org.atomify.client.readTimeout";
	public static final String FOLLOW_REDIRECT_PROPERTY = "org.atomify.client.followRedirect";
	public static final int DEFAULT_CONNECT_TIMEOUT = 300;
	public static final int DEFAULT_READ_TIMEOUT = 300;
	public static final boolean DEFAULT_FOLLOW_REDIRECT = true;

	public Client newInstance() {
		ClientConfig config = new DefaultClientConfig();
		Map<String, Object> properties = config.getProperties();
		properties.put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, Boolean.valueOf(System.getProperty(FOLLOW_REDIRECT_PROPERTY, Boolean
				.toString(DEFAULT_FOLLOW_REDIRECT))));
		properties.put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, Integer.getInteger(CONNECT_TIMEOUT_PROPERTY, DEFAULT_CONNECT_TIMEOUT));
		properties.put(ClientConfig.PROPERTY_READ_TIMEOUT, Integer.getInteger(READ_TIMEOUT_PROPERTY, DEFAULT_READ_TIMEOUT));
		return Client.create(config);
	}

}
