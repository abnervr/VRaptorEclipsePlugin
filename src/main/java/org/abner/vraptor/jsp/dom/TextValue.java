package org.abner.vraptor.jsp.dom;

public class TextValue {

    private String value;

    private int colNumber;

    private int lineNumber;

    public TextValue(String value, int colNumber, int lineNumber) {
        this.value = value;
        this.colNumber = colNumber;
        this.lineNumber = lineNumber;
    }

    public String getValue() {
        return value;
    }

    public int getColNumber() {
        return colNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return value;
    }

}
