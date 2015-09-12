package org.abner.vraptor.validator;

import org.abner.vraptor.JspParseException;
import org.abner.vraptor.controller.Controller;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.expression.LinkToExpression;
import org.abner.vraptor.parser.ControllerParser;
import org.eclipse.core.runtime.CoreException;

public class LinkToValidator implements ExpressionValidator<LinkToExpression> {

    public static final LinkToValidator INSTANCE = new LinkToValidator();

    @Override
    public void validate(LinkToExpression expression, Jsp jsp) throws JspParseException, CoreException {
        String linkToClass = expression.getLinkToClass();

        Controller controller = ControllerParser.findControllerByClassName(jsp.getProject(), linkToClass);
        if (controller == null) {
            throw new JspParseException(linkToClass + " not found", expression.getLocation());
        }
    }

}
