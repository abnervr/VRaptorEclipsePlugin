package org.abner.vraptor.jsp.dom;

import java.util.ArrayList;
import java.util.List;

import org.abner.vraptor.jsp.dom.builder.ExpressionBuilder;
import org.abner.vraptor.jsp.expression.Expression;

public class TextElement implements IElement {

    private List<TextValue> values = new ArrayList<>();

    private List<Expression> expressions;

    public List<Expression> getExpressions() {
        if (expressions == null) {
            expressions = ExpressionBuilder.parseExpressions(values);
        }
        return expressions;
    }

    public void append(String value, int colNumber, int lineNumber) {
        values.add(new TextValue(value, colNumber, lineNumber));
    }

    public List<TextValue> getValues() {
        return values;
    }

}
