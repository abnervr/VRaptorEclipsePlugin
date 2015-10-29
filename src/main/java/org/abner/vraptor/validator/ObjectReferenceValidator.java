package org.abner.vraptor.validator;

import java.util.List;

import org.abner.vraptor.ExpressionLanguageException;
import org.abner.vraptor.controller.Controller;
import org.abner.vraptor.controller.IncludedObject;
import org.abner.vraptor.jsp.ContextObject;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.expression.ExpressionReference;
import org.abner.vraptor.jsp.expression.ObjectReferenceExpression;
import org.abner.vraptor.parser.ControllerParser;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class ObjectReferenceValidator implements ExpressionValidator<ObjectReferenceExpression> {

    private List<IncludedObject> references;
    private Controller controller;
    private ObjectReferenceExpression expression;
    private Jsp jsp;

    public static final ObjectReferenceValidator getInstance() {
        return new ObjectReferenceValidator();
    }

    @Override
    public void validate(ObjectReferenceExpression expression, Jsp jsp) throws ExpressionLanguageException, CoreException {
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

    private void validateExpressions() throws ExpressionLanguageException, JavaModelException {
        for (ExpressionReference e : expression.getReferences()) {
            String name = e.getSegment(0);
            Object object = findByName(name);
            if (object == null) {
                throw new ExpressionLanguageException(name + " not included in " + controller.getName(),
                                expression.getLocation());
            } else if (object instanceof IncludedObject &&
                            ((IncludedObject) object).getField() != null) {
                validateObjectTree(e, (IncludedObject) object);
            }
        }

    }

    private void validateObjectTree(ExpressionReference e, IncludedObject object) throws JavaModelException, ExpressionLanguageException {
        IType type = object.getFieldType();
        int currentSegment = 1;
        if (type != null) {
            while (currentSegment < e.getSegmentCount()) {
                String name = e.getSegment(currentSegment++);
                IField field = type.getField(name);
                if (field == null) {
                    throw new ExpressionLanguageException(name + " not included in " +
                                    controller.getName(), expression.getLocation());
                } else {
                    type = field.getDeclaringType();
                }
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
