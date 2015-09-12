package org.abner.vraptor.builder;

import java.util.Map;

import org.abner.vraptor.JspParseException;
import org.abner.vraptor.jsp.Location;
import org.abner.vraptor.parser.JspParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class VRaptorBuilder extends IncrementalProjectBuilder {

    class SampleDeltaVisitor implements IResourceDeltaVisitor {

        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            IResource resource = delta.getResource();
            switch (delta.getKind()) {
                case IResourceDelta.ADDED:
                    // handle added resource
                    check(resource);
                    break;
                case IResourceDelta.REMOVED:
                    // handle removed resource
                    break;
                case IResourceDelta.CHANGED:
                    // handle changed resource
                    check(resource);
                    break;
            }
            // return true to continue visiting children.
            return true;
        }
    }

    class SampleResourceVisitor implements IResourceVisitor {
        @Override
        public boolean visit(IResource resource) {
            check(resource);
            // return true to continue visiting children.
            return true;
        }
    }

    class VraptorJspErrorHandler implements ErrorHandler {

        private IFile file;

        public VraptorJspErrorHandler(IFile file) {
            this.file = file;
        }

        private void addMarker(JspParseException e, int severity) {
            VRaptorBuilder.this.addMarker(file, e.getMessage(), e.getLocation(), severity);
        }

        @Override
        public void error(JspParseException exception) {
            addMarker(exception, IMarker.SEVERITY_ERROR);
        }

        @Override
        public void fatalError(JspParseException exception) {
            addMarker(exception, IMarker.SEVERITY_ERROR);
        }

        @Override
        public void warning(JspParseException exception) {
            addMarker(exception, IMarker.SEVERITY_WARNING);
        }
    }

    public static final String BUILDER_ID = "VRaptorEclipsePlugin.vraptorBuilder";

    private static final String MARKER_TYPE = "VRaptorEclipsePlugin.jspProblem";

    private void addMarker(IFile file, String message, Location location, int severity) {
        try {
            IMarker marker = file.createMarker(MARKER_TYPE);
            marker.setAttribute(IMarker.MESSAGE, message);
            marker.setAttribute(IMarker.SEVERITY, severity);
            marker.setAttribute(IMarker.LINE_NUMBER, location.getLineNumber());
            if (location.getEndColNumber() != -1) {
                marker.setAttribute(IMarker.CHAR_START, location.getStartColNumber());
                marker.setAttribute(IMarker.CHAR_END, location.getEndColNumber());
            }
        } catch (CoreException e) {}
    }

    @Override
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
        if (kind == FULL_BUILD) {
            fullBuild(monitor);
        } else {
            IResourceDelta delta = getDelta(getProject());
            if (delta == null) {
                fullBuild(monitor);
            } else {
                incrementalBuild(delta, monitor);
            }
        }
        return null;
    }

    @Override
    protected void clean(IProgressMonitor monitor) throws CoreException {
        // delete markers set and files created
        getProject().deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
    }

    void check(IResource resource) {
        if (resource instanceof IFile && resource.getName().endsWith(".jsp")) {
            IFile file = (IFile) resource;
            deleteMarkers(file);
            VraptorJspErrorHandler reporter = new VraptorJspErrorHandler(file);
            try {
                getParser().parse(file, reporter);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void deleteMarkers(IFile file) {
        try {
            file.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
        } catch (CoreException ce) {}
    }

    protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
        try {
            getProject().accept(new SampleResourceVisitor());
        } catch (CoreException e) {}
    }

    private JspParser getParser() {
        return new JspParser();
    }

    protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
        // the visitor does the work.
        delta.accept(new SampleDeltaVisitor());
    }
}
