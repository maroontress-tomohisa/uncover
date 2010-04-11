package com.maroontress.uncover.gxx;

/**
*/
public final class ConstantComponent extends Component {
    /** */
    private CharSequence name;

    /**
    */
    public ConstantComponent(final String name) {
	this.name = name;
    }

    /**
    */
    public void export(final Exporter b) {
	b.append(name);
    }
}
