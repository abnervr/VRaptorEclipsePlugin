package org.abner.vraptor.parser.jsp;

import java.util.ArrayList;
import java.util.List;

import org.abner.vraptor.jsp.expression.Expression;
import org.abner.vraptor.parser.jsp.builder.ExpressionBuilder;

public class TextElement implements IElement {

    private List<TextValue> values = new ArrayList<>();

    private List<Expression> expressions;

    public List<Expression> getExpressions() {
        if (expressions == null) {
            expressions = ExpressionBuilder.parseExpressions(values);
        }
        return expressions;
    }

    public void append(String value, int colNumber) {
        values.add(new TextValue(value, colNumber));
    }

}
