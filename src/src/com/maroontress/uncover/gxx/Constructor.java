package com.maroontress.uncover.gxx;

/**
   コンストラクタコンポーネントです。
*/
public final class Constructor extends SimpleComponent {
    /**
       インスタンスを生成します。
    */
    public Constructor() {
    }

    /** {@inheritDoc} */
    public void export(final Exporter b) {
	b.appendComponent();
    }
}
