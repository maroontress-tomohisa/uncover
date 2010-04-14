package com.maroontress.uncover.gxx;

/**
   不変コンポーネントです。
*/
public final class ConstantComponent extends Component {
    /** コンポーネントの名前です。 */
    private CharSequence name;

    /**
       インスタンスを生成します。

       生成したインスタンスは不変オブジェクトです。

       @param name コンポーネントの名前
    */
    public ConstantComponent(final String name) {
	this.name = name;
    }

    /** {@inheritDoc} */
    public void export(final Exporter b) {
	b.append(name);
    }
}
