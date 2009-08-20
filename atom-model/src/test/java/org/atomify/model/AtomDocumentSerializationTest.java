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
package org.atomify.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import javax.xml.transform.stream.StreamResult;

import org.atomify.model.publishing.AtomPubCategories;
import org.atomify.model.publishing.AtomPubService;
import org.atomify.model.syndication.AtomEntry;
import org.atomify.model.syndication.AtomFeed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.xml.sax.InputSource;

@RunWith(Parameterized.class)
public class AtomDocumentSerializationTest {

	private String resource;
	private Class<? extends AtomDocument> expectedType;

	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] { // DocumentResouce, ExpectedDocumentClass
				{ "publishing/atom-service-document.xml", AtomPubService.class }, // AtomPubService
						{ "publishing/atom-categories-document.xml", AtomPubCategories.class }, // AtomPubCategories
						{ "syndication/atom-feed-document.xml", AtomFeed.class }, // AtomFeed
						{ "syndication/atom-entry-document.xml", AtomEntry.class } // AtomEntry
				});
	}

	public AtomDocumentSerializationTest(String resource, Class<? extends AtomDocument> expectedType) {
		assert resource != null && expectedType != null;
		this.resource = resource;
		this.expectedType = expectedType;
	}

	@Test
	public void testSerialize() throws Exception {
		System.out.println("\n----- " + this.resource + "\n\n");
		URL documentURL = getClass().getResource(this.resource);
		assertNotNull("Could not find test document " + this.resource, documentURL);
		AtomDocument testDoc = new AtomDocumentParser().parse(documentURL);
		assertNotNull(testDoc);
		assertTrue(this.expectedType.isAssignableFrom(testDoc.getClass()));
		StringWriter data = new StringWriter();
		new AtomDocumentSerializer().serialize(testDoc, new StreamResult(data));
		System.out.println(data);
		AtomDocument newTestDoc = new AtomDocumentParser().parse(new InputSource(new StringReader(data.toString())));
		assertNotNull(newTestDoc);
		assertEquals(this.expectedType, newTestDoc.getClass());
		assertEquals(testDoc, newTestDoc);
	}

}

//
// import java.io.*;
// //SAX classes.
// import org.xml.sax.*;
// import org.xml.sax.helpers.*;
// //JAXP 1.1
// import javax.xml.parsers.*;
// import javax.xml.transform.*;
// import javax.xml.transform.stream.*;
// import javax.xml.transform.sax.*;
// [...]
// //PrintWriter from a Servlet
// PrintWriter out = response.getWriter();
// StreamResult streamResult = new StreamResult(out);
// SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
// //SAX2.0 ContentHandler.
// TransformerHandler hd = tf.newTransformerHandler();
// Transformer serializer = hd.getTransformer();
// serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
// serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
// serializer.setOutputProperty(OutputKeys.INDENT,"yes");
// hd.setResult(streamResult);
// hd.startDocument();
// AttributesImpl atts = new AttributesImpl();
// //USERS tag.
// hd.startElement("","","USERS",atts);
// //USER tags.
// String[] id = {"PWD122","MX787","A4Q45"};
// String[] type = {"customer","manager","employee"};
// String[] desc = {"Tim@Home","Jack&Moud","John D'oé"};
// for (int i=0;i<id.length;i++)
// {
// atts.clear();
// atts.addAttribute("","","ID","CDATA",id[i]);
// atts.addAttribute("","","TYPE","CDATA",type[i]);
// hd.startElement("","","USER",atts);
// hd.characters(desc[i].toCharArray(),0,desc[i].length());
// hd.endElement("","","USER");
// }
// hd.endElement("","","USERS");
// hd.endDocument();
// [...]
