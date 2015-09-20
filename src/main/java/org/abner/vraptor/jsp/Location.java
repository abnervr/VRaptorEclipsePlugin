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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + endColNumber;
        result = prime * result + lineNumber;
        result = prime * result + startColNumber;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Location other = (Location) obj;
        if (endColNumber != other.endColNumber) {
            return false;
        }
        if (lineNumber != other.lineNumber) {
            return false;
        }
        if (startColNumber != other.startColNumber) {
            return false;
        }
        return true;
    }


}
