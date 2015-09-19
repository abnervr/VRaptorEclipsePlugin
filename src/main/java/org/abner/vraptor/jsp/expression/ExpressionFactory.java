package org.abner.vraptor.jsp.expression;

import org.abner.vraptor.jsp.Location;

public class ExpressionFactory {

    public static Expression create(String expression, Location location) {
        if (expression.contains("linkTo[")) {
            return new LinkToExpression(expression, location);
        } else {
            ObjectReferenceExpression objectExpression = new ObjectReferenceExpression(expression, location);
            if (!objectExpression.getReferences().isEmpty()) {
                return objectExpression;
            }
        }
        return new EmptyExpression(expression, location);
    }
}
