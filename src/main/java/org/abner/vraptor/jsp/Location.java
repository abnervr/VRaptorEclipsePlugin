package org.abner.vraptor.jsp;

public class Location {

    private int lineNumber;

    private int startColNumber;

    private int endColNumber;

    public Location(int lineNumber, int startColNumber) {
        this(lineNumber, startColNumber, -1);
    }

    public Location(int lineNumber, int startColNumber, int endColNumber) {
        this.lineNumber = lineNumber;
        this.startColNumber = startColNumber;
        this.endColNumber = endColNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getStartColNumber() {
        return startColNumber;
    }

    public int getEndColNumber() {
        return endColNumber;
    }


}
