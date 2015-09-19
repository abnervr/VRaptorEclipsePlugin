package org.abner.vraptor.jsp.expression;

import org.eclipse.core.runtime.CoreException;

import org.abner.vraptor.JspParseException;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.Location;

public abstract class Expression {

    protected String value;
    private Location location;

    public Expression(String value, Location location) {
        this.value = value;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public abstract void validate(Jsp jsp) throws JspParseException, CoreException;

}
