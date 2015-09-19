package org.abner.vraptor.parser;

import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.SearchPattern;

import org.abner.vraptor.jsp.Jsp;

public class PatternHelper {

    public static SearchPattern createByClassName(String className) {
        return SearchPattern.createPattern(className, IJavaSearchConstants.CLASS,
                        IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
    }

    public static SearchPattern createByJsp(Jsp jsp) {
        return SearchPattern.createPattern(jsp.getCurrentDirectory(), IJavaSearchConstants.CLASS,
                        IJavaSearchConstants.DECLARATIONS, SearchPattern.R_PREFIX_MATCH);
    }

}
