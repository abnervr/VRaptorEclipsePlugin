package org.abner.vraptor.jsp;

import java.util.ArrayList;
import java.util.List;

import org.abner.vraptor.jsp.dom.Document;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class Jsp {

    private IFile file;

    private IJavaProject project;

    private Document document;

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

    public void setDocument(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public IFile getFile() {
        return file;
    }

    public String getCurrentDirectory() {
        IPath fullPath = file.getFullPath();
        return fullPath.segment(fullPath.segmentCount() - 2);
    }

    public String getPath() {
        String path = null;
        if (!getCurrentDirectory().equals("jsp")) {
            IPath fullPath = file.getFullPath();
            int i = 3;
            while (i < fullPath.segmentCount() && !fullPath.segment(fullPath.segmentCount() - i).equals("jsp")) {
                if (path == null) {
                    path = fullPath.segment(fullPath.segmentCount() - i);
                } else {
                    path = fullPath.segment(fullPath.segmentCount() - i) + "/" + path;
                }
                i++;
            }
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

    private List<ContextObject> contextObjects = new ArrayList<>();

    public List<ContextObject> getContextObjects() {
        return contextObjects;
    }

    public void addContextObject(ContextObject object) {
        contextObjects.add(object);
    }

    public void removeContextObject(ContextObject object) {
        contextObjects.remove(object);
    }

}
