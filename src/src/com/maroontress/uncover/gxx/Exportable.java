package com.maroontress.uncover.gxx;

/**
   名前、型の抽象基底クラスです。エクスポータに自分自身を出力する機能
   を提供します。
*/
public abstract class Exportable {

    /** {@inheritDoc} */
    public final String toString() {
	Exporter b = new Exporter();
	export(b);
	return b.toString();
    }

    /**
       エクスポータに出力します。

       @param b エクスポータ
    */
    public abstract void export(Exporter b);

    /**
       インスタンスのコピーを生成します。

       @return インスタンスのコピー
    */
    public final Exportable createCopy() {
	/*
	  積極的にObject.clone()を使いたいわけではない。Checkstyleの
	  DesignForExtensionを満たすのも難しい。このケースではclone()
	  の戻り値を実際のクラスにキャストする必要もない。
	*/
	return new SourceName(toString());
    }
}
