package org.atomify.model.publishing;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.Locale;

import org.atomify.model.AtomConstants;
import org.atomify.model.syndication.AtomLanguage;
import org.jbasics.xml.types.XmlSpaceType;
import org.junit.Test;

public class AtomPubAcceptTest {

	@Test
	public void testSimpleBuilding() {
		AtomPubAcceptBuilder builder = AtomPubAccept.newBuilder();
		AtomPubAccept test = builder.setAcceptMediaRange(AtomConstants.ATOM_ENTRY_MEDIA_TYPE).build();
		assertEquals(AtomConstants.ATOM_ENTRY_MEDIA_TYPE.toString(), test.getAcceptMediaRange());
		AtomPubAccept test2 = builder.build();
		assertSame(test, test2);
		builder.reset();
		test = builder.setAcceptMediaRange("text/*").build();
		test2 = builder.build();
		assertNotSame(test, test2);
		assertEquals(test, test2);
		builder.reset();
		test = builder.build();
		assertEquals("", test.getAcceptMediaRange());
	}

	@Test
	public void testBuildingWithCommonAttributes() {
		URI base = URI.create("http://example.com/");
		AtomPubAcceptBuilder builder = AtomPubAccept.newBuilder();
		AtomPubAccept test = builder.setXmlBase(base).setXmlLang(AtomLanguage.valueOf(Locale.GERMAN)).setXmlSpace(
				XmlSpaceType.PRESERVED).setAcceptMediaRange(AtomConstants.ATOM_ENTRY_MEDIA_TYPE).build();
		assertEquals(AtomConstants.ATOM_ENTRY_MEDIA_TYPE.toString(), test.getAcceptMediaRange());
		assertEquals(base, test.getXmlBase());
		assertEquals(XmlSpaceType.PRESERVED, test.getXmlSpace());
		assertEquals(Locale.GERMAN, test.getXmlLang().toLocale());
	}
	
	@Test
	public void testEqualsAndHashCode() {
		URI base = URI.create("http://example.com/");
		AtomPubAcceptBuilder builder = AtomPubAccept.newBuilder();
		AtomPubAccept test = builder.setXmlBase(base).setXmlLang(AtomLanguage.valueOf(Locale.GERMAN)).setXmlSpace(
				XmlSpaceType.PRESERVED).setAcceptMediaRange(AtomConstants.ATOM_ENTRY_MEDIA_TYPE).build();
		AtomPubAccept testTwo = builder.build();
		assertNotSame(test, testTwo);
		assertEquals(test, testTwo);
		assertEquals(test.hashCode(), testTwo.hashCode());
		testTwo = builder.setXmlBase(null).build();
		assertNotSame(test, testTwo);
		assertFalse(test.equals(testTwo));
		assertFalse(test.hashCode() == testTwo.hashCode());
		assertEquals(test, test);
	}

}
