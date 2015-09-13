package org.abner.vraptor.controller;

import java.util.ArrayList;
import java.util.List;

import org.abner.vraptor.jsp.Jsp;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class Controller {

    private IType element;

    public Controller(IType element) {
        this.element = element;
    }

    public IJavaElement getElement() {
        return element;
    }

    public String getName() {
        return element.getElementName();
    }

    public List<IncludedObject> getIncludedObjectsFor(Jsp jsp) throws JavaModelException {
        List<IncludedObject> objects = new ArrayList<>();
        for (IMethod method : element.getMethods()) {
            if (!method.isConstructor() &&
                            jsp.getName().equalsIgnoreCase(method.getElementName())) {
                objects.addAll(ObjectReferenceFactory.create(method.getSource()));
            }
        }
        return objects;
    }

}
