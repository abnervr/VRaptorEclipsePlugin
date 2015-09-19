package org.abner.vraptor.jsp.dom.builder;

import org.abner.vraptor.jsp.dom.Document;
import org.abner.vraptor.jsp.dom.IElement;

public class DocumentBuilder {

    private Document document = new Document();

    public Document build(JspIterator iterator) {
        try {
            for (IElement element : IElementBuilder.buildElements(iterator)) {
                document.addElement(element);
            }
            return document;
        } finally {
            iterator.close();
        }
    }


}
