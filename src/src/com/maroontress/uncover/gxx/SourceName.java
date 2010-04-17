package com.maroontress.uncover.gxx;

/**
   名前をもつコンポーネントです。
*/
public final class SourceName extends Component {
    /** コンポーネントの名前です。 */
    private CharSequence name;

    /**
       インスタンスを生成します。

       @param name コンポーネントの名前
    */
    public SourceName(final CharSequence name) {
	this.name = name;
    }

    /** {@inheritDoc} */
    public void export(final Exporter b) {
	b.append(name);
    }

    /** {@inheritDoc} */
    public void exportName(final Exporter b) {
	export(b);
    }
}
