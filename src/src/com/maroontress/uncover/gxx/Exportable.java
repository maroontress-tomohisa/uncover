package com.maroontress.uncover.gxx;

/**
*/
public abstract class Exportable {
    /**
    */
    public final String toString() {
	Exporter b = new Exporter();
	export(b);
	return b.toString();
    }

    /**
    */
    public abstract void export(Exporter b);

    /**
    */
    public final Exportable clone() {
	return TemplatedComponent.create(this.toString());
    }
}
