package org.abner.vraptor.parser;

import java.util.ArrayList;
import java.util.List;

import org.abner.vraptor.controller.Controller;
import org.abner.vraptor.jsp.Jsp;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;

public class ControllerParser {

    private static List<Controller> controllers = new ArrayList<>();

    public static void clear() {
        controllers.clear();
    }

    public static Controller findControllerByClassName(IJavaProject project, String className) throws CoreException {
        for (Controller controller : controllers) {
            if (controller.getElement().getElementName().equals(className)) {
                return controller;
            }
        }
        Controller controller = SearchHelper.searchByName(project, className);
        if (controller != null) {
            controllers.add(controller);
        }
        return controller;
    }

    public static Controller findControllerByJsp(Jsp jsp) throws CoreException {
        for (Controller controller : controllers) {
            String name = controller.getName();
            String currentDirectory = jsp.getCurrentDirectory();
            if (name.toLowerCase().startsWith(currentDirectory.toLowerCase())) {
                return controller;
            }
        }
        Controller controller = SearchHelper.searchByJsp(jsp);
        if (controller != null) {
            controllers.add(controller);
        }
        return controller;
    }

}
