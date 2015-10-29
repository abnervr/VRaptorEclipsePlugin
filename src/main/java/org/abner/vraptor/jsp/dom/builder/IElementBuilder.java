package org.abner.vraptor.jsp.dom.builder;

import java.util.ArrayList;
import java.util.List;

import org.abner.vraptor.jsp.dom.Element;
import org.abner.vraptor.jsp.dom.IElement;
import org.abner.vraptor.jsp.dom.TextElement;

public class IElementBuilder {

    public static List<IElement> buildElements(JspIterator iterator) {
        return buildElements(null, iterator);
    }

    public static List<IElement> buildElements(Element element, JspIterator iterator) {
        List<IElement> elements = new ArrayList<>();
        while (iterator.hasNext()) {
            String value = iterator.next();

            if (isElementStart(value)) {
                elements.add(buildElement(iterator, value));
            } else {
                if (value.startsWith("Azulix")) {
                    System.out.println("Ahahahaha");
                }
                TextElement textElement = getLastTextElement(elements);
                textElement.append(value, iterator.getColNumber(), iterator.getLineNumber());
            }
        }
        return elements;
    }

    public static Element buildElement(JspIterator iterator, String value) {
        ElementBuilder builder = new ElementBuilder(iterator);
        return builder.build(value);
    }

    private static TextElement getLastTextElement(List<IElement> elements) {
        int lastIndex = elements.size() - 1;
        if (lastIndex >= 0 &&
                        elements.get(lastIndex) instanceof TextElement) {
            return (TextElement) elements.get(lastIndex);
        }
        TextElement textElement = new TextElement();
        elements.add(textElement);
        return textElement;
    }

    static boolean isElementStart(String value) {
        return value.matches("<[a-zA-Z][a-zA-Z0-9:]*>??");
    }

}
