package org.abner.vraptor.jsp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import org.abner.vraptor.jsp.expression.Expression;

public class Jsp {

    private List<Expression> expressions = new ArrayList<>();

    private IFile file;

    private IJavaProject project;

    public Jsp(IFile file) throws CoreException {
        this.file = file;
        if (project == null && file.getProject().hasNature("org.eclipse.jdt.core.javanature")) {
            project = JavaCore.create(file.getProject());
        }
    }

    public boolean isInJavaProject() {
        return project != null;
    }

    public IJavaProject getProject() {
        return project;
    }

    public void addExpression(Expression expression) {
        expressions.add(expression);
    }

    public IFile getFile() {
        return file;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public String getCurrentDirectory() {
        IPath fullPath = file.getFullPath();
        return fullPath.segment(fullPath.segmentCount() - 2);
    }

    public String getPath() {
        IPath fullPath = file.getFullPath();
        String path = null;
        int i = 3;
        while (!fullPath.segment(fullPath.segmentCount() - i).equals("jsp")) {
            if (path == null) {
                path = fullPath.segment(fullPath.segmentCount() - i);
            } else {
                path = fullPath.segment(fullPath.segmentCount() - i) + "/" + path;
            }
            i++;
        }
        return path;
    }

    public String getName() {
        String name = file.getName();
        int extensionIndexOf = name.lastIndexOf("." + file.getFileExtension());
        if (extensionIndexOf != -1) {
            return name.substring(0, extensionIndexOf);
        } else {
            return name;
        }
    }
}
