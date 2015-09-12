package org.abner.vraptor.parser;

import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.SearchPattern;

public class PatternHelper {

    public static SearchPattern createByClassName(String className) {
        return SearchPattern.createPattern(className, IJavaSearchConstants.CLASS,
                        IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
    }

    public static SearchPattern createByAnnotation(String path) {
        return SearchPattern.createPattern("@Resource", IJavaSearchConstants.ANNOTATION_TYPE,
                        IJavaSearchConstants.REFERENCES, SearchPattern.R_EXACT_MATCH);
    }
}
