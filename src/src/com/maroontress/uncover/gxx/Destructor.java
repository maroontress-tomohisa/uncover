package com.maroontress.uncover.gxx;

/**
   デストラクタコンポーネントです。
*/
public final class Destructor extends NonPrefixComponent {
    /**
       インスタンスを生成します。
    */
    public Destructor() {
    }

    /** {@inheritDoc} */
    public void export(final Exporter b) {
	b.append("~");
	b.appendComponent();
    }
}
