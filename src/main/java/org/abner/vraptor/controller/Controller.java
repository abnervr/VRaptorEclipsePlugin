package org.abner.vraptor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.parser.ControllerParser;

public class Controller {

    private IType element;

    private Map<String, List<IncludedObject>> objects = new HashMap<>();

    public Controller(IType element) {
        this.element = element;
    }

    public IJavaElement getElement() {
        return element;
    }

    public String getName() {
        return element.getElementName();
    }

    public List<IncludedObject> getIncludedObjectsFor(Jsp jsp) throws CoreException {
        if (objects.containsKey(jsp.getName())) {
            return objects.get(jsp.getName());
        } else {
            List<IncludedObject> includedObjects = loadIncludedObjects(jsp);
            objects.put(jsp.getName(), includedObjects);
            return includedObjects;
        }
    }

    private List<IncludedObject> loadIncludedObjects(Jsp jsp) throws JavaModelException, CoreException {
        List<IncludedObject> objects = new ArrayList<>();
        for (IMethod method : element.getMethods()) {
            if (!method.isConstructor() &&
                            jsp.getName().equalsIgnoreCase(method.getElementName())) {
                for (IMethod methodReference : ControllerParser.findMethodReferences(method)) {
                    objects.addAll(ObjectReferenceFactory.create(methodReference.getSource()));
                }
            }
        }
        return objects;
    }

    public String getParentPath(int parts) {
        IPackageFragment packageFragment = element.getPackageFragment();
        String name = packageFragment.getElementName();
        return name;
    }

}
