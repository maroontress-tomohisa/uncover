package com.maroontress.uncover.gxx;

/**
   文字列による型名です。
*/
public final class StringTypeName implements TypeName {
    /** 型の名前です。 */
    private String name;

    /**
       インスタンスを生成します。

       @param name 型名
    */
    public StringTypeName(final String name) {
	this.name = name;
    }

    /** {@inheritDoc} */
    public void exportName(final Exporter b) {
	b.append(name);
    }
}
