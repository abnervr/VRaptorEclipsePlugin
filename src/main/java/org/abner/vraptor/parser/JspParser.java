package org.abner.vraptor.parser;

import org.abner.vraptor.builder.ErrorHandler;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.dom.builder.DocumentBuilder;
import org.abner.vraptor.validator.JspValidator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class JspParser {

    public void parse(IFile file, ErrorHandler errorHandler) throws CoreException {
        Jsp jsp = new Jsp(file);
        if (jsp.isInJavaProject()) {
            jsp.setDocument(DocumentBuilder.build(file.getContents()));
            JspValidator.validate(jsp, errorHandler);
        }
    }

}
