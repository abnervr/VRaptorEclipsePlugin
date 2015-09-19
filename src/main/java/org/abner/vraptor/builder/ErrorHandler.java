package org.abner.vraptor.builder;

import org.abner.vraptor.JspParseException;

public interface ErrorHandler {

    void error(JspParseException exception);

    void fatalError(JspParseException exception);

    void warning(JspParseException exception);
}
