package org.abner.vraptor.controller;

import java.util.ArrayList;
import java.util.List;

public class ObjectReferenceFactory {

    private String methodSource;

    private int includeStart = 0;

    public ObjectReferenceFactory(String methodSource) {
        this.methodSource = methodSource;
    }

    private List<IncludedObject> create() {
        List<IncludedObject> references = new ArrayList<>();
        for (nextIncludeStart(); includeStart != -1; nextIncludeStart()) {
            String include = methodSource.substring(includeStart, findIncludeEnd());
            references.add(new IncludedObject(include));
        }
        return references;
    }

    private void nextIncludeStart() {
        includeStart = methodSource.indexOf(".include(", includeStart);
        if (includeStart != -1) {
            includeStart += ".include(".length();
        }
    }

    private int findIncludeEnd() {
        int includeEnd = methodSource.indexOf(')', includeStart);
        int parenthesisStart = methodSource.indexOf('(', includeStart);
        while (parenthesisStart != -1 && parenthesisStart < includeEnd) {
            includeEnd = methodSource.indexOf(')', includeEnd + 1);
            parenthesisStart = methodSource.indexOf('(', parenthesisStart + 1);
        }
        return includeEnd;
    }

    static List<IncludedObject> create(String source) {
        return new ObjectReferenceFactory(source).create();
    }

}
