package com.maroontress.uncover.gxx;

/**
   テンプレート引数です。
*/
public abstract class TemplateArguments extends Exportable {
    /**
       コンテキストからテンプレート引数を生成します。

       @param context コンテキスト
       @return テンプレート引数
    */
    public static TemplateArguments create(final Context context) {
	return new ListArguments(context);
    }

    /**
       文字列でテンプレート引数を生成します。

       文字列は<>に囲まれる表現でなければなりません。

       @param args テンプレート引数を表す文字列
       @return テンプレート引数
    */
    public static TemplateArguments create(final String args) {
	return new StringArguments(args);
    }

    /** {@inheritDoc} */
    @Override public final void export(final Exporter b) {
	char c = b.lastChar();
	if (!Character.isLetterOrDigit(c) && c != '_') {
	    b.append(" ");
	}
	b.append("<");
	exportArgs(b);
	b.append(">");
    }

    /**
       テンプレート引数の中身を出力します。

       @param b エクスポータ
    */
    protected abstract void exportArgs(final Exporter b);
}
