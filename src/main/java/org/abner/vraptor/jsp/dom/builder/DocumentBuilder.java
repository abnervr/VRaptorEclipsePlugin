package org.abner.vraptor.jsp.dom.builder;

import java.io.InputStream;

import org.abner.vraptor.jsp.dom.Document;
import org.abner.vraptor.jsp.dom.IElement;

public class DocumentBuilder {
 
    public static Document build(InputStream is) {
        return build(new JspIterator(is));
    }


    private static Document build(JspIterator iterator) {
        Document document = new Document();
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
