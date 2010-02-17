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

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.atomify.model.AtomRelations;
import org.atomify.model.syndication.AtomEntry;
import org.atomify.model.syndication.AtomEntryBuilder;
import org.atomify.model.syndication.AtomFeedBuilder;
import org.atomify.model.syndication.AtomLink;
import org.atomify.model.syndication.AtomPerson;
import org.atomify.model.syndication.AtomText;

/**
 * An Atom Feed resource base class. Derive this class to provide an atom feed in your application.
 * 
 * @author Stephan Schloepke
 * @param <T> The type this entity feed resource uses as entity.
 */
public abstract class AtomEntityCollectionResource<Entity, EntityDelegate extends AtomEntityDelegate<Entity>> extends AtomCollectionResource {
	private static final Map<String, ?> EMPTY_PARAMETERS = Collections.emptyMap();

	/**
	 * Fill the {@link AtomFeedBuilder} with the entries.
	 * 
	 * @param feedBuilder The feed builder to add the entries to.
	 * @param uriInfo The uriInfo of the request.
	 * @param mandantId The mandantId of the request.
	 * @param pageSize The size of the page to use when getting the feed data.
	 * @param page The page number to fill data with.
	 * @return The atom feed builder given as a parameter (builder pattern).
	 * @see AtomCollectionResource.rms.atom.service.AtomSyndicatonFeedResource#filleEntries(de.rms.atom.model.AtomFeedBuilder,
	 *      javax.ws.rs.core.UriInfo, java.lang.String, int, int)
	 */
	@Override
	public final AtomFeedBuilder filleEntries(final AtomFeedBuilder feedBuilder, final UriInfo uriInfo, final int pageSize, final int page) {
		PagedEntityAccessor<EntityDelegate> entityAccessor = createPagedEntityAccessor(EMPTY_PARAMETERS);
		for (EntityDelegate entity : entityAccessor.queryPagedEntities(pageSize, page)) {
			URI entryLink = uriInfo.getAbsolutePathBuilder().path("{entry}").build(entity.getPathSegmentIdentifier()).normalize();
			AtomEntryBuilder entry = AtomEntry.newBuilder().setId(entity.getIdentifier()).addLink(
					AtomLink.newBuilder().setHref(entryLink).setRel(AtomRelations.ALTERNATE).build()).setUpdated(entity.getLastModified()).setTitle(
					AtomText.newBuilder().setTextContent(entity.getEntityName()).build());
			if (entity.getCreated() != null) {
				entry.setPublished(entity.getCreated());
			}
			entry = fillFeedEntryContent(entry, entity.delegate());
			if (entry.getAuthors().size() == 0) {
				AtomPerson author = entity.getLastModifiedUser();
				if (author != null) {
					entry.addAuthor(author);
				}
			}
			feedBuilder.addEntry(entry.build());
		}
		return feedBuilder;
	}

	/**
	 * Fill an entry with the entity.
	 * 
	 * @param entryBuilder The builder for the entry.
	 * @param uriInfo The URI info of the request.
	 * @param mandantId The mandantId of the request.
	 * @param entryId The entryId of the request.
	 * @return The entry builder given as parameter (builder pattern).
	 * @see AtomCollectionResource.rms.atom.jaxrs.AtomSyndicatonFeedResource#fillEntry(de.rms.atom.jaxrs.AtomEntryBuilder,
	 *      javax.ws.rs.core.UriInfo, java.lang.String, java.lang.String)
	 */
	@Override
	public final AtomEntryBuilder fillEntry(final AtomEntryBuilder entryBuilder, final UriInfo uriInfo, final String entryIdString) {
		PagedEntityAccessor<EntityDelegate> entityAccessor = createPagedEntityAccessor(EMPTY_PARAMETERS);
		EntityDelegate entity = entityAccessor.queryEntity(entryIdString);
		if (entity == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		URI entryLink = uriInfo.getAbsolutePathBuilder().build().normalize();
		AtomEntryBuilder entry = AtomEntry.newBuilder().setId(entity.getIdentifier()).addLink(
				AtomLink.newBuilder().setHref(entryLink).setRel(AtomRelations.ALTERNATE).build()).setUpdated(entity.getLastModified()).setTitle(
				AtomText.newBuilder().setTextContent(entity.getEntityName()).build());
		if (entity.getCreated() != null) {
			entry.setPublished(entity.getCreated());
		}
		entry = fillFeedEntryContent(entry, entity.delegate());
		if (entry.getAuthors().size() == 0) {
			AtomPerson author = entity.getLastModifiedUser();
			if (author != null) {
				entry.addAuthor(author);
			}
		}
		return entry;
	}

	/**
	 * Creates a paged entity accessor used by this feed producer to load the entities.
	 * 
	 * @param mandandId The mandantId of the request.
	 * @return The created paged entity accessor (must ot be null).
	 */
	public abstract PagedEntityAccessor<EntityDelegate> createPagedEntityAccessor(final Map<String, ?> parameters);

	/**
	 * Called to fill an entry in the feed (summary filling usually). Subclasses have to implement
	 * this method to support content filling.
	 * 
	 * @param entryBuilder The entry builder to add the content to.
	 * @return The given entry builder (builder pattern).
	 */
	public abstract AtomEntryBuilder fillFeedEntryContent(AtomEntryBuilder entryBuilder, Entity entity);

	/**
	 * Called to fill a complete entry (called directly so not summarized). Subclasses have to
	 * implement this method to support content filling.
	 * 
	 * @param entryBuilder The entry builder to add the content to.
	 * @return The given entry builder (builder pattern).
	 */
	public abstract AtomEntryBuilder fillEntryContent(AtomEntryBuilder entryBuilder, Entity entity);

}
