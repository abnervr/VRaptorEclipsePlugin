package org.abner.vraptor.jsp.dom.builder;

import java.util.ArrayList;
import java.util.List;

import org.abner.vraptor.jsp.dom.Attribute;
import org.abner.vraptor.jsp.dom.TextValue;
import org.abner.vraptor.jsp.expression.Expression;

public class AttributeBuilder {

    public static Attribute buildAttribute(String attribute, JspIterator iterator) {
        return new AttributeBuilder(attribute).build(iterator);
    }

    private String rawValue;
    private List<TextValue> values = new ArrayList<>();
    private char delimiter;

    public AttributeBuilder(String rawValue) {
        this.rawValue = rawValue;
    }

    Attribute build(JspIterator iterator) {
        values.add(new TextValue(rawValue, iterator.getColNumber()));

        int indexOf = rawValue.indexOf('=');
        if (indexOf != -1 && indexOf + 1 < rawValue.length()) {
            delimiter = rawValue.charAt(indexOf + 1);
            findAttributeEnd(iterator, indexOf + 2);
            String name = rawValue.substring(0, indexOf);
            return new Attribute(rawValue, name, parseValue());
        }
        return new Attribute(rawValue);
    }

    private Object parseValue() {
        List<Expression> expressions = ExpressionBuilder.parseExpressions(values);
        if (!expressions.isEmpty()) {
            return expressions;
        } else {
            int start = rawValue.indexOf(delimiter) + 1;
            if (start < rawValue.length() && rawValue.indexOf(delimiter, start) >= 0) {
                return rawValue.substring(start, rawValue.indexOf(delimiter, start));
            } else {
                return null;
            }
        }

    }

    private List<TextValue> findAttributeEnd(JspIterator iterator, int start) {
        int attributeEnd = rawValue.indexOf(delimiter, start);
        while (attributeEnd == -1 && iterator.hasNext()) {
            values.add(new TextValue(iterator.next(), iterator.getColNumber()));
            rawValue += " " + iterator.next();
            attributeEnd = rawValue.indexOf(delimiter, start);
        }
        return values;
    }
}