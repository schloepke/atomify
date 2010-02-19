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
package org.atomify.service.test.resources;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Path;
import javax.ws.rs.core.UriInfo;

import org.atomify.model.syndication.AtomContent;
import org.atomify.model.syndication.AtomDate;
import org.atomify.model.syndication.AtomEntryBuilder;
import org.atomify.model.syndication.AtomFeedBuilder;
import org.atomify.model.syndication.AtomId;
import org.atomify.model.syndication.AtomText;
import org.atomify.service.AtomEntityCollectionResource;
import org.atomify.service.PagedEntityAccessor;
import org.atomify.service.annotations.AtomPubServiceEntry;
import org.atomify.service.test.entities.StringEntityDelegate;
import org.atomify.service.test.entities.StringEntityTestPool;

@Path("collection")
@AtomPubServiceEntry(workspace = "Test Workspace", collection = "Simple CollectioN")
public class AtomCollectionTestResource extends AtomEntityCollectionResource<String, StringEntityDelegate> {
	private static final AtomId TEST_FEED_ID = AtomId.valueOf("urn:atomify:test:feed:" + UUID.randomUUID());
	private static final AtomText TEST_FEED_NAME = AtomText.newBuilder().setContent("Atomify Test Feed").build();

	@Override
	public PagedEntityAccessor<StringEntityDelegate> createPagedEntityAccessor(Map<String, ?> parameters) {
		return StringEntityTestPool.SHARED_INSTANCE;
	}

	@Override
	public AtomEntryBuilder fillEntryContent(AtomEntryBuilder entryBuilder, String entity) {
		return entryBuilder.setContent(AtomContent.newBuilder().setContent(entity).build());
	}

	@Override
	public AtomEntryBuilder fillFeedEntryContent(AtomEntryBuilder entryBuilder, String entity) {
		return fillEntryContent(entryBuilder, entity);
	}

	@Override
	public AtomFeedBuilder fillFeed(AtomFeedBuilder feedBuilder, UriInfo uriInfo) {
		return feedBuilder.setId(TEST_FEED_ID).setTitle(TEST_FEED_NAME).setUpdated(AtomDate.valueOf(new Date()));
	}

}
