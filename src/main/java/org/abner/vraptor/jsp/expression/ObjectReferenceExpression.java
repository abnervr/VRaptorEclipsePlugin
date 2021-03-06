package org.abner.vraptor.jsp.expression;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import org.abner.vraptor.ExpressionLanguageException;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.Location;
import org.abner.vraptor.validator.ObjectReferenceValidator;

public class ObjectReferenceExpression extends Expression {

    private List<ExpressionReference> references = new ArrayList<>();

    public ObjectReferenceExpression(String value, Location location) {
        super(value, location);
        for (String part : value.split("\\s")) {
            if (part.contains("[") && part.contains("]")) {
                // TODO mapOrArrayObject[key]
                continue;
            }
            ExpressionReference expressionReference = new ExpressionReference(part);
            if (expressionReference.getSegmentCount() > 0) {
                references.add(expressionReference);
            }
        }
    }

    public List<ExpressionReference> getReferences() {
        return references;
    }

    @Override
    public void validate(Jsp jsp) throws ExpressionLanguageException, CoreException {
        ObjectReferenceValidator.getInstance().validate(this, jsp);
    }

}
