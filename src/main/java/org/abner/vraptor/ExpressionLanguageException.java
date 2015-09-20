package org.abner.vraptor;

import org.abner.vraptor.jsp.Location;

public class ExpressionLanguageException extends Exception {

    private static final long serialVersionUID = 8420034150664365018L;
    private Location location;
    private Type type;

    public enum Type {
        ERROR, WARNING;
    }

    public ExpressionLanguageException(String message, Location location) {
        this(message, location, Type.ERROR);
    }

    public ExpressionLanguageException(String message, Location location, Type type) {
        super(message);
        this.location = location;
        this.type = Type.ERROR;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isError() {
        return type == Type.ERROR;
    }

}
