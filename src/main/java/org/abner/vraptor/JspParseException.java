package org.abner.vraptor;

import org.abner.vraptor.jsp.Location;

public class JspParseException extends Exception {

    private static final long serialVersionUID = 8420034150664365018L;
    private Location location;

    public JspParseException(String message, Location location) {
        super(message);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

}
