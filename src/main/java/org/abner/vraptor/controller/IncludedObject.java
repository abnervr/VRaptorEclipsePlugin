package org.abner.vraptor.controller;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

/**
 * Object included in controller
 * 
 * @author abner
 *
 */
public class IncludedObject {

    private IMethod method;

    private String name;

    private String field;

    public IncludedObject(IMethod method, String name, String field) {
        this.method = method;
        this.name = name;
        this.field = field;
    }

    public IMethod getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }

    public String getField() {
        return field;
    }

    public IType getFieldType() throws JavaModelException {
        if (field.indexOf('.') == -1 && field.indexOf('(') == -1) {
            findTypeDeclaration();
        }
        return null;
    }

    private void findTypeDeclaration() throws JavaModelException {
        String source = method.getSource();
        int fieldStart = source.indexOf(field);
        if (fieldStart != -1) {
            String declaration = source.substring(0, fieldStart).trim();
            if (declaration.lastIndexOf(' ') != -1) {
                declaration = declaration.substring(declaration.lastIndexOf(' ') + 1);
            }
            if (declaration.lastIndexOf('(') != -1) {
                declaration = declaration.substring(declaration.lastIndexOf('(') + 1);
            }
            System.out.printf("Field '%s' type is '%s'\n", field, declaration);
        }
    }

}
