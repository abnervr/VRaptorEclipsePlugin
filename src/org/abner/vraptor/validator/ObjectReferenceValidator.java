package org.abner.vraptor.validator;

import java.util.List;

import org.abner.vraptor.JspParseException;
import org.abner.vraptor.controller.Controller;
import org.abner.vraptor.controller.IncludedObject;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.expression.ExpressionReference;
import org.abner.vraptor.jsp.expression.ObjectReferenceExpression;
import org.abner.vraptor.parser.ControllerParser;
import org.eclipse.core.runtime.CoreException;

public class ObjectReferenceValidator implements ExpressionValidator<ObjectReferenceExpression> {

    private List<IncludedObject> references;
    private Controller controller;
    private ObjectReferenceExpression expression;

    public static final ObjectReferenceValidator getInstance() {
        return new ObjectReferenceValidator();
    }

    @Override
    public void validate(ObjectReferenceExpression expression, Jsp jsp) throws JspParseException, CoreException {
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
            IncludedObject object = findByName(name);
            if (object == null) {
                throw new JspParseException(name + " not included in " + controller.getName(),
                                expression.getLocation());
            } else {
                // Validate object tree
            }
        }
    }

    private IncludedObject findByName(String segment) {
        for (IncludedObject object : references) {
            if (object.getName().equals(segment)) {
                return object;
            }
        }
        return null;
    }

}
