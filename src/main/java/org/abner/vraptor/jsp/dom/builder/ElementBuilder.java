package org.abner.vraptor.jsp.dom.builder;

import org.abner.vraptor.jsp.dom.Attribute;
import org.abner.vraptor.jsp.dom.Element;
import org.abner.vraptor.jsp.dom.IElement;

public class ElementBuilder {

    private JspIterator iterator;

    private Element element;

    public ElementBuilder(JspIterator iterator) {
        this.iterator = iterator;
    }

    Element build(String value) {
        createElement(value);
        // <h2>
        if (!value.endsWith(">")) {
            value = createAttributes();
        }
        // <br/> <img href="/image.jpg"/>
        if (!value.endsWith("/>")
                        // <br><hr>
                        && iterator.findElementEnd(element)) {
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
        for (IElement child : IElementBuilder.buildElements(element, iterator)) {
            element.addChild(child);
        }
    }
}
