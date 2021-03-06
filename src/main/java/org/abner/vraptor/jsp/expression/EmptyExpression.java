package org.abner.vraptor.jsp.expression;

import org.abner.vraptor.ExpressionLanguageException;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.Location;
import org.eclipse.core.runtime.CoreException;

public class EmptyExpression extends Expression {

    public EmptyExpression(String value, Location location) {
        super(value, location);
    }

    @Override
    public void validate(Jsp jsp) throws ExpressionLanguageException, CoreException {
        // DO nothing
    }

}
