package org.abner.vraptor.jsp.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Reference to an included object
 * 
 * @author abner
 *
 */
public class ExpressionReference {

    private static final String[] IGNORE_EXPRESSIONS = {
                    "not", "empty", "eq", "lt", "gt", "true", "false", "and", "or",
                    "?", "==", "<=", ">=", ">", "<", ":", "-", "+", "!", "("
    };

    static {
        Arrays.sort(IGNORE_EXPRESSIONS);
    }

    private String[] references;

    public ExpressionReference(String expression) {
        List<String> expressions = new ArrayList<>(Arrays.asList(expression.split("\\.")));
        expressions = removeIgnoreExpressions(expressions);
        expressions = removeUnaryFunctions(expressions);
        references = expressions.toArray(new String[expressions.size()]);
    }

    private List<String> removeIgnoreExpressions(List<String> expressions) {
        for (Iterator<String> iterator = expressions.iterator(); iterator.hasNext();) {
            String value = iterator.next();
            if (Arrays.binarySearch(IGNORE_EXPRESSIONS, value.toLowerCase()) >= 0) {
                iterator.remove();
            }
        }
        return expressions;
    }

    private List<String> removeUnaryFunctions(List<String> expressions) {
        for (int i = 0; i < expressions.size(); i++) {
            String e = removeUnaryFunctions(expressions.get(i));
            if (!e.matches("[a-zA-Z]+.*")) {
                expressions.remove(i--);
            } else {
                expressions.set(i, e);
            }
        }
        return expressions;
    }

    private String removeUnaryFunctions(String expression) {
        if (expression.startsWith("!")) {
            return removeUnaryFunctions(expression.substring(1));
        } else if (expression.startsWith("(")) {
            return removeUnaryFunctions(expression.substring(1));
        } else if (expression.endsWith(")") && !expression.endsWith("()")) {
            return expression.substring(0, expression.length() - 1);
        }
        return expression;
    }

    public String getSegment(int value) {
        return references[value];
    }

    public int getSegmentCount() {
        return references.length;
    }

}
