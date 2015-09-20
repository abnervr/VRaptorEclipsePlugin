package org.abner.vraptor.validator;

import java.util.List;

import org.abner.vraptor.JspParseException;
import org.abner.vraptor.controller.Controller;
import org.abner.vraptor.controller.IncludedObject;
import org.abner.vraptor.jsp.ContextObject;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.expression.ExpressionReference;
import org.abner.vraptor.jsp.expression.ObjectReferenceExpression;
import org.abner.vraptor.parser.ControllerParser;
import org.eclipse.core.runtime.CoreException;

public class ObjectReferenceValidator implements ExpressionValidator<ObjectReferenceExpression> {

    private List<IncludedObject> references;
    private Controller controller;
    private ObjectReferenceExpression expression;
    private Jsp jsp;

    public static final ObjectReferenceValidator getInstance() {
        return new ObjectReferenceValidator();
    }

    @Override
    public void validate(ObjectReferenceExpression expression, Jsp jsp) throws JspParseException, CoreException {
        this.jsp = jsp;
        controller = ControllerParser.findControllerByJsp(jsp);

        if (controller != null) {
            references = controller.getIncludedObjectsFor(jsp);
            if (!references.isEmpty()) {
                this.expression = expression;
                validateExpressions();
            }
        }
    }

    private void validateExpressions() throws JspParseException {
        for (ExpressionReference e : expression.getReferences()) {
            String name = e.getSegment(0);
            Object object = findByName(name);
            if (object == null) {
                throw new JspParseException(name + " not included in " + controller.getName(),
                                expression.getLocation());
            } else {
                // Validate object tree
            }
        }
    }

    private Object findByName(String segment) {
        for (IncludedObject object : references) {
            if (object.getName().equals(segment)) {
                return object;
            }
        }
        for (ContextObject contextObject : jsp.getContextObjects()) {
            if (contextObject.getName().equals(segment)) {
                return contextObject;
            }
        }
        return null;
    }

}
