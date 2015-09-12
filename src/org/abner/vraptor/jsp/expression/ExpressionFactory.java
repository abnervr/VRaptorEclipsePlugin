package org.abner.vraptor.jsp.expression;

import org.abner.vraptor.jsp.Location;

public class ExpressionFactory {

    public static Expression create(String expression, Location location) {
        if (expression.contains("linkTo[")) {
            return new LinkToExpression(expression, location);
        } else if (expression.contains(".")) {
            // TODO
            return null;
        } else {
            return null;
        }
    }
}
