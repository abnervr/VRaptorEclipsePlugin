package org.abner.vraptor.jsp.dom.builder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.abner.vraptor.jsp.dom.Element;

public class JspIterator {

    private static final int LINE_FEED_SIZE = 1;

    public static final JspIterator EMPTY_ITERATOR = new JspIterator();

    private List<String> lines = new ArrayList<>();
    private int lineIndex = 0;
    private int colNumber = 0;
    private LineIterator lineIterator;

    public JspIterator(InputStream is) {
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        }
    }

    private JspIterator() {}

    public boolean hasNext() {
        if (lineHasNext()) {
            return true;
        }
        while (lineIndex < lines.size()) {
            lineIterator = new LineIterator(lines.get(lineIndex));
            if (lineHasNext()) {
                return true;
            }
        }
        return false;
    }

    private boolean lineHasNext() {
        if (lineIterator != null) {
            if (lineIterator.hasNext()) {
                return true;
            } else {
                colNumber += lineIterator.getColIndex() + LINE_FEED_SIZE;
                lineIndex++;
                lineIterator = null;
            }
        }
        return false;
    }

    public String next() {
        return lineIterator.next();
    }

    public int getColNumber() {
        if (lineIterator != null) {
            return colNumber + lineIterator.getColIndex();
        } else {
            return colNumber;
        }
    }

    public int getLineNumber() {
        return lineIndex + 1;
    }

    public boolean findElementEnd(Element element) {
        int index = lineIndex;
        // TODO check if parent element ends before the current element
        while (index < lines.size()) {
            String line = lines.get(index);
            if (index == lineIndex) {
                line = line.substring(lineIterator.getColIndex());
            }
            if (line.indexOf("</" + element.getName()) != -1) {
                return true;
            }
            index++;
        }
        System.out.printf("Element end not found %s [%d]\n", element.getName(), getColNumber());
        return false;
    }
}
