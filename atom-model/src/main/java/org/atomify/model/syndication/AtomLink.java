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
package org.atomify.model.syndication;

import java.awt.PageAttributes.MediaType;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.atomify.model.AtomContractConstraint;
import org.atomify.model.AtomMediaType;

/**
 * Represents an atom link element.
 * 
 * @author Stephan Schloepke
 */
public class AtomLink extends AtomCommonAttributes {
	/**
	 * <b>Required:</b> href attribute.
	 * <p>
	 * TODO: must be an IRI reference.
	 * </p>
	 */
	private URI href; // REQUIRED
	/**
	 * <b>Optional:</b> relation(rel) attribute. If not present it is default <em>alternate</em>
	 * <p>
	 * TODO: it must be either an IRI or an atom NC name. Need to think about how to wrap this correctly.
	 * </p>
	 */
	private URI rel; // AtomNCName or AtomURI
	/**
	 * <b>Optional:</b> type attribute holding the media type of the destination.
	 */
	private AtomMediaType type;
	/**
	 * <b>Optional:</b> hreflang attribute holding the language of the destination.
	 */
	private Locale hreflang;
	/**
	 * <b>Optional:</b> title attribute. The title is language sensitive.
	 */
	private String title;
	/**
	 * <b>Optional:</b> length attribute.
	 */
	private Integer lenght;
	/**
	 * <b>Optional:</b> any mixed content holding text|element. Any direct element must not be of the atom name space.
	 * <p>
	 * TODO: Do we want to aktually make this a Node of DOM?
	 * </p>
	 */
	private List<Object> undefinedContent;

	/**
	 * Empty constructor used in conjunction with a builder.
	 */
	public AtomLink() {
		// Empty constructor for builder.
	}
	
	/**
	 * Creates a link for the given href and relation and type.
	 * 
	 * @param href The href of the link (must not be null).
	 * @param rel The relation of the link.
	 * @param type The media type of the link.
	 */
	public AtomLink(final URI href, final String rel, final MediaType type) {
		this.href = AtomContractConstraint.notNull("href", href);
	}

	/**
	 * Sets the link href URI (must not be null).
	 * 
	 * @param href the href to set
	 */
	public void setHref(final URI href) {
		this.href = AtomContractConstraint.notNull("href", href);
	}

	/**
	 * Returns the link href URI.
	 * 
	 * @return the href
	 */
	public URI getHref() {
		return this.href;
	}

	/**
	 * Sets the link relation.
	 * 
	 * @param rel the rel to set
	 */
	public void setRel(final URI rel) {
		this.rel = rel;
	}

	/**
	 * Returns the link relation.
	 * 
	 * @return the rel
	 */
	public URI getRel() {
		return this.rel;
	}

	/**
	 * Sets the media type of the link destination.
	 * 
	 * @param type the type to set
	 */
	public void setType(final AtomMediaType type) {
		this.type = type;
	}

	/**
	 * Returns the media type of the link destination.
	 * 
	 * @return the type
	 */
	public AtomMediaType getType() {
		return this.type;
	}

	/**
	 * Sets the href language.
	 * 
	 * @param hreflang the hreflang to set
	 */
	public void setHreflang(final Locale hreflang) {
		this.hreflang = hreflang;
	}

	/**
	 * Returnsthe href language.
	 * 
	 * @return the hreflang
	 */
	public Locale getHreflang() {
		return this.hreflang;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title the title to set
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Returns the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the length.
	 * 
	 * @param lenght the lenght to set
	 */
	public void setLenght(final Integer lenght) {
		this.lenght = lenght;
	}

	/**
	 * Returns the length.
	 * 
	 * @return the lenght
	 */
	public Integer getLenght() {
		return this.lenght;
	}

	/**
	 * Returns the lazy initialized undefined content list.
	 * 
	 * @return the undefinedContent
	 */
	public List<Object> getUndefinedContent() {
		if (this.undefinedContent == null) {
			this.undefinedContent = new ArrayList<Object>();
		}
		return this.undefinedContent;
	}

}
