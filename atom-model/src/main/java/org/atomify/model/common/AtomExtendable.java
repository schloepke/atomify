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
package org.atomify.model.common;

import org.atomify.model.extension.AtomExtension;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by stephan on 03.06.16.
 */
public class AtomExtendable extends AtomCommonAttributes  {
    /**
     * <b>Optional:</b> atom extensions.
     */
    private List<AtomExtension> extensions;

    public AtomExtendable() {
        this.extensions = Collections.emptyList();
    }

    /**
     * Returns the lazy initialized collection of extensions.
     *
     * @return the extensions
     */
    public List<AtomExtension> getExtensions() {
        return this.extensions;
    }

    protected void setExtensions(List<AtomExtension> extensions) {
        if (extensions == null || extensions.isEmpty()) {
            this.extensions = Collections.emptyList();
        } else {
            this.extensions = Collections.unmodifiableList(new ArrayList<AtomExtension>(extensions));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AtomExtendable that = (AtomExtendable) o;
        return extensions.equals(that.extensions);

    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + (extensions != null ? extensions.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "extensions=" + this.extensions;
    }

    protected void serializeExtensions(ContentHandler handler, AttributesImpl attributes) throws SAXException {
        for (AtomExtension extension : this.extensions) {
            extension.serialize(handler, attributes);
        }
    }
}
