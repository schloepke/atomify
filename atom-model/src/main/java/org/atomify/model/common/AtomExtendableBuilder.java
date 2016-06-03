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

import org.atomify.model.AtomContractConstraint;
import org.atomify.model.extension.AtomExtension;
import org.jbasics.parser.annotations.AnyElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by stephan on 03.06.16.
 */
public class AtomExtendableBuilder<T extends AtomExtendableBuilder<?>> extends AtomCommonBuilder<T> {
    /**
     * <b>Optional:</b> atom extensions.
     */

    protected List<AtomExtension> extensions;

    @AnyElement
    @SuppressWarnings("unchecked")
    public T addExtension(AtomExtension extension) {
        if (this.extensions == null) {
            this.extensions = new ArrayList<AtomExtension>();
        }
        this.extensions.add(AtomContractConstraint.notNull("extension", extension));
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T addExtensions(Collection<AtomExtension> extensions) {
        if (this.extensions == null) {
            this.extensions = new ArrayList<AtomExtension>();
        }
        this.extensions.addAll(AtomContractConstraint.notNull("extensions", extensions));
        return (T) this;
    }

    protected final <AT extends AtomExtendable> AT attachParentBuilder(AT instance) {
        super.attachParentBuilder(instance).setExtensions(this.extensions);
        return instance;
    }

}
