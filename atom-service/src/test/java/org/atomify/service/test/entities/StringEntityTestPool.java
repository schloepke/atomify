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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.atomify.service.PagedEntityAccessor;

public class StringEntityTestPool implements PagedEntityAccessor<StringEntityDelegate> {
	public static StringEntityTestPool SHARED_INSTANCE = new StringEntityTestPool();

	private final List<StringEntityDelegate> fixedData;

	public StringEntityTestPool() {
		List<StringEntityDelegate> temp = new ArrayList<StringEntityDelegate>();
		for (int i = 0; i < 100; i++) {
			temp.add(new StringEntityDelegate(i + 1));
		}
		this.fixedData = Collections.unmodifiableList(temp);
	}

	public StringEntityDelegate queryEntity(String identifierPathSegment) {
		try {
			int index = Integer.parseInt(identifierPathSegment);
			if (index > 0 && index <= this.fixedData.size()) {
				return this.fixedData.get(index - 1);
			} else {
				return null;
			}
		} catch (RuntimeException e) {
			return null;
		}
	}

	public List<StringEntityDelegate> queryPagedEntities(int pageSize, int page) {
		int start = page * pageSize;
		if (start < this.fixedData.size()) {
			int end = start + pageSize;
			if (end > this.fixedData.size()) {
				end = this.fixedData.size();
			}
			return this.fixedData.subList(start, end);
		} else {
			return Collections.emptyList();
		}
	}

}
