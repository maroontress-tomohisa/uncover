package com.maroontress.uncover.gxx;

/**
   デフォルトの型の実装です。
*/
public final class DefaultType extends Type {
    /** 型の名前を表すインスタンスです。 */
    private TypeName name;

    /**
       文字列で型名を生成します。

       @param name 型名
    */
    public DefaultType(final String name) {
	this.name = new StringTypeName(name);
    }

    /**
       エクスポータブルで型名を生成します。

       @param name 型名
    */
    public DefaultType(final Exportable name) {
	this.name = new ExportableTypeName(name);
    }

    /**
       コンテキストで型名を生成します。

       コンテキストは型名が始まる位置にある必要があります。

       コンテキストは型名分進みます。

       @param context コンテキスト
    */
    public DefaultType(final Context context) {
	name = new ExportableTypeName(context);
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	name.exportName(b);
	exportQualifiers(b);
    }
}
