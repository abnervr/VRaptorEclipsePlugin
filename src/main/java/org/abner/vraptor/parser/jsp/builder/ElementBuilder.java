package org.abner.vraptor.parser.jsp.builder;

import org.abner.vraptor.parser.jsp.Attribute;
import org.abner.vraptor.parser.jsp.Element;
import org.abner.vraptor.parser.jsp.IElement;

public class ElementBuilder {

    private JspIterator iterator;

    private Element element;

    public ElementBuilder(JspIterator iterator) {
        this.iterator = iterator;
    }

    Element build(String value) {
        createElement(value);
        if (!value.endsWith(">")) {
            value = createAttributes();
        }
        if (!value.endsWith("/>")) {
            createChildElements();
        }
        return element;
    }

    private void createElement(String value) {
        element = new Element(parseName(value));
    }

    private String parseName(String value) {
        String name = value.substring(1);
        if (name.endsWith("/>")) {
            name = name.substring(0, name.length() - 2);
        } else if (name.endsWith(">")) {
            name = name.substring(0, name.length() - 1);
        }
        return name;
    }

    private String createAttributes() {
        while (iterator.hasNext()) {
            String value = iterator.next();

            Attribute attribute = AttributeBuilder.buildAttribute(value, iterator);
            element.addAttribute(attribute);
            if (attribute.getRawValue().endsWith(">")) {
                return attribute.getRawValue();
            }
        }
        return "";
    }

    private void createChildElements() {
        String elementEnd = "</" + element.getName();
        for (IElement child : IElementBuilder.buildElements(elementEnd, iterator)) {
            element.addChild(child);
        }
    }
}
