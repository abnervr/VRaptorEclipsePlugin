package org.abner.vraptor.jsp.dom.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.abner.vraptor.jsp.Location;
import org.abner.vraptor.jsp.dom.TextValue;
import org.abner.vraptor.jsp.expression.Expression;
import org.abner.vraptor.jsp.expression.ExpressionFactory;

public class ExpressionBuilder {

    public static List<Expression> parseExpressions(List<TextValue> values) {
        ExpressionBuilder builder = new ExpressionBuilder();
        return builder.build(values.iterator());
    }

    private String currentExpression;

    private int currentExpressionStart;

    private List<Expression> expressions = new ArrayList<>();

    private List<Expression> build(Iterator<TextValue> iterator) {
        while (iterator.hasNext()) {
            TextValue textValue = iterator.next();
            if (currentExpression != null) {
                mergeExpression(textValue);
            } else {
                findExpression(textValue);
            }
        }
        return expressions;
    }

    private void findExpression(TextValue textValue) {
        findExpressionStart(textValue, 0);
    }

    private void findExpressionStart(TextValue textValue, int start) {
        int expressionStart = textValue.getValue().indexOf("${", start);
        if (expressionStart != -1) {
            findExpressionEnd(textValue, expressionStart);
        }
    }

    private void findExpressionEnd(TextValue textValue, int expressionStart) {
        int expressionEnd = textValue.getValue().indexOf('}', expressionStart);
        if (expressionEnd != -1) {
            String expression = textValue.getValue().substring(expressionStart, expressionEnd + 1);

            build(expression, textValue.getLineNumber(), expressionStart, expressionEnd);
            findExpressionStart(textValue, expressionEnd);
        } else {
            // Creates an expression that is not yet finished
            currentExpression = textValue.getValue().substring(expressionStart);
            currentExpressionStart = textValue.getColNumber() + expressionStart;
        }
    }

    private void mergeExpression(TextValue textValue) {
        String value = textValue.getValue();
        int expressionEnd = value.indexOf('}');
        if (expressionEnd != -1) {
            appendCurrentExpression(value.substring(0, expressionEnd + 1));

            build(currentExpression, textValue.getLineNumber(), currentExpressionStart, textValue.getColNumber() + expressionEnd);
            currentExpression = null;
            findExpressionStart(textValue, expressionEnd);
        } else {
            appendCurrentExpression(value);
        }
    }

    private void appendCurrentExpression(String value) {
        currentExpression += " " + value;
    }

    private void build(String expressionValue, int lineNumber, int expressionStart, int expressionEnd) {
        Location location = new Location(lineNumber, expressionStart, expressionEnd);

        Expression expression = ExpressionFactory.create(expressionValue, location);
        expressions.add(expression);
    }
}
