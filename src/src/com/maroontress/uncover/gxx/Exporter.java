package com.maroontress.uncover.gxx;

/**
   エクスポータブルの出力先です。
*/
public final class Exporter {
    /** 実際の出力先となる文字列ビルダです。 */
    private StringBuilder builder;

    /** 最終コンポーネントです。 */
    private Component component;

    /**
       インスタンスを生成します。
    */
    public Exporter() {
	builder = new StringBuilder();
    }

    /**
       最終コンポーネントを設定します。

       @param component コンポーネント
    */
    public void setComponent(final Component component) {
	this.component = component;
    }

    /**
       最終コンポーネントを出力します。
    */
    public void appendComponent() {
	component.export(this);
    }

    /**
       文字列シーケンスを出力します。

       @param seq 文字列シーケンス
       @return このインスタンス
    */
    public Exporter append(final CharSequence seq) {
	builder.append(seq);
	return this;
    }

    /**
       出力を文字列として取得します。

       @return 文字列
    */
    public String toString() {
	return builder.toString();
    }

    /**
       出力された最後の文字を取得します。

       @return 出力された最後の文字
    */
    public char lastChar() {
	return builder.charAt(builder.length() - 1);
    }
}
