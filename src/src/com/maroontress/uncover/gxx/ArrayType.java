package com.maroontress.uncover.gxx;

/**
   配列の型です。
*/
public final class ArrayType extends Type {
    /** */
    private Type elementType;

    /** */
    private int elementNum;

    /**
       配列の要素の型と数でインスタンスを生成します。

       @param elementType 要素の型
       @param elementNum 要素数
    */
    public ArrayType(final Type elementType, final int elementNum) {
	this.elementType = elementType;
	this.elementNum = elementNum;
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	elementType.export(b);
	if (hasQualifiers()) {
	    b.append(" (");
	    exportQualifiers(b);
	    b.append(")[");
	} else {
	    b.append(" [");
	}
	b.append(Integer.toString(elementNum));
	b.append("]");
    }
}
