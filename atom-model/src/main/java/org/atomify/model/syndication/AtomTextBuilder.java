package org.atomify.model.syndication;

import org.atomify.model.AtomCommonBuilder;
import org.atomify.model.syndication.AtomText.Type;
import org.jbasics.parser.annotations.Attribute;
import org.jbasics.parser.annotations.Content;
import org.jbasics.pattern.builder.Builder;

public class AtomTextBuilder extends AtomCommonBuilder<AtomTextBuilder> implements Builder<AtomText> {
	private AtomText.Type type;
	private String content;

	@Attribute(namespace = "", name = "type", required = true)
	public AtomTextBuilder setType(AtomText.Type type) {
		this.type = type;
		return this;
	}

	@Content(mixed = false)
	public AtomTextBuilder setContent(String content) {
		this.content = content;
		return this;
	}

	public AtomText build() {
		if (this.type == Type.xhtml) {
			throw new UnsupportedOperationException("XHtml type is not yet supported");
		}
		return new AtomPlainText(this.type == Type.html, this.content);
	}

}
