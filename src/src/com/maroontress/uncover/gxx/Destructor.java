package com.maroontress.uncover.gxx;

/**
*/
public final class Destructor extends Component {
    /**
    */
    public Destructor() {
    }

    /**
    */
    public void export(final Exporter b) {
	b.append("~");
	b.appendComponent();
    }
}
