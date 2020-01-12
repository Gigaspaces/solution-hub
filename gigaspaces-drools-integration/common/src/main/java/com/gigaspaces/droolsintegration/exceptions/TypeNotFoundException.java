package com.gigaspaces.droolsintegration.exceptions;

public class TypeNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = -4850624487115120399L;
	
	private final String typeName;

    public TypeNotFoundException(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

}