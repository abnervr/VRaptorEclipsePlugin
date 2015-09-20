package org.abner.vraptor.jsp.dom;

import java.util.Collections;
import java.util.List;

import org.abner.vraptor.jsp.expression.Expression;

public class Attribute {

    private String rawValue;

    private String name;

    private String value;

    private List<Expression> expressions;

    public Attribute(String rawValue, String name) {
        this.rawValue = rawValue;
        this.name = name;
    }

    public Attribute(String rawValue, String name, String value) {
        this(rawValue, name);
        this.value = value;
    }

    public Attribute(String rawValue, String name, List<Expression> expressions) {
        this(rawValue, name);
        this.expressions = expressions;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public List<Expression> getExpressions() {
        if (expressions == null) {
            return Collections.emptyList();
        }
        return expressions;
    }

    public String getRawValue() {
        return rawValue;
    }


}
