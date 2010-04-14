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
       インスタンスを複製します。

       clone()という名前はやめる。createCopy()とかにする。

       S[0-9A-Z]*_のあとにIが続かないことが確実なら、戻り値の型名は
       ConstantComponentの方がよいけど。

       TemplatedComponentが不変ではないのが元凶。

       @return 複製したインスタンス
    */
    public final Exportable clone() {
	return TemplatedComponent.create(this.toString());
    }
}
