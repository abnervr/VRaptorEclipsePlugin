package org.abner.vraptor.controller;

/**
 * Object included in controller
 * 
 * @author abner
 *
 */
public class IncludedObject {

    private String name;

    // private IType type;

    public IncludedObject(String include) {
        if (include.indexOf(',') != -1) {
            name = include.substring(0, include.indexOf(',')).trim();
            if (name.startsWith("\"") && name.endsWith("\"")) {
                name = name.substring(1, name.length() - 1);
            }
        } else {
            name = include;
        }
    }

    public String getName() {
        return name;
    }

}
