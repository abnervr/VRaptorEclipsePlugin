package org.abner.vraptor.parser.jsp;

import java.util.ArrayList;
import java.util.List;

public class Element implements IElement {

    private String name;

    private List<Attribute> attributes = new ArrayList<>();

    private List<IElement> children = new ArrayList<>();

    public Element(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public void addChild(IElement child) {
        children.add(child);
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<IElement> getChildren() {
        return children;
    }

}
