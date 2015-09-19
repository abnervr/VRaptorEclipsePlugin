package org.abner.vraptor.parser.jsp.builder;

import org.abner.vraptor.parser.jsp.Document;
import org.abner.vraptor.parser.jsp.IElement;

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
