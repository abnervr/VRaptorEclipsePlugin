package org.abner.vraptor.parser;

import java.io.InputStream;
import java.util.Scanner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import org.abner.vraptor.JspParseException;
import org.abner.vraptor.builder.ErrorHandler;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.Location;
import org.abner.vraptor.jsp.expression.Expression;
import org.abner.vraptor.jsp.expression.ExpressionFactory;

public class JspParser {

    private Jsp jsp;

    private int lineNumber = 1;

    private int colNumber = 0;

    public void parse(IFile file, ErrorHandler errorHandler) throws CoreException {
        jsp = new Jsp(file);
        if (jsp.isInJavaProject()) {
            parseExpressions(file.getContents());

            for (Expression e : jsp.getExpressions()) {
                try {
                    e.validate(jsp);
                } catch (JspParseException parseException) {
                    errorHandler.error(parseException);
                }
            }
        }
    }

    private void parseExpressions(InputStream is) {
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                try {
                    parseExpression(line);
                    lineNumber++;
                    colNumber += line.length() + 1;
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseExpression(String line) {
        int fromIndex = 0;
        while (fromIndex < line.length()) {
            int indexOf = line.indexOf("${", fromIndex);

            if (indexOf != -1) {
                fromIndex = indexOf + 1;
                if (line.indexOf('}', fromIndex) != -1) {
                    String expressionValue = line.substring(fromIndex + 1, line.indexOf('}', fromIndex));
                    Location location = createLocation(line, fromIndex);

                    Expression expression = ExpressionFactory.create(expressionValue, location);
                    if (expression != null) {
                        jsp.addExpression(expression);
                    }
                }
            } else {
                fromIndex = line.length();
            }
        }
    }

    private Location createLocation(String line, int fromIndex) {
        return new Location(lineNumber, colNumber + fromIndex, colNumber + line.indexOf('}', fromIndex));
    }

}
