package org.abner.vraptor.parser;

import java.util.ArrayList;
import java.util.List;

import org.abner.vraptor.controller.Controller;
import org.abner.vraptor.jsp.Jsp;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;

public class SearchHelper {

    static Controller searchByName(IJavaProject project, String className) throws CoreException {
        SearchPattern namePattern = PatternHelper.createByClassName(className);
        List<Controller> controllers = search(project, namePattern);

        if (!controllers.isEmpty()) {
            return controllers.get(0);
        } else {
            return null;
        }
    }

    static Controller searchByJsp(Jsp jsp) throws CoreException {
        SearchPattern namePattern = PatternHelper.createByJsp(jsp);
        List<Controller> controllers = search(jsp.getProject(), namePattern);

        if (controllers.size() == 1) {
            return controllers.get(0);
        } else if (!controllers.isEmpty() && jsp.getPath() != null) {
            return findByPath(jsp, controllers);
        }
        return null;

    }

    private static Controller findByPath(Jsp jsp, List<Controller> controllers) {
        String path = jsp.getPath();
        int parts = path.split("/").length;
        for (Controller controller : controllers) {
            if (path.equals(controller.getParentPath(parts))) {
                return controller;
            }
        }
        return null;
    }

    private static List<Controller> search(IJavaProject project, SearchPattern namePattern) throws JavaModelException, CoreException {
        List<Controller> controllers = new ArrayList<Controller>();

        IJavaSearchScope scope = SearchEngine.createJavaSearchScope(project.getPackageFragments());

        SearchRequestor requestor = new SearchRequestor() {

            @Override
            public void acceptSearchMatch(SearchMatch match) {
                if (match.getElement() instanceof IJavaElement) {
                    IJavaElement element = (IJavaElement) match.getElement();
                    controllers.add(new Controller((IType) element));
                }
            }

        };

        SearchEngine searchEngine = new SearchEngine();
        searchEngine.search(namePattern, new SearchParticipant[] {SearchEngine
                        .getDefaultSearchParticipant()}, scope, requestor,
                        null);

        return controllers;
    }

    public static List<IMethod> searchMethodReference(IMethod method) throws CoreException {
        List<IMethod> methods = new ArrayList<>();
        SearchPattern methodPattern = SearchPattern.createPattern(method, IJavaSearchConstants.METHOD, IJavaSearchConstants.REFERENCES);

        IJavaSearchScope scope = SearchEngine.createJavaSearchScope(method.getJavaProject().getPackageFragments());
        SearchRequestor requestor = new SearchRequestor() {

            @Override
            public void acceptSearchMatch(SearchMatch match) {
                if (match.getElement() instanceof IMethod) {
                    IMethod method = (IMethod) match.getElement();
                    methods.add(method);
                }
            }

        };

        SearchEngine searchEngine = new SearchEngine();
        searchEngine.search(methodPattern, new SearchParticipant[] {SearchEngine
                        .getDefaultSearchParticipant()}, scope, requestor,
                        null);
        return methods;
    }

}
