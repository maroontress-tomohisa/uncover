package com.maroontress.uncover.gxx;

/**
*/
public final class StringTypeName implements TypeName {
    /** */
    private String name;

    /**
    */
    public StringTypeName(final String name) {
	this.name = name;
    }

    /**
    */
    public void exportName(final Exporter b) {
	b.append(name);
    }
}
