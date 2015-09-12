package org.abner.vraptor.controller;

import org.abner.vraptor.jsp.expression.Expression;
import org.eclipse.jdt.core.IJavaElement;

public class Controller {

    private IJavaElement element;

    public Controller(IJavaElement element) {
        this.element = element;
    }

    public IJavaElement getElement() {
        return element;
    }

    public void evaluateExpression(Expression expression) {
        // TODO Auto-generated method stub
    }

}
