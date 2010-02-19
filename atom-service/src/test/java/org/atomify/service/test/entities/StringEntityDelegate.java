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
package org.atomify.service.test.entities;

import java.util.Date;

import org.atomify.model.syndication.AtomDate;
import org.atomify.model.syndication.AtomId;
import org.atomify.model.syndication.AtomPerson;
import org.atomify.service.AtomEntityDelegate;

public class StringEntityDelegate implements AtomEntityDelegate<String> {
	private static final AtomPerson LAST_MOD = AtomPerson.valueOf("test-system");

	private final int index;
	private final AtomId identifier;
	private final AtomDate createdAndModified;
	private final String entityName;
	private final String entityContent;

	public StringEntityDelegate(int index) {
		this.index = index;
		this.identifier = AtomId.valueOf("urn:atomify:test:" + this.index);
		this.createdAndModified = AtomDate.valueOf(new Date());
		this.entityName = "Test Entity with ID " + this.identifier.getId();
		this.entityContent = "This is a test entity not useful for anything else but testing";
	}

	public AtomId getIdentifier() {
		return this.identifier;
	}

	public String getPathSegmentIdentifier() {
		return Integer.toHexString(this.index);
	}

	public String getEntityName() {
		return this.entityName;
	}

	public AtomDate getCreated() {
		return this.createdAndModified;
	}

	public AtomDate getLastModified() {
		return this.createdAndModified;
	}

	public AtomPerson getLastModifiedUser() {
		return LAST_MOD;
	}

	public String delegate() {
		return this.entityContent;
	}

}
