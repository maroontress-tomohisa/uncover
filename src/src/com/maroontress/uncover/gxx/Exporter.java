package com.maroontress.uncover.gxx;

/**
*/
public final class Exporter {
    /** */
    private StringBuilder builder;

    /** */
    private Component component;

    /** */
    public Exporter() {
	builder = new StringBuilder();
    }

    /**
    */
    public void setComponent(final Component component) {
	this.component = component;
    }

    /**
    */
    public void appendComponent() {
	component.export(this);
    }

    /**
    */
    public Exporter append(final CharSequence seq) {
	builder.append(seq);
	return this;
    }

    /**
    */
    public String toString() {
	return builder.toString();
    }

    /**
    */
    public char lastChar() {
	return builder.charAt(builder.length() - 1);
    }
}
