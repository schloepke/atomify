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
package org.atomify.service;

import org.atomify.model.syndication.AtomDate;
import org.atomify.model.syndication.AtomId;
import org.atomify.model.syndication.AtomPerson;
import org.jbasics.pattern.delegation.Delegate;

public interface AtomEntityDelegate<T> extends Delegate<T> {

	/**
	 * Returns the path segment for the id. It is required that the path segment does not contain
	 * any slash characters (path separator) in order to be able to parse it.
	 * <p>
	 * The contract defines that the returned String
	 * <ul>
	 * <li>MUST NOT be null or empty</li>
	 * <li>MUST NOT contain slash characters</li>
	 * <li>SHOULD NOT contain whitespace characters</li>
	 * <li>SHOULD only contain ASC-II characters</li>
	 * <li>MUST NOT already be URL encoded</li>
	 * <li>MUST hold sufficient information to map back to the real entity identifer</li>
	 * </ul>
	 * 
	 * @return The path segment identifier for this entity.
	 */
	String getPathSegmentIdentifier();

	/**
	 * The atom id which must not be null.
	 * 
	 * @return The atom identifier of this entity which MUST NOT be null.
	 */
	AtomId getIdentifier();

	/**
	 * Returns the human readable name of the entity. The name must not be unique but should be in
	 * order to differ between two entities. The returned value MUST NOT be null.
	 * 
	 * @return The human readable name of the entity which MUST NOT be null.
	 */
	String getEntityName();

	/**
	 * Returns the date the entity was created. The returned value SHOULD NOT be null.
	 * 
	 * @return Returns the date when the entity was created (optional and can be null but should
	 *         not)
	 */
	AtomDate getCreated();

	/**
	 * Returns the date the entity was last modified. The returned value MUST NOT be null.
	 * 
	 * @return The last modified date which MUST NOT be null.
	 */
	AtomDate getLastModified();

	/**
	 * Returns the AtomPerson for the entity. The AtomPerson can be null but if so the atom syndication format
	 * requires the feed to have an author. You SHOULD NOT return a null person.
	 * 
	 * @return The user who last modified the entity (SHOULD NOT be null)
	 */
	AtomPerson getLastModifiedUser();

}
