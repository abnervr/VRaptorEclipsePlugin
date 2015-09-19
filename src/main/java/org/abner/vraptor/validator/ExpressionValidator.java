package org.abner.vraptor.validator;

import org.abner.vraptor.JspParseException;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.expression.Expression;
import org.eclipse.core.runtime.CoreException;

public interface ExpressionValidator<E extends Expression> {

    void validate(E expression, Jsp jsp) throws JspParseException, CoreException;

}

