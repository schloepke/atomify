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
package org.atomify.service;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.atomify.model.AtomRelations;
import org.atomify.model.syndication.AtomEntry;
import org.atomify.model.syndication.AtomEntryBuilder;
import org.atomify.model.syndication.AtomFeed;
import org.atomify.model.syndication.AtomFeedBuilder;
import org.atomify.model.syndication.AtomLink;

/**
 * Base class to implement a paged {@link AtomFeed} resource with functionality to get entries.
 * <p>
 * In the terminology of Atom Publishing this describes a collection where you can get a feed and also entries as well
 * as put and post to edit the feed and/or the entries. In the terminology of Atom Syndication this represents a feed
 * with the ability to also return the entries as well as editing them.
 * </p>
 * <p>
 * As implementor you need to derive the class and implement the abstract methods. A more detailed description will have
 * to follow here and in a separate how to document. Do not expect this to be present until after version 1.0 is
 * released.
 * </p>
 * 
 * @author Stephan Schloepke
 * @since 1.0
 */
public abstract class AtomCollectionResource {

	/**
	 * Returns the Feed to be rendered for the request.
	 * 
	 * @param currentPage The page of the feed request given as query parameter page.
	 * @return The feed for the request.
	 */
	@GET
	@Produces(AtomFeed.MEDIA_TYPE_STRING)
	public final AtomFeed getFeed(@QueryParam("page") @DefaultValue("0") final int currentPage, @Context final UriInfo uriInfo) {
		int pageSize = getPageSize();
		AtomFeedBuilder feedBuilder = fillFeed(AtomFeed.newBuilder(), uriInfo);
		filleEntries(feedBuilder, uriInfo, pageSize, currentPage);
		if (pageSize > 0) {
			if (feedBuilder.getEntries().size() == pageSize) {
				feedBuilder.addLink(AtomLink.newBuilder().setHref(
						uriInfo.getAbsolutePathBuilder().queryParam("page", Integer.valueOf(currentPage + 1)).build()).setRel(
						AtomRelations.NEXT).setType(AtomFeed.MEDIA_TYPE).setTitle("Next page").build());
			}
			if (currentPage > 0) {
				feedBuilder.addLink(AtomLink.newBuilder().setHref(
						uriInfo.getAbsolutePathBuilder().queryParam("page", Integer.valueOf(currentPage - 1)).build()).setRel(
						AtomRelations.PREVIOUS).setType(AtomFeed.MEDIA_TYPE).setTitle("Previous page").build());
				feedBuilder.addLink(AtomLink.newBuilder().setHref(
						uriInfo.getAbsolutePathBuilder().queryParam("page", Integer.valueOf(0)).build()).setRel(AtomRelations.FIRST).setType(
						AtomFeed.MEDIA_TYPE).setTitle("First page").build());
			}
		}
		return feedBuilder.build();
	}

	/**
	 * Returns a single entry of the feed with the URI feedUri/entryId.
	 * 
	 * @param entryId The entry id to return.
	 * @return The entry for the request.
	 */
	@GET
	@Produces(AtomEntry.MEDIA_TYPE_STRING)
	@Path("{entryId}")
	public final AtomEntry getFeedEntry(@PathParam("entryId") final String entryId, @Context final UriInfo uriInfo) {
		return fillEntry(AtomEntry.newBuilder(), uriInfo, entryId).build();
	}

	/**
	 * Returns the used page size (defaults to 20) or a value less than or equal to zero indicating
	 * no automatic paging required.
	 * <p>
	 * The page size is the amount of entries in one page for a paged feed. Overwrite this method to change the default
	 * page size of 20 entries. If you return a value less than or equal to zero the automatic generation of paging
	 * links is disabled. The implementor deriving from this class can still set the page links them self using the page
	 * number as query parameter "page".
	 * </p>
	 * 
	 * @return The page size for the feed or minus one to disable automatic paging.
	 */
	public int getPageSize() {
		return 20;
	}

	/**
	 * Returns the total amount of elements in the feed or minus one to indicate that the total
	 * amount is unknown.
	 * <p>
	 * Overwrite this method in order to give the paged feed the opportunity to let the caller know what the last page
	 * is. The total amount is divided by the page size to determine the last page and give this as link in the feed. If
	 * either the total amount or the page size is minus one the link to the last page will not appear.
	 * </p>
	 * 
	 * @return Returns the total numbers of entries in this feed or minus one if the total amount is
	 *         unknown.
	 */
	public int getTotalSize() {
		return -1;
	}

	/**
	 * Fill the feed. This is called right at the beginning when the feed is to be created.
	 * <p>
	 * When implementing this method use it to fill feed related things like feed links, feed title, feed id and so on.
	 * </p>
	 * 
	 * @param feedBuilder The feed builder building the feed.
	 * @param uriInfo The uri info of the request.
	 * @param mandantId The mandant id of the request as given in the path parameter mandant.
	 * @return The feed builder as given in the parameter list (builder pattern).
	 */
	public abstract AtomFeedBuilder fillFeed(final AtomFeedBuilder feedBuilder, final UriInfo uriInfo);

	/**
	 * Fill the entries of the feed. Called to fill the entris of the fedd for the given page size
	 * and the given page.
	 * 
	 * @param feedBuilder The feed builder to fill the entries with.
	 * @param uriInfo The uri info of the request.
	 * @param mandantId The mandant as given in the path parameter mandant.
	 * @param pageSize The page size for the filling of the feed.
	 * @param page The page to fill.
	 * @return The feed builder as given in the parameter list (builder pattern).
	 */
	public abstract AtomFeedBuilder filleEntries(final AtomFeedBuilder feedBuilder, final UriInfo uriInfo, final int pageSize, final int page);

	/**
	 * Fill an entry for an entry request. Use this to fill a complete entry not to fill the entry
	 * for a feed.
	 * 
	 * @param entryBuilder The entry builder to use creating the entry.
	 * @param uriInfo The uri info of the request.
	 * @param mandantId The mandant as given in the path parameter mandant.
	 * @param entryId The entry id as given in the path parameter entry.
	 * @return The feed builder as supplied in the parameters (builder pattern).
	 */
	public abstract AtomEntryBuilder fillEntry(final AtomEntryBuilder entryBuilder, final UriInfo uriInfo, final String entryId);

}
