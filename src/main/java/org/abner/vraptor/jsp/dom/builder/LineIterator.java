package org.abner.vraptor.jsp.dom.builder;

public class LineIterator {

    private String line;
    private String parts[];
    private int currentIndex = 0;
    private int partIndex = -1;

    public LineIterator(String line) {
        this.line = line;
        if (!line.trim().isEmpty()) {
            line = line.replaceAll("(\\<[a-zA-z0-9:]+\\>)", " $1 ");
            line = line.replaceAll("(\\<[a-zA-z0-9:])", " $1");
            line = line.replaceAll("\\>(\\S)", "> $1");
            line = line.replaceAll("(\\</[a-zA-z0-9:]+\\>)", " $1 ");
            line = line.trim();
            this.parts = line.split("\\s+");
        } else {
            this.parts = new String[0];
        }
    }

    boolean hasNext() {
        return ++partIndex < parts.length;
    }

    String next() {
        return parts[partIndex];
    }

    int getColIndex() {
        if (partIndex < parts.length) {
            return currentIndex + line.substring(currentIndex).indexOf(parts[partIndex]);
        } else {
            return line.length();
        }
    }

}
