package org.abner.vraptor.jsp.expression;

import org.eclipse.core.runtime.CoreException;

import org.abner.vraptor.ExpressionLanguageException;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.Location;
import org.abner.vraptor.validator.LinkToValidator;

public class LinkToExpression extends Expression {

    public LinkToExpression(String value, Location location) {
        super(value, location);
    }

    public String getLinkToClass() {
        return value.substring(value.indexOf('[') + 1, value.indexOf(']'));
    }

    @Override
    public void validate(Jsp jsp) throws ExpressionLanguageException, CoreException {
        LinkToValidator.INSTANCE.validate(this, jsp);
    }

}
