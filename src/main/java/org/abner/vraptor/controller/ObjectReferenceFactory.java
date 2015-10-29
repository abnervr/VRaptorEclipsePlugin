package org.abner.vraptor.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;

public class ObjectReferenceFactory {

    private IMethod method;

    private String methodSource;

    private int includeStart = 0;

    public ObjectReferenceFactory(IMethod methodReference) throws JavaModelException {
        this.method = methodReference;
        this.methodSource = methodReference.getSource();
    }

    private List<IncludedObject> create() {
        List<IncludedObject> references = new ArrayList<>();
        for (nextIncludeStart(); includeStart != -1; nextIncludeStart()) {
            String include = methodSource.substring(includeStart, findIncludeEnd());
            String name = getIncludedObjectName(include);
            String field = getIncludedObjectField(include);
            references.add(new IncludedObject(method, name, field));
        }
        return references;
    }

    private String getIncludedObjectField(String include) {
        if (include.indexOf(',') != -1) {
            return include.substring(include.indexOf(',') + 1).trim();
        }
        return null;
    }

    private String getIncludedObjectName(String include) {
        if (include.indexOf(',') != -1) {
            String name = include.substring(0, include.indexOf(',')).trim();
            if (name.startsWith("\"") && name.endsWith("\"")) {
                name = name.substring(1, name.length() - 1);
            }
            return name;
        } else {
            return include;
        }
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

    static List<IncludedObject> create(IMethod methodReference) throws JavaModelException {
        return new ObjectReferenceFactory(methodReference).create();
    }

}
