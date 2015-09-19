package org.abner.vraptor.parser.jsp.builder;

public class LineIterator {

    private String line;
    private String parts[];
    private int currentIndex = 0;
    private int partIndex = -1;

    public LineIterator(String line) {
        this.line = line;
        if (!line.trim().isEmpty()) {
            this.parts = line.trim().split("\\s+");
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
