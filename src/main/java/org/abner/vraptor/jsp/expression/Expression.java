package org.abner.vraptor.jsp.expression;

import org.abner.vraptor.JspParseException;
import org.abner.vraptor.jsp.Jsp;
import org.abner.vraptor.jsp.Location;
import org.eclipse.core.runtime.CoreException;

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

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Expression other = (Expression) obj;
        if (location == null) {
            if (other.location != null) {
                return false;
            }
        } else if (!location.equals(other.location)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    public abstract void validate(Jsp jsp) throws JspParseException, CoreException;

}
