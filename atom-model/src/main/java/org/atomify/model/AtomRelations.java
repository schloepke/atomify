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
package org.atomify.model;

import java.net.URI;

/**
 * Link relations as defined by the Atom Syndication Format and registered with IANA Atom links.
 * <p>
 * The list here is either defined in the Atom Syndication Format spec 1.0 (RFC 4287) or registered at <a
 * href="http://www.iana.org/assignments/link-relations">IANA as Atom Link Relation</a>.
 * </p>
 * 
 * @author stephan
 */
public class AtomRelations {
	
	/**
	 * The place of the registry (this is not the same as in RFC 4287 mentioned. They changed it? The original URI mentioned is
	 * http://www.iana.org/assignments/relation but that page does not exist).
	 */
	public static final URI IANA_ATOM_LINK_RELATION = URI.create("http://www.iana.org/assignments/link-relations");

	/**
	 * The alternate link relation supplying an alternate representation of the content. The link is only allowed to appear once for the combination
	 * of media type and href language. (Defined by RFC 4287 / Atom Syndication Format)
	 */
	public static final URI ALTERNATE = URI.create("alternate");

	/**
	 * A link relation pointing on the resource itself. The self should always have be the same URI of the resource provided. (Defined by RFC 4287 /
	 * Atom Syndication Format)
	 */
	public static final URI SELF = URI.create("self");

	/**
	 * A link relation pointing on a related resource of this entry. (Defined by RFC 4287 / Atom Syndication Format)
	 */
	public static final URI RELATED = URI.create("related");

	/**
	 * A link relation pointing on a related resource which is potentially large in size. (Defined by RFC 4287 / Atom Syndication Format)
	 */
	public static final URI ENCLOSURE = URI.create("enclosure");

	/**
	 * A link relation pointing on a source of the information in this entry. (Defined by RFC 4287 / Atom Syndication Format)
	 */
	public static final URI VIA = URI.create("via");

	/**
	 * A link relation pointing to the first page of a paged feed (Defined by RFC 5005 / Feed Paging and Archiving).
	 */
	public static final URI FIRST = URI.create("first");

	/**
	 * A link relation pointing to the last page of a paged feed (Defined by RFC 5005 / Feed Paging and Archiving).
	 */
	public static final URI LAST = URI.create("last");

	/**
	 * A link relation pointing to the next page of a paged feed (Defined by RFC 5005 / Feed Paging and Archiving).
	 */
	public static final URI NEXT = URI.create("next");

	/**
	 * A link relation pointing to the previous page of a paged feed (Defined by RFC 5005 / Feed Paging and Archiving).
	 */
	public static final URI PREVIOUS = URI.create("previous");

	/**
	 * A link relation pointing on a resource where the entry can be modified (Defined by RFC 5023 / Atom Publishing Protocol)
	 */
	public static final URI EDIT = URI.create("edit");
}
