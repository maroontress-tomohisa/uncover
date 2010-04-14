package com.maroontress.uncover.gxx;

/**
   エクスポータブルによる型名です。
*/
public final class ExportableTypeName implements TypeName {
    /** 型の名前です。 */
    private Exportable name;

    /**
       インスタンスを生成します。

       @param name 型名
    */
    public ExportableTypeName(final Exportable name) {
	this.name = name;
    }

    /**
       インスタンスを生成します。

       コンテキストの直前の文字はSでした。Substitution.parse()を使用し
       て型名を取得します。

       @param context コンテキスト
    */
    public ExportableTypeName(final Context context) {
	Substitution.parse(context, new SubstitutionListener() {
	    public void templeateFound(final Context context) {
		name = Composite.newSubstitutionTemplate(context);
	    }
	    public void standardPrefixFound(final Context context,
					    final TemplatedComponent sub) {
		if (context.startsWith('I')) {
		    sub.parseTemplateArgument(context);
		}
		name = Composite.newStandardPrefix(sub);
	    }
	    public void substitutionFound(final Exportable e) {
		name = e;
	    }
	});
    }

    /** {@inheritDoc} */
    public void exportName(final Exporter b) {
	name.export(b);
    }
}
