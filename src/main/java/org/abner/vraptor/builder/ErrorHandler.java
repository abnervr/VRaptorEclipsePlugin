package org.abner.vraptor.builder;

import org.abner.vraptor.ExpressionLanguageException;

public interface ErrorHandler {

    void error(ExpressionLanguageException exception);

    void fatalError(ExpressionLanguageException exception);

    void warning(ExpressionLanguageException exception);
}
