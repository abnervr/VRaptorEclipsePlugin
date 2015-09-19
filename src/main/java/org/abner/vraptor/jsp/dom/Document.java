package org.abner.vraptor.jsp.dom;

import java.util.ArrayList;
import java.util.List;

public class Document {

    private List<IElement> elements = new ArrayList<>();

    public void addElement(IElement element) {
        elements.add(element);
    }

    public List<IElement> getElements() {
        return elements;
    }
}
