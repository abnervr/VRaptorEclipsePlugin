package org.abner.vraptor.jsp.dom;

public class TextValue {

    private String value;

    private int colNumber;

    public TextValue(String value, int colNumber) {
        this.value = value;
        this.colNumber = colNumber;
    }

    public String getValue() {
        return value;
    }

    public int getColNumber() {
        return colNumber;
    }

    @Override
    public String toString() {
        return value;
    }

}
