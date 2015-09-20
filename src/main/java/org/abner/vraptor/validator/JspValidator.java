package org.abner.vraptor.validator;

import java.util.List;

import org.abner.vraptor.ExpressionLanguageException;
import org.abner.vraptor.builder.ErrorHandler;
import org.abner.vraptor.jsp.ContextObject;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.dom.Attribute;
import org.abner.vraptor.jsp.dom.Element;
import org.abner.vraptor.jsp.dom.IElement;
import org.abner.vraptor.jsp.dom.TextElement;
import org.abner.vraptor.jsp.expression.Expression;
import org.eclipse.core.runtime.CoreException;

public class JspValidator {

    public static void validate(Jsp jsp, ErrorHandler handler) throws CoreException {
        new JspValidator(jsp).validate(handler);
    }

    private Jsp jsp;

    private ErrorHandler handler;

    public JspValidator(Jsp jsp) {
        this.jsp = jsp;
    }

    private void validate(ErrorHandler handler) throws CoreException {
        this.handler = handler;
        for (IElement e : jsp.getDocument().getElements()) {
            validateIElement(e);
        }
    }

    private void validateIElement(IElement e) throws CoreException {
        if (e instanceof Element) {
            validateElement((Element) e);
        } else if (e instanceof TextElement) {
            validateExpressions(((TextElement) e).getExpressions());
        }
    }

    private void validateElement(Element element) throws CoreException {
        validateAttributes(element);
        ContextObject contextObject = createContextObject(element);
        for (IElement child : element.getChildren()) {
            validateIElement(child);
        }
        if (contextObject != null) {
            jsp.removeContextObject(contextObject);
        }
    }

    private ContextObject createContextObject(Element element) {
        if (element.getName().equals("c:forEach") && element.hasAttribute("var") && element.hasAttribute("items")) {
            String name = element.getAttributeByName("var").getValue();
            List<Expression> expressions = element.getAttributeByName("items").getExpressions();
            if (expressions.size() == 1) {
                ContextObject contextObject = new ContextObject(name, expressions.get(0));
                jsp.addContextObject(contextObject);
                return contextObject;
            }
        }
        return null;
    }

    private void validateAttributes(Element element) throws CoreException {
        for (Attribute attribute : element.getAttributes()) {
            validateExpressions(attribute.getExpressions());
        }
    }

    private void validateExpressions(List<Expression> expressions) throws CoreException {
        for (Expression e : expressions) {
            try {
                e.validate(jsp);
            } catch (ExpressionLanguageException exception) {
                if (exception.isError()) {
                    handler.error(exception);
                } else {
                    handler.warning(exception);
                }
            }
        }
    }

}
