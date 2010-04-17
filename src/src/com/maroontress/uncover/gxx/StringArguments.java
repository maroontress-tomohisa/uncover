package com.maroontress.uncover.gxx;

/**
   文字列で実現したテンプレート引数です。
*/
public final class StringArguments extends TemplateArguments {
    /** テンプレート引数を表す文字列です。 */
    private String args;

    /**
       インスタンスを生成します。

       @param args 引数
    */
    public StringArguments(final String args) {
	this.args = args;
    }

    /** {@inheritDoc} */
    @Override public void exportArgs(final Exporter b) {
	b.append(args);
    }
}
