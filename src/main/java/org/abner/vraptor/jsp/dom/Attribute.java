package org.abner.vraptor.jsp.dom;

public class Attribute {

    private String rawValue;

    private String name;

    private Object value;

    public Attribute(String rawValue) {
        this(rawValue, null, null);
    }

    public Attribute(String rawValue, String name, Object value) {
        this.rawValue = rawValue;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getRawValue() {
        return rawValue;
    }


}
